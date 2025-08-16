package com.Discord.DiscordBot.TextCommands;

import com.Discord.DiscordBot.Sessions.UserProfile;
import com.Discord.DiscordBot.Sessions.UserProfileManager;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.util.List;

// Secret command only I (kyche) can use
public class ResetPointsTextCommand {

    public static void execute(MessageReceivedEvent event) {
        User targetUser = null;
        try {
            // Get the list of mentions(only 1 mention)
            List<User> mentionedUsers = event.getMessage().getMentions().getUsers();
            targetUser = mentionedUsers.get(0);
            // Load the user's profile
            UserProfile profile = UserProfileManager.loadProfile(targetUser);

            // Reset points to 0 and update title
            profile.addPoints(-profile.getPoints()); // Subtract all points to reach 0

            // Send confirmation message
            event.getChannel().sendMessage(
                    "✅ Successfully reset points for " + targetUser.getAsMention() +
                            "\nTheir new title is: " + profile.getTitle() + " with " + profile.getPoints() + " points."
            ).queue();

        } catch (IOException e) { // This only happens when I use the command wrong
            event.getChannel().sendMessage(
                    "❌ Failed to reset points for " + targetUser.getAsMention() +
                            ": " + e.getMessage()
            ).queue();
        }
    }

}
