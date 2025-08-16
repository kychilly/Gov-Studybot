package com.Discord.DiscordBot.Units;

import com.Discord.DiscordBot.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.awt.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ActiveQuestionTracker {

    private static final Map<User, Question> userQuestions = new HashMap<>();
    private static final Map<Long, User> activeMessageIds = new HashMap<>(); // The IDs of the question messages the bot sent. Returns the user message associated with
    private static final Map<User, Integer> questionIds = new HashMap<>(); // The IDs of the user's questions
    private static final Map<User, Long> questionTimestamps = new HashMap<>(); // Stores the amount of time left before question ends(gets deleted cause afk)
    private static final Map<User, Long> questionChannelIds = new HashMap<>(); // Compliments questionTimestamps

    public static void addActiveQuestion(User user, Question question, long messageId, int questionId, long channelId) {
        userQuestions.put(user, question);
        activeMessageIds.put(messageId, user);
        questionIds.put(user, questionId);
        questionTimestamps.put(user, System.currentTimeMillis());
        questionChannelIds.put(user, channelId);
    }

    // A lot of spagetti code rn
    public static void removeActiveQuestionByButtonAnswer(User user, Long messageId) {
        userQuestions.remove(user);
        activeMessageIds.remove(messageId);
        questionIds.remove(user);
        questionTimestamps.remove(user);
        questionChannelIds.remove(user);
    }

    public static void removeActiveQuestion(User user, Long messageId) {
        // Edit the original message
        try {
            Long channelId = questionChannelIds.get(user);
            if (channelId != null && messageId != null) {
                TextChannel channel = user.getJDA().getTextChannelById(channelId);
                if (channel != null) {
                    channel.editMessageEmbedsById(messageId,
                            new EmbedBuilder()
                                    .setTitle("⏰ Question Expired")
                                    .setDescription(String.format(
                                            "%s, your question has expired because you didn't respond within %d minutes.\n\n" +
                                                    "Use `%s<number>` or `/%s-practice-question` to start a new question.",
                                            user.getAsMention(),
                                            Constants.unitQuestionTimeoutInMinutes,
                                            Constants.prefix,
                                            Constants.slashPrefix
                                    ))
                                    .setColor(new Color(255, 82, 82)) // A nicer red color
                                    .setThumbnail(user.getEffectiveAvatarUrl()) // User's avatar
                                    .setFooter("⏰ Question timed out • " + Instant.now().atZone(ZoneId.of("America/New_York")).format(DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a")))
                                    .build()
                    ).queue(
                            success -> { channel.editMessageComponentsById(messageId, Collections.emptyList()).queue(); },
                            error -> {} // Still remove question if can't edit
                    );
                }
            }
        } catch (Exception e) {
            // Still remove the question if errors
            e.printStackTrace();
        }

        // Then proceed with cleanup as before
        userQuestions.remove(user);
        activeMessageIds.remove(messageId);
        questionIds.remove(user);
        questionTimestamps.remove(user);
        questionChannelIds.remove(user);
    }

    public static boolean hasActiveQuestion(User user) {
        return userQuestions.containsKey(user);
    }

    public static Question getActiveQuestion(User user) {
        return userQuestions.get(user);
    }

    public static User getUserByMessageId(Long messageId) {
        return activeMessageIds.get(messageId);
    }


    // Sussy timer stuff
    public static void checkForExpiredQuestions(ShardManager shardManager) {
        long currentTime = System.currentTimeMillis();
        long timeoutMillis = 1000L * 60 * Constants.unitQuestionTimeoutInMinutes;

        // Create a copy to avoid concurrent modification
        new HashMap<>(questionTimestamps).forEach((user, timestamp) -> {
            if (currentTime - timestamp > timeoutMillis) {
                // Question has expired
                Long messageId = getMessageIdForUser(user);
                removeActiveQuestion(user, messageId);
                notifyUserOfExpiration(shardManager, user);
            }
        });
    }

    private static void notifyUserOfExpiration(ShardManager shardManager, User user) {
        Long channelId = getChannelIdForUser(user);

        if (channelId != null) { // So far, only sends DM notification??? possible fix later
            TextChannel channel = shardManager.getTextChannelById(channelId);
            System.out.println("Line 70");
            if (channel != null) {
                System.out.println("Line 72");
                try { // Just sends the message that the user hasn't answered in like 2 minutes
                    System.out.println("Line 74");
                    channel.sendMessage(user.getAsMention() + " ⌛ Your question has expired because you didn't respond within " + Constants.unitQuestionTimeoutInMinutes + " minutes.")
                            .queue(
                                    success -> {},
                                    error -> sendDMNotification(shardManager, user)
                            );
                } catch (Exception e) {
                    System.out.println("I failed the thing above me lol");
                    sendDMNotification(shardManager, user);
                }
            } else {
                sendDMNotification(shardManager, user);
            }
        } else {
            sendDMNotification(shardManager, user);
        }
    }

    private static void sendDMNotification(ShardManager shardManager, User user) {
        shardManager.retrieveUserById(user.getId()).queue(
                botUser -> {
                    botUser.openPrivateChannel().queue(
                            channel -> {
                                channel.sendMessage("⌛ Your question has expired because you didn't respond within " + Constants.unitQuestionTimeoutInMinutes +
                                                " minutes. Use the `" + Constants.prefix + "<number>` or `/" + Constants.slashPrefix+ "-practice-question` command again to get a new question.")
                                        .queue(null,
                                                e -> System.out.println("Failed to send DM to " + user.getId())
                                        );
                            },
                            e -> System.out.println("Couldn't open DM channel for " + user.getId())
                    );
                },
                e -> System.out.println("Couldn't retrieve user " + user.getId())
        );
    }

    public static Long getMessageIdForUser(User user) {
        return activeMessageIds.entrySet().stream()
                .filter(entry -> entry.getValue().equals(user))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }



    public static Long getChannelIdForUser(User user) {
        return questionChannelIds.get(user);
    }

}