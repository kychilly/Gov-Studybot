package com.Discord.DiscordBot.Units;

import com.Discord.DiscordBot.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static com.Discord.DiscordBot.listeners.ButtonListener.*;

public class HandleUnitsCommand {

    // This should be for all 4 units. Parameters the unit()
    public static void execute(MessageReceivedEvent event, User user, int unit, ArrayList<Question> specificQuestionList) {

        if (unit > Constants.numUnits) {
            event.getChannel().sendMessage(Objects.requireNonNull(event.getMember()).getAsMention() + ", the AP " + Constants.slashPrefix.toUpperCase() + " Course only has " + Constants.numUnits + " units!").queue();
            return;
        }

        if (unit <= 0) {
            event.getChannel().sendMessage(Objects.requireNonNull(event.getMember()).getAsMention() + ", Please choose a unit between 1-" + Constants.numUnits).queue();
            return;
        }

        if (ActiveQuestionTracker.hasActiveQuestion(user)) {
            handleActiveQuestion(event, user); // Handles if the person already had an active question
            return;
        }

        int prevQuestion = incorrectUserQuestions.get(user) != null
                ? incorrectUserQuestions.get(user).getQuestionId() : -1;

        if (incorrectUserAnswers.get(user) != null) { // Should always remove last
            incorrectUserAnswers.remove(user);
            incorrectUserQuestions.remove(user);
            Long entryToRemove = -1L;
            for (Map.Entry<Long, User> entry : incorrectMessageIds.entrySet()) {
                if (entry.getValue().equals(user)) {
                    entryToRemove = entry.getKey();
                }
            }
            if (entryToRemove == -1L) {
                event.getChannel().sendMessage("MASSIVE BUG, PLEASE DONT DO WHATEVER U JUST DID LOL (line 37 of HandleUnitsCommand execute").queue();
            }
            incorrectMessageIds.remove(entryToRemove);
        }

        Question question = QuestionBank.getRandomQuestion(specificQuestionList, prevQuestion);
        if (question == null) {
            event.getChannel().sendMessage(String.format("No questions available for Unit %s", unit)).queue();
            return;
        }

        // Just checks in case somehow the answers werent removed previously cause it is new question
        // Jk, this is needed now since only removing when asking a next question
        if (incorrectUserAnswers.get(user) != null) { // Should be both
            incorrectUserAnswers.remove(user);
            incorrectUserQuestions.remove(user);
            Long entryToRemove = -1L;
            for (Map.Entry<Long, User> entry : incorrectMessageIds.entrySet()) {
                if (entry.getValue().equals(user)) {
                    entryToRemove = entry.getKey();
                }
            }
            if (entryToRemove == -1L) {
                event.getChannel().sendMessage("MASSIVE BUG, PLEASE DONT DO WHATEVER U JUST DID LOL (line 60 in HandleUnitsCommand execute").queue();
            }
            incorrectMessageIds.remove(entryToRemove);
        }

        // Create question embed
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle(String.format("Unit %s Question (%s)", unit, question.getQuestionDifficulty()))
                .setColor(Color.BLUE)
                .setDescription(user.getAsMention() + ", " + question.getQuestion())
                .addField("Options:", // Single field or else sus choice placement
                        "A) " + question.getOptionA() + "\n" +
                                "B) " + question.getOptionB() + "\n" +
                                "C) " + question.getOptionC() + "\n" +
                                "D) " + question.getOptionD(),
                        false
                )
                .setFooter(String.format("Choose the correct answer below (ID: %d)", question.getQuestionId()));

        // Create message with buttons
        MessageCreateBuilder messageBuilder = new MessageCreateBuilder()
                .setEmbeds(embedBuilder.build())
                .addActionRow( // I'm too lazy to get the real addActionRow thing lol, change that later
                        net.dv8tion.jda.api.interactions.components.buttons.Button.primary("answer_A", "A"),
                        net.dv8tion.jda.api.interactions.components.buttons.Button.primary("answer_B", "B"),
                        net.dv8tion.jda.api.interactions.components.buttons.Button.primary("answer_C", "C"),
                        net.dv8tion.jda.api.interactions.components.buttons.Button.primary("answer_D", "D")
                );

        long channelId = event.getChannel().getIdLong();

        event.getChannel().sendMessage(messageBuilder.build()).queue(msg -> {
            ActiveQuestionTracker.addActiveQuestion(user, question, msg.getIdLong(), question.getQuestionId(), channelId);
        });

    }

    public static void handleActiveQuestion(MessageReceivedEvent event, User user) {
        Long messageId = ActiveQuestionTracker.getMessageIdForUser(user);
        Long channelId = ActiveQuestionTracker.getChannelIdForUser(user);

        // If we can't find the message or channel, clean up and notify user
        if (messageId == null || channelId == null) {
            ActiveQuestionTracker.removeActiveQuestion(user, messageId);
            event.getChannel().sendMessage("Your previous question couldn't be found. I've cleared it - you may now request a new one.")
                    .queue();
            return;
        }

        // Build the jump URL
        String jumpUrl = String.format("https://discord.com/channels/%s/%d/%d",
                event.getGuild().getId(),
                channelId,
                messageId);

        // Create an embed with the clickable link
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(0xFFA500) // Orange color for warning
                .setThumbnail(Objects.requireNonNull(event.getMember()).getUser().getEffectiveAvatarUrl())
                .setDescription(String.format(
                        "%s, you already have an active question!\n\n" +
                                "[➔ Jump to your question](%s)\n\n" +
                                "Please answer it before requesting a new one.",
                        user.getAsMention(),
                        jumpUrl
                ));

        // Send response
        event.getChannel().sendMessageEmbeds(embed.build()).queue();
    }

}
