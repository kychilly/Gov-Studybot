package com.Discord.DiscordBot.commands;

import com.Discord.DiscordBot.Constants;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {

    static List<CommandData> commandData = new ArrayList<>();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();

        if (command.equalsIgnoreCase(Constants.slashPrefix + "-test")) {
            TestCommand.execute(event); // Needs work
        } else if (command.equalsIgnoreCase(Constants.slashPrefix + "-practice-question")) {
            UnitsCommand.execute(event);
        } else if (command.equalsIgnoreCase(Constants.slashPrefix + "-ask")) {
            GPTCommand.execute(event);
        } else if (command.equalsIgnoreCase(Constants.slashPrefix + "-profile")) {
            ProfileCommand.execute(event);
        } else if (command.equalsIgnoreCase(Constants.slashPrefix + "-help")) {
            HelpCommand.execute(event);
        }

    }

    public static void initializeCommands() {
        commandData.add(TestCommand.getCommandData());
        commandData.add(UnitsCommand.getCommandData());
        commandData.add(GPTCommand.getCommandData());
        commandData.add(ProfileCommand.getCommandData());
        commandData.add(HelpCommand.getCommandData());
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {

        //updates all commands in guilds
        event.getGuild().updateCommands()
                .addCommands(commandData)
                .queue(
                        success -> System.out.println("✅ New commands registered in " + event.getGuild().getName()),
                        error -> System.err.println("❌ Failed in " + event.getGuild().getName() + ": " + error.getMessage())
                );
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {

        //updates all commands in guilds
        event.getGuild().updateCommands()
                .addCommands(commandData)
                .queue(
                        success -> System.out.println("✅ New commands registered in " + event.getGuild().getName()),
                        error -> System.err.println("❌ Failed in " + event.getGuild().getName() + ": " + error.getMessage())
                );
    }
}