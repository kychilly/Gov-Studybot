package com.Discord.DiscordBot.listeners;

import com.Discord.DiscordBot.Constants;
import com.Discord.DiscordBot.TextCommands.*;
import com.Discord.DiscordBot.Units.*;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class UnitListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        User user = event.getAuthor();
        String message = event.getMessage().getContentRaw().trim();

        if (user.isBot()) return;

        if (!message.startsWith(Constants.prefix)) { return; } // Otherwise the message doesn't matter to the bot

        // The !Constants.prefix<number> command

        if (message.equalsIgnoreCase(Constants.prefix + "1")) {
            HandleUnitsCommand.execute(event, user, 1, QuestionBank.getUnit1Questions());
        } else if (message.equalsIgnoreCase(Constants.prefix + "2")) {
            HandleUnitsCommand.execute(event, user, 2, QuestionBank.getUnit2Questions());
        } else if (message.equalsIgnoreCase(Constants.prefix + "3")) {
            HandleUnitsCommand.execute(event, user, 3, QuestionBank.getUnit3Questions());
        } else if (message.equalsIgnoreCase(Constants.prefix + "4")) {
            HandleUnitsCommand.execute(event, user, 4, QuestionBank.getUnit4Questions());
        } else if (message.equalsIgnoreCase(Constants.prefix + "5")) {
            HandleUnitsCommand.execute(event, user, 5, QuestionBank.getUnit5Questions());
        } else if (message.equalsIgnoreCase(Constants.prefix + "6")) {
            HandleUnitsCommand.execute(event, user, 6, QuestionBank.getUnit6Questions());
        } else if (message.equalsIgnoreCase(Constants.prefix + "7")) {
            HandleUnitsCommand.execute(event, user, 7, QuestionBank.getUnit7Questions());
        } else if (message.equalsIgnoreCase(Constants.prefix + "8")) {
            HandleUnitsCommand.execute(event, user, 8, QuestionBank.getUnit8Questions());
        } else if (message.equalsIgnoreCase(Constants.prefix + "9")) {
            HandleUnitsCommand.execute(event, user, 9, QuestionBank.getUnit9Questions());
        } else if (message.equalsIgnoreCase(Constants.prefix + "10")) {
            HandleUnitsCommand.execute(event, user, 10, QuestionBank.getUnit10Questions());
        }

        // Less important commands
        else if (message.equalsIgnoreCase(Constants.prefix + "-info")) {
            InfoTextCommand.execute(event);
        } else if (message.equalsIgnoreCase(Constants.prefix + "-questionbank")) {
            QuestionBankTextCommand.execute(event);
        } else if (message.equalsIgnoreCase(Constants.prefix + "-resources")) {
            ResourcesTextCommand.execute(event);
        } else if (message.startsWith(Constants.prefix + "-report")) {
            ReportTextCommand.execute(event, message.substring(Constants.prefix.length() + 7));
        } else if (event.getAuthor().getIdLong() == 840216337119969301L && message.startsWith(Constants.prefix + "-remove")) {
            ResetPointsTextCommand.execute(event);
        } else {
            // In case the user doesnt know what the commands are
            event.getChannel().sendMessage("Hello " + Objects.requireNonNull(event.getMember()).getAsMention() + ", I believe you are trying to use one of the " + Constants.prefix + " commands. The commands I have available for use are: " +
                    "\n - "+ Constants.prefix + "<unit> : Gets a random unit <unit> question" +
                    "\n - " + Constants.prefix + "-info : Gets information about the AP " + Constants.slashPrefix.toUpperCase() + " Course" +
                    "\n - " + Constants.prefix + "-questionbank : Gets the questions I have" +
                    "\n - " + Constants.prefix + "-resources : Gets resources to help study for the AP " + Constants.slashPrefix.toUpperCase() + " course" +
                    "\n - " + Constants.prefix + "-report <Your message>: Report a bug or issue with this bot" +
                    "\n\nPlease feel free to use the `/" + Constants.slashPrefix + "-ask` or `/" + Constants.slashPrefix + "-help` command for more assistance on how to use these commands!").queue();
        }

    }


}