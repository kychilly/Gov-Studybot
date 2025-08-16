package com.Discord.DiscordBot.commands;

import com.Discord.DiscordBot.Constants;
import com.Discord.DiscordBot.Sessions.UserProfile;
import com.Discord.DiscordBot.Sessions.UserProfileManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class ProfileCommand extends ListenerAdapter {

    public static CommandData getCommandData() {
        return Commands.slash(Constants.slashPrefix + "-profile", "Gets your AP " +  Constants.slashPrefix.toUpperCase() + " profile!")
                .addOption(OptionType.USER, "user", "Who's profile do you want to see? Leave blank if you just want yours", false);
    }

    public static void execute(SlashCommandInteractionEvent event) {

        User targetUser = event.getOption("user") == null
                ? event.getUser()
                : Objects.requireNonNull(event.getOption("user")).getAsUser();

        try {
            UserProfile profile = UserProfileManager.loadProfile(targetUser);
            String footerDescription = getFooterDescription(profile);

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle(targetUser.getName() + "'s Profile")
                    .setThumbnail(targetUser.getEffectiveAvatarUrl())
                    .addField("🏆 Title", profile.getTitle(), true)
                    .addField("⭐ Points", String.valueOf(profile.getPoints()), true)
                    .setColor(Color.decode("#5865F2"))
                    .setFooter(footerDescription);

            event.replyEmbeds(embed.build()).queue();
        } catch (IOException e) {
            event.reply("❌ An error occurred while loading the profile.").setEphemeral(true).queue();
            e.printStackTrace();
        }
    }

    public static String getFooterDescription(UserProfile profile) {
        int points = profile.getPoints();

        if (points >= 1000) {
            return "You've reached the highest level - " + Constants.titles[4];
        } else if (points >= 500) {
            return (1000 - points) + " points until you're a " + Constants.titles[4];
        } else if (points >= 250) {
            return (500 - points) + " points until you're a " + Constants.titles[3];
        } else if (points >= 100) {
            return (250 - points) + " points until you're a " + Constants.titles[2];
        } else {
            return (100 - points) + " points until you're a " + Constants.titles[1];
        }

    }
}