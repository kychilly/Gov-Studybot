package com.Discord.DiscordBot.commands;

import com.Discord.DiscordBot.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class HelpCommand {

    private static final String BUTTON_PREV = "help_prev";
    private static final String BUTTON_NEXT = "help_next";

    public static CommandData getCommandData() {
        return Commands.slash(Constants.slashPrefix + "-help", "Displays a help page");
    }

    public static void execute(SlashCommandInteractionEvent event) {
        sendHelpPage(event, 1);
    }

    public static void handleButtonInteraction(ButtonInteractionEvent event) {
        // Get current page from footer text
        String footer = event.getMessage().getEmbeds().get(0).getFooter().getText();
        assert footer != null;
        int currentPage = footer.contains("Page 1/2") ? 1 : 2;

        // Determine new page based on button clicked
        int newPage = event.getComponentId().equals(BUTTON_NEXT) ? 2 : 1;

        // Only update if we're actually changing pages
        if (newPage != currentPage) {
            sendHelpPage(event, newPage);
        }
        // No need for else case since we already deferred in the listener
    }

    private static void sendHelpPage(ButtonInteractionEvent event, int page) {
        EmbedBuilder embed = buildHelpEmbed(page);
        ActionRow buttons = buildButtons(page);

        // Use editOriginal() since we already deferred
        event.getHook().editOriginalEmbeds(embed.build())
                .setComponents(buttons)
                .queue();
    }

    private static void sendHelpPage(SlashCommandInteractionEvent event, int page) {
        EmbedBuilder embed = buildHelpEmbed(page);
        ActionRow buttons = buildButtons(page);

        if (event.isAcknowledged()) {
            event.getHook().editOriginalEmbeds(embed.build())
                    .setComponents(buttons)
                    .queue();
        } else {
            event.replyEmbeds(embed.build())
                    .setComponents(buttons)
                    .setEphemeral(false)
                    .queue();
        }
    }

    private static EmbedBuilder buildHelpEmbed(int page) {
        EmbedBuilder embed = new EmbedBuilder();
        String title = "📚 AP " + Constants.slashPrefix.toUpperCase() + " StudyBot Help";
        String description = "These are the commands you may use.";

        // Common AI help field for both pages
        String aiHelpField = "Use `/" + Constants.slashPrefix + "-ask` for:\n" +
                "- Help with using commands\n" +
                "- " + Constants.slashPrefix.toUpperCase() + " concepts\n" +
                "Example: `/" + Constants.slashPrefix + "-ask How do I use the test command?`";

        if (page == 1) {
            embed.setTitle(title)
                    .setDescription(description)
                    .setColor(0x1E90FF)
                    .addField("Slash Commands",
                            "• `/" + Constants.slashPrefix + "-practice-question`\n" +
                                    "• `/" + Constants.slashPrefix + "-test`\n" +
                                    "• `/" + Constants.slashPrefix + "-profile`\n" +
                                     "• `/" + Constants.slashPrefix + "-help`",
                            false);
        } else {
            embed.setTitle(title)
                    .setDescription(description)
                    .setColor(0x1E90FF)
                    .addField("Less Important Commands",
                            "• `" + Constants.prefix + "<unit>` - Replace <unit> with a number\n" +
                                    "• `" + Constants.prefix + "-questionbank`\n" +
                                    "• `" + Constants.prefix + "-resources`\n" +
                                    "• `" + Constants.prefix + "-info`\n" +
                                    "• `" + Constants.prefix + "-report <message>` - Replace <message> with your report\n",
                            false);
        }

        // Add consistent AI help section
        embed.addField("\u200B", "\u200B", false)
                .addField("💡 Instant AI Help Available", aiHelpField, false)
                .setFooter("Page " + page + "/2 | Good luck! \uD83E\uDEE1", null);

        embed.setThumbnail(Constants.collegeBoardThumbnail);
        return embed;
    }

    private static ActionRow buildButtons(int page) {
        Button prevButton = Button.secondary(BUTTON_PREV, "◀ Previous").withDisabled(page == 1);
        Button nextButton = Button.secondary(BUTTON_NEXT, "Next ▶").withDisabled(page == 2);
        return ActionRow.of(prevButton, nextButton);
    }
}