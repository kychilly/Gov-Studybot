package com.Discord.DiscordBot.TextCommands;

import com.Discord.DiscordBot.Constants;
import com.Discord.DiscordBot.Units.Question;
import com.Discord.DiscordBot.Units.QuestionBank;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageEditBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.Discord.DiscordBot.listeners.ButtonListener.getAnswerText;

public class QuestionBankTextCommand {

    private static final int QUESTIONS_PER_PAGE = 5;
    private static final Map<Long, Integer> userPageStates = new HashMap<>();
    private static final Map<Long, Integer> userUnitStates = new HashMap<>();

    public static void execute(MessageReceivedEvent event) {
        User user = event.getMember().getUser();

        // Get questions from your existing QuestionBank
        ArrayList<Question> allQuestions = QuestionBank.getQuestionBank();

        // Filter for Unit 1 questions by default
        List<Question> unit1Questions = allQuestions.stream()
                .filter(q -> q.getUnit() == 1)
                .collect(Collectors.toList());

        // Store user's page and unit state
        userPageStates.put(user.getIdLong(), 0);
        userUnitStates.put(user.getIdLong(), 1);

        sendQuestionBankPage(event, unit1Questions, 0, 1);
    }

    private static void sendQuestionBankPage(MessageReceivedEvent event, List<Question> questions, int page, int unit) {
        MessageCreateBuilder messageBuilder = createQuestionBankMessage(questions, page, unit);
        event.getChannel().sendMessage(messageBuilder.build()).queue();
    }

    private static void updateQuestionBankPage(ButtonInteractionEvent event, List<Question> questions, int page, int unit) {
        MessageEditBuilder messageBuilder = new MessageEditBuilder()
                .setEmbeds(createQuestionBankEmbed(questions, page, unit).build())
                .setComponents(createActionRows(unit, page, questions.size()));

        event.getHook().editOriginal(messageBuilder.build()).queue();
    }

    private static MessageCreateBuilder createQuestionBankMessage(List<Question> questions, int page, int unit) {
        return new MessageCreateBuilder()
                .setEmbeds(createQuestionBankEmbed(questions, page, unit).build())
                .setComponents(createActionRows(unit, page, questions.size()));
    }

    private static EmbedBuilder createQuestionBankEmbed(List<Question> questions, int page, int unit) {
        int totalPages = (int) Math.ceil((double) questions.size() / QUESTIONS_PER_PAGE);
        page = Math.max(0, Math.min(page, totalPages - 1));

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(String.format("Unit %d Questions (Page %d/%d)", unit, page + 1, totalPages))
                .setColor(Color.BLUE);

        int startIdx = page * QUESTIONS_PER_PAGE;
        int endIdx = Math.min(startIdx + QUESTIONS_PER_PAGE, questions.size());

        if (questions.isEmpty()) {
            embed.setDescription("No questions available for this unit.");
        } else {
            for (int i = startIdx; i < endIdx; i++) {
                Question q = questions.get(i);
                String divider = (i < endIdx - 1) ? "\n-------------------------" : "";

                embed.addField(
                        String.format("(ID: %d) Question %d - %s", q.getQuestionId(), i + 1, q.getQuestionDifficulty()),
                        String.format("%s\nA) %s\nB) %s\nC) %s\nD) %s\nAnswer: ||%s - %s||%s",
                                q.getQuestion(), q.getOptionA(), q.getOptionB(),
                                q.getOptionC(), q.getOptionD(), q.getCorrectAnswer(), getAnswerText(q, q.getCorrectAnswer()), divider),
                        false
                );
            }

            embed.setFooter(String.format("Showing questions %d-%d of %d in Unit %d",
                    startIdx + 1, endIdx, questions.size(), unit));
        }

        return embed;
    }

    private static List<ActionRow> createActionRows(int unit, int page, int totalQuestions) {
        int totalPages = (int) Math.ceil((double) totalQuestions / QUESTIONS_PER_PAGE);

        // Navigation buttons
        Button prevButton = Button.primary("qbank_prev", "◀").withDisabled(page == 0);
        Button nextButton = Button.primary("qbank_next", "▶").withDisabled(page >= totalPages - 1 || totalQuestions == 0);

        // Create unit buttons dynamically based on Constants.numUnits
        List<Button> unitButtons = new ArrayList<>();
        for (int i = 1; i <= Constants.numUnits; i++) {
            unitButtons.add(
                    Button.secondary("qbank_unit" + i, "Unit " + i)
                            .withDisabled(unit == i)
            );
        }

        // Create action rows
        ActionRow navigationRow = ActionRow.of(prevButton, nextButton);

        // Split unit buttons into chunks of 5 (max buttons per row)
        List<ActionRow> unitRows = new ArrayList<>();
        for (int i = 0; i < unitButtons.size(); i += 5) {
            List<Button> rowButtons = unitButtons.subList(i, Math.min(i + 5, unitButtons.size()));
            unitRows.add(ActionRow.of(rowButtons));
        }

        // Combine all rows
        List<ActionRow> actionRows = new ArrayList<>();
        actionRows.add(navigationRow);
        actionRows.addAll(unitRows);

        return actionRows;
    }

    public static void handleButtonInteraction(ButtonInteractionEvent event) {
        String buttonId = event.getComponentId();
        User user = event.getUser();

        if (user.isBot()) return;

        long userId = user.getIdLong();
        int currentPage = userPageStates.getOrDefault(userId, 0);
        int currentUnit = userUnitStates.getOrDefault(userId, 1);

        ArrayList<Question> allQuestions = QuestionBank.getQuestionBank();
        List<Question> unitQuestions;

        // Handle navigation buttons
        if (buttonId.equals("qbank_prev")) {
            currentPage--;
        } else if (buttonId.equals("qbank_next")) {
            currentPage++;
        }
        // Handle unit selection buttons
        else if (buttonId.startsWith("qbank_unit")) {
            try {
                currentUnit = Integer.parseInt(buttonId.substring("qbank_unit".length()));
                currentPage = 0;
            } catch (NumberFormatException e) {
                return; // Invalid unit button
            }
        } else {
            return; // Not our button
        }

        // Filter questions for the selected unit
        final int unitFilter = currentUnit;
        unitQuestions = allQuestions.stream()
                .filter(q -> q.getUnit() == unitFilter)
                .collect(Collectors.toList());

        // Update user state
        userPageStates.put(userId, currentPage);
        userUnitStates.put(userId, currentUnit);

        // Update the existing message
        updateQuestionBankPage(event, unitQuestions, currentPage, currentUnit);
    }
}