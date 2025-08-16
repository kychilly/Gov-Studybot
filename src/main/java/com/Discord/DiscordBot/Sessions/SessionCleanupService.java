package com.Discord.DiscordBot.Sessions;

import com.Discord.DiscordBot.Constants;
import com.Discord.DiscordBot.commands.TestCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.sharding.ShardManager;
import java.awt.Color;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SessionCleanupService {
    private final ShardManager shardManager;
    private final long cleanupIntervalMinutes;
    private final long sessionTimeoutMinutes;

    public SessionCleanupService(ShardManager shardManager,
                                 long cleanupIntervalMinutes,
                                 long sessionTimeoutMinutes) {
        this.shardManager = shardManager;
        this.cleanupIntervalMinutes = cleanupIntervalMinutes;
        this.sessionTimeoutMinutes = sessionTimeoutMinutes;
    }

    public void startCleanupTask() {
        Timer timer = new Timer(true); // Daemon thread
        timer.scheduleAtFixedRate(new TimerTask() {
                                      @Override
                                      public void run() {
                                          cleanupInactiveSessions();
                                      }
                                  }, TimeUnit.MINUTES.toMillis(cleanupIntervalMinutes),
                TimeUnit.MINUTES.toMillis(cleanupIntervalMinutes));
    }

    private void cleanupInactiveSessions() {
        long now = System.currentTimeMillis();
        List<Long> toRemove = new ArrayList<>();
        Map<Long, SessionInfo> sessionsToProcess = new HashMap<>();

        // First collect sessions that need processing
        synchronized (TestCommand.class) {
            TestCommand.getActiveTests().forEach((userId, session) -> {
                if (now - session.getLastActivityTime() > TimeUnit.MINUTES.toMillis(sessionTimeoutMinutes)) {
                    toRemove.add(userId);
                    sessionsToProcess.put(userId, new SessionInfo(
                            session.getMessageId(),
                            session.getChannelId(),
                            session.getUser(),
                            session.isSubmitted()
                    ));
                }
            });
        }

        // Process each session
        toRemove.forEach(userId -> {
            SessionInfo sessionInfo = sessionsToProcess.get(userId);
            if (sessionInfo != null) {
                processSessionCleanup(sessionInfo);
            }

            // Remove the session from active tests
            synchronized (TestCommand.class) {
                TestCommand.getActiveTests().remove(userId);
            }

            System.out.println("[Cleanup] Removed session for user: " + userId);
        });
    }

    private void processSessionCleanup(SessionInfo sessionInfo) {
        try {
            TextChannel channel = shardManager.getTextChannelById(sessionInfo.channelId());
            if (channel == null) return;

            if (sessionInfo.isSubmitted()) {
                // For submitted tests: Just remove buttons
                channel.editMessageComponentsById(sessionInfo.messageId(), Collections.emptyList()).queue();
            } else {
                // For non-submitted tests: Show expired message and remove buttons
                shardManager.retrieveUserById(sessionInfo.user().getIdLong()).queue(user -> {
                    channel.editMessageEmbedsById(sessionInfo.messageId(),
                                    createExpiredEmbed(user, sessionInfo.user().getAsMention()))
                            .setComponents(Collections.emptyList())
                            .queue();
                }, error -> {
                    // Fallback if user can't be retrieved
                    channel.editMessageEmbedsById(sessionInfo.messageId(),
                                    createExpiredEmbed(null, null))
                            .setComponents(Collections.emptyList())
                            .queue();
                });
            }
        } catch (Exception e) {
            System.out.println("[SessionCleanup] Failed to process session for user: " + sessionInfo.user().getIdLong());
        }
    }

    private MessageEmbed createExpiredEmbed(User user, String mention) {
        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("⏰ Test Session Expired")
                .setColor(new Color(255, 82, 82))
                .setFooter("⏰ Test timed out • " +
                        Instant.now().atZone(ZoneId.of("America/New_York"))
                                .format(DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a")));

        if (user != null && mention != null) {
            builder.setDescription(mention + String.format(", your test session has expired after %d minutes of inactivity." +
                                    "\n\nUse /%s-test to start another test.",
                            Constants.testTimeoutInMinutes,
                            Constants.slashPrefix))
                    .setThumbnail(user.getEffectiveAvatarUrl());
        } else {
            builder.setDescription("Your test session has expired after " +
                            sessionTimeoutMinutes + " minutes of inactivity.")
                    .setFooter("Session ended • " +
                                    Instant.now().atZone(ZoneId.of("America/New_York"))
                                            .format(DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a")),
                            "https://cdn.jsdelivr.net/gh/twitter/twemoji@14.0.2/assets/72x72/23f0.png");
        }

        return builder.build();
    }


    private record SessionInfo(long messageId, long channelId, User user, boolean isSubmitted) {}
}