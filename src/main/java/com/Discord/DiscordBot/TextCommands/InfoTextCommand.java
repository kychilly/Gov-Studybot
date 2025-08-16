package com.Discord.DiscordBot.TextCommands;

import com.Discord.DiscordBot.Constants;
import com.Discord.DiscordBot.Z_Units.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class InfoTextCommand {

    public static void execute(MessageReceivedEvent event) {
        Constants.SendAvailableQuestions(event);
    }


}
