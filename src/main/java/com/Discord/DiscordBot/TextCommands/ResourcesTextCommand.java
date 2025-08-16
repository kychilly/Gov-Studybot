package com.Discord.DiscordBot.TextCommands;

import com.Discord.DiscordBot.Constants;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ResourcesTextCommand {

    public static void execute(MessageReceivedEvent event) {
        MessageEmbed resourcesEmbed = Constants.createResourcesEmbed();
        event.getChannel().sendMessageEmbeds(resourcesEmbed).queue();
    }

}
