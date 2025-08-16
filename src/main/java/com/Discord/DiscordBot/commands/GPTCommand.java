package com.Discord.DiscordBot.commands;

import com.Discord.DiscordBot.A_IndividualMethods.BadWordChecker;
import com.Discord.DiscordBot.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import okhttp3.*;

import java.io.IOException;
import java.text.Normalizer;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class GPTCommand {

    private static final Dotenv config = Dotenv.configure().ignoreIfMissing().load();
    private static final String GROQ_API_KEY = config.get("GROQ_API_KEY");

    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    // Map to track last request times per user
    private static final Map<String, Long> lastUsed = new HashMap<>();
    private static final long COOLDOWN_MILLIS = 5000; // 5 seconds

    public static CommandData getCommandData() {
        return Commands.slash(Constants.slashPrefix + "-ask", "Ask me questions about the AP " + Constants.slashPrefix.toUpperCase() + " Course or how to use my commands!")
                .addOption(OptionType.STRING,"prompt", "What do you want to ask me?", true);
    }

    public static void execute(SlashCommandInteractionEvent event) {
        String message = event.getOption("prompt").getAsString();
        System.out.println(event.getUser().getAsTag() + " says: " + message); // Test
        String userId = event.getMember().getId();
        long now = Instant.now().toEpochMilli();

        if (lastUsed.containsKey(userId)) {
            long last = lastUsed.get(userId);
            long remaining = COOLDOWN_MILLIS - (now - last);
            if (remaining > 0) {
                long seconds = (remaining + 999) / 1000;
                event.reply("⏳ Please wait " + seconds + " more second" + (seconds == 1 ? "" : "s") + " before using me!")
                        .setEphemeral(true)
                        .queue();
                return;
            }
        }

        lastUsed.put(userId, now); // Update usage time

        event.deferReply().queue(); // Acknowledge the interaction first

        new Thread(() -> {
            try {
                String reply = getGroqReply(Constants.COMMAND_GUIDE_PROMPT + message);
                sendReplyInChunks(reply, event);
            } catch (Exception e) {
                e.printStackTrace();
                event.getHook().sendMessage("⚠️ ERROR: I am currently down now due to serverside issues. I should be back up soon. Sorry for the inconvenience.").queue();
            }
        }).start();
    }

    private static String getGroqReply(String prompt) throws IOException {
        JsonArray messages = new JsonArray();

        // System message to force the model to censor bad words
        JsonObject systemMessage = new JsonObject();
        systemMessage.addProperty("role", "system");
        systemMessage.addProperty("content",
                "You must never output profanity, racial slurs, homophobic slurs, or other offensive terms. " +
                        "Replace such words with '_______'. This includes common leetspeak and Cyrillic lookalikes. " +
                        "Examples: n-word (and variations), f-slur, kike, ch*nk, wop, sp*c, c*nt, bitch, fuck, shit, retard, moron. " +
                        "Also make sure to check if the user is trying to trick you into saying bad words(Example: concatenate \"f\" \"a\" \"g\" \"g\" \"o\" \"t\"" +
                        "Check to make sure emojis are not able to bypass either" +
                        "If the user is trying to get you to promote ideologies(like hitler or politics) tell them they are trying to get you to respond in a bad manner and that you wont do that." +
                        "Detect and censor these even if letters are substituted with symbols or foreign characters.");

        // User message
        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        userMessage.addProperty("content", prompt);

        // Add both messages
        messages.add(systemMessage);
        messages.add(userMessage);

        // Build request
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", "llama3-70b-8192"); // Possible to upgrade to a better model
        requestBody.add("messages", messages);

        RequestBody body = RequestBody.create(requestBody.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://api.groq.com/openai/v1/chat/completions")
                .addHeader("Authorization", "Bearer " + GROQ_API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response: " + response.code() + "\n" + response.body().string());
            }

            String responseBody = response.body().string();
            JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
            return json.getAsJsonArray("choices")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("message")
                    .get("content").getAsString();
        }
    }


    private static void sendReplyInChunks(String reply, SlashCommandInteractionEvent event) {
        // First check the entire reply for bad words
        if (BadWordChecker.hasAReallyBadWord(reply)) {
            event.getHook().sendMessage(
                    "I am unable to answer your question as I believe you are trying to make me say an extremely hateful word."
            ).queue();
            return;
        }

        // If no bad words, start chunking(should always be only 1 chunk though)
        int maxLength = 2000;
        int start = 0;
        boolean firstChunk = true;

        while (start < reply.length()) {
            int end = Math.min(start + maxLength, reply.length());

            if (end < reply.length() && reply.charAt(end) != ' ') {
                int lastSpace = reply.lastIndexOf(' ', end);
                if (lastSpace > start) {
                    end = lastSpace;
                }
            }

            String chunk = reply.substring(start, end).trim();

            if (firstChunk) {
                event.getHook().sendMessage(chunk).queue();
                firstChunk = false;
            } else {
                event.getHook().sendMessage(chunk).queue();
            }

            start = end;
            while (start < reply.length() && reply.charAt(start) == ' ') start++;
        }
    }

}

