package com.Discord.DiscordBot.TextCommands;

import com.Discord.DiscordBot.Constants;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class ReportTextCommand {

    private static final long reportChannelId = 1322032766300454964L;

    // UserId - Time until they can send a report
    private static final HashMap<Long, Long> cooldownMap = new HashMap<>();

    public static void execute(MessageReceivedEvent event, String report) {

        if (event.getAuthor().isBot()) {
            return;
        }

        long userId = event.getAuthor().getIdLong();
        long currentTime = System.currentTimeMillis();

        // Check if user is in cooldown map
        if (cooldownMap.containsKey(userId) && cooldownMap.get(userId) > currentTime) {
            long timeLeft = (cooldownMap.get(userId) - currentTime) / 1000;
            event.getChannel().sendMessage(parseCooldownMessage(event,(int)timeLeft)).queue();
            return;
        }

        // Put user on cooldown
        cooldownMap.put(userId, currentTime + Constants.reportCooldown);
        startCooldownRemovalTimer(userId);

        // Send report to report channel
        TextChannel channel = event.getJDA().getTextChannelById(reportChannelId);
        assert channel != null : "Your text channel ID is wrong";

        channel.sendMessage("---------------------------------------------------------------\n" +
                event.getMember().getUser().getAsMention() + " from " + event.getGuild().getName() +
                " sent the following report:\n" + report).queue();

        event.getChannel().sendMessage("Report sent successfully. Thanks for your feedback!").queue();
    }

    private static void startCooldownRemovalTimer(long userId) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                cooldownMap.remove(userId);
            }
        }, Constants.reportCooldown);
    }

    // n is in seconds
    public static String parseCooldownMessage(MessageReceivedEvent event, int n) {
        String cooldownMessage;
        if (n/60.0 <= 1) {
            cooldownMessage = String.format(event.getMember().getUser().getAsMention() + ", you must wait %d seconds before using the %s-report command.",
                    n, Constants.slashPrefix);
        } else {
            cooldownMessage = String.format(event.getMember().getUser().getAsMention() + ", you must wait %d minutes before using the %s-report command.",
                    n/60, Constants.slashPrefix);
        }
        return cooldownMessage;
    }



}
