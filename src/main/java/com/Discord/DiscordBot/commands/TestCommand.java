package com.Discord.DiscordBot.commands;

import com.Discord.DiscordBot.Constants;
import com.Discord.DiscordBot.Sessions.TestSession;
import com.Discord.DiscordBot.Sessions.UserProfile;
import com.Discord.DiscordBot.Sessions.UserProfileManager;
import com.Discord.DiscordBot.Units.Question;
import com.Discord.DiscordBot.Units.QuestionBank;
import com.Discord.DiscordBot.listeners.ButtonListener;
import com.Discord.DiscordBot.A_IndividualMethods.CalculatePoints;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TestCommand {

    private static final Map<Long, TestSession> activeTests = new HashMap<>();

    public static Map<Long, TestSession> getActiveTests() {
        return activeTests;
    }

    public static SlashCommandData getCommandData() {
        return Commands.slash(Constants.slashPrefix + "-test", "Take a practice AP " + Constants.slashPrefix.toUpperCase() + " test")
                .addOptions(
                        new OptionData(OptionType.INTEGER, "num-questions", "Number of questions to test. (default: 10)", false)
                                .addChoice("5 Questions", 5)
                                .addChoice("10 Questions", 10)
                                .addChoice("15 Questions", 15)
                                .addChoice("20 Questions", 20)
                                .addChoice("50 Questions", 50),
                        createUnitOption()
                );
    }

    private static OptionData createUnitOption() {
        OptionData unitOption = new OptionData(OptionType.INTEGER, "unit", "Specific unit to test", false);

        // Dynamically add unit choices based on Constants.numUnits
        for (int i = 1; i <= Constants.numUnits; i++) {
            unitOption.addChoice("Unit " + i, i);
        }

        return unitOption;
    }

    public static void execute(SlashCommandInteractionEvent event) {
        if (event.getUser().isBot()) return;

        int numQuestions = event.getOption("num-questions") != null ?
                Objects.requireNonNull(event.getOption("num-questions")).getAsInt() : 10;
        Integer unit = event.getOption("unit") != null ?
                Objects.requireNonNull(event.getOption("unit")).getAsInt() : null;

        if (unit != null && unit > Constants.numUnits) { // In case they type in the command
            event.reply("Unit " + unit + " is not a Unit in the AP " + Constants.slashPrefix.toUpperCase() + " course.").setEphemeral(true).queue();
            return;
        }

        List<Question> questions = getQuestionsForUnit(unit, numQuestions);

        if (questions.isEmpty()) {
            event.reply("No questions available for the selected unit.").setEphemeral(true).queue();
            return;
        }

        event.deferReply().queue(hook -> {
            // Get and remove previous test in one operation
            TestSession previousTest = activeTests.remove(event.getUser().getIdLong());
            boolean hadPreviousTest = previousTest != null;

            // Create new session
            TestSession session = new TestSession(questions, event.getUser());
            activeTests.put(event.getUser().getIdLong(), session);

            // Send the test first to get its message reference
            hook.sendMessageEmbeds(createTestEmbed(session))
                    .setComponents(createActionRows(session))
                    .queue(newTestMessage -> {
                        // Store IDs for the new test
                        session.setMessageId(newTestMessage.getIdLong());
                        session.setChannelId(newTestMessage.getChannel().getIdLong());

                        // Send appropriate notification
                        if (hadPreviousTest) {
                            String message = previousTest.isSubmitted()
                                    ? "⌛ " + event.getUser().getAsMention() + ", here is your new test!"
                                    : "⌛ " + event.getUser().getAsMention() + ", your previous test was ended as you started a new test.";

                            hook.sendMessage(message)
                                    .setEphemeral(true)
                                    .queue(msg -> msg.delete().queueAfter(3, TimeUnit.SECONDS));
                        }

                        // If there was a previous test, edit its message with link to new test
                        if (hadPreviousTest && !previousTest.isSubmitted()) {
                            editPreviousTestMessage(event, previousTest, newTestMessage);
                        }


                    });
        });
    }

    // Button interaction for TestCommand
    public static void handleButtonInteraction(ButtonInteractionEvent event) {
        long userId = event.getUser().getIdLong();

        // First check if the user has an active test
        if (!activeTests.containsKey(userId)) {
            sendEphemeralReply(event, "You don't have an active test session or it has expired.");
            return;
        }

        TestSession session = activeTests.get(userId);

        // Check if the button is from the user's test message
        if (event.getMessageIdLong() != session.getMessageId()) {
            sendEphemeralReply(event, "This isn't your test! Please go to your own test or start a new one with `/" + Constants.slashPrefix + "-test`.");
            return;
        }

        // Reset inactivity timer on every button click
        // Don't think this can ever be null
        session.setLastActivityTime(System.currentTimeMillis()); // 1755075541021+ ish milliseconds passed since 1970

        String buttonId = event.getComponentId();

        if (session.isSubmitted() && !buttonId.startsWith("test_review_") && !buttonId.equals("test_results_review")) {
            sendEphemeralReply(event, "This test has already been submitted. Use the review button to see your answers.");
            return;
        }

        // Defer the reply if we haven't already(just in case, should never happen though)
        if (!event.isAcknowledged()) {
            event.deferEdit().queue();
        }

        switch (buttonId) {
            case "test_prev":
                session.previousQuestion();
                updateTestMessage(event, session);
                break;

            case "test_next":
                session.nextQuestion();
                updateTestMessage(event, session);
                break;

            case "test_submit":
                session.setSubmitted(true);
                showTestResults(event, session);
                break;

            case "test_preview_review":  // before submitting
                showPreviewReview(event, session);
                break;

            case "test_results_review":  // Full review button (after submission)
                session.setCurrentIndex(0);
                showFullReview(event, session);
                break;

            case "test_preview_prev":
                session.previousQuestion();
                showPreviewReview(event, session);
                break;

            case "test_preview_next":
                session.nextQuestion();
                showPreviewReview(event, session);
                break;

            case "test_preview_back":
                updateTestMessage(event, session);
                break;

            case "test_review_prev":
                session.previousQuestion();
                showFullReview(event, session);
                break;

            case "test_review_next":
                session.nextQuestion();
                showFullReview(event, session);
                break;

            case "test_review_close":
                showTestResults(event, session);
                break;

            default:
                if (buttonId.startsWith("test_answer_")) {
                    String answer = buttonId.substring("test_answer_".length());
                    session.setAnswer(session.getCurrentIndex(), answer.toUpperCase());
                    updateTestMessage(event, session);
                }
        }
    }


    private static void updateTestMessage(ButtonInteractionEvent event, TestSession session) {
        if (event.isAcknowledged()) {
            event.getHook().editOriginalEmbeds(createTestEmbed(session))
                    .setComponents(createActionRows(session))
                    .queue();
        } else {
            event.editMessageEmbeds(createTestEmbed(session))
                    .setComponents(createActionRows(session))
                    .queue();
        }
    }

    private static void showTestResults(ButtonInteractionEvent event, TestSession session) {
        int score = session.calculateScore(); // Score in terms of like n/m, not the points
        int total = session.getTotalQuestions();
        double percentage = (double) score / total * 100;
        int pointsEarned = CalculatePoints.calculateTestPoints(session);

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Test Results")
                .setDescription(String.format("You scored **%d/%d** (%.1f%%)\n%s", score, total, percentage, getScoreMessage(percentage)))
                .addField("", getOtherScoreMessage(percentage), false)
                .setFooter(String.format("Points earned: %d", pointsEarned))
                .setThumbnail(event.getUser().getEffectiveAvatarUrl())
                .setColor(percentage >= Constants.percentageFor3 ? 0x00FF00 : 0xFF0000);

        // Add points here(add by using the pointsEarned thing)
        try {
            UserProfile profile = UserProfileManager.loadProfile(event.getUser());
            profile.addPoints(pointsEarned);
        } catch (IOException e) {
            System.err.println("Failed to update profile: " + e.getMessage());
        }

        // Now show the full review button
        Button reviewButton = Button.secondary("test_results_review", "🔍 Review Test with Answers");

        event.getHook().editOriginalEmbeds(embed.build())
                .setComponents(ActionRow.of(reviewButton))
                .queue();
    }

    private static void showFullReview(ButtonInteractionEvent event, TestSession session) {
        int currentIndex = session.getCurrentIndex();
        Question question = session.getQuestions().get(currentIndex);
        String userAnswer = session.getUserAnswerByIndex(currentIndex);
        String correctAnswer = question.getCorrectAnswer();
        boolean isCorrect = correctAnswer.equalsIgnoreCase(userAnswer);

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Review - Question " + (currentIndex + 1) + "/" + session.getTotalQuestions())
                .setDescription(question.getQuestion()) // Using the question description getting method from ButtonListener getAnswerText(Question, String(a b c or d)
                .addField("Your Answer", userAnswer != null ? userAnswer + ") " + ButtonListener.getAnswerText(question, userAnswer): "Not answered", false)
                .addField("Correct Answer", correctAnswer + ") " + ButtonListener.getAnswerText(question, correctAnswer), false)
                .setColor(isCorrect ? 0x00FF00 : 0xFF0000)
                .setFooter(isCorrect ? "Correct!" : "Incorrect");

        List<Button> buttons = new ArrayList<>();

        if (currentIndex > 0) {
            buttons.add(Button.primary("test_review_prev", "◀ Previous"));
        }

        if (currentIndex < session.getTotalQuestions() - 1) {
            buttons.add(Button.primary("test_review_next", "Next ▶"));
        }

        buttons.add(Button.danger("test_review_close", "✖ Close"));

        event.getHook().editOriginalEmbeds(embed.build())
                .setComponents(ActionRow.of(buttons))
                .queue();
    }

    private static List<Question> getQuestionsForUnit(Integer unit, int count) {
        List<Question> questions;

        if (unit == null) {
            // Get questions from all units
            questions = new ArrayList<>(QuestionBank.getQuestionBank());
        } else {
            // Get questions from specific unit
            switch (unit) {
                case 1: questions = QuestionBank.getUnit1Questions(); break;
                case 2: questions = QuestionBank.getUnit2Questions(); break;
                case 3: questions = QuestionBank.getUnit3Questions(); break;
                case 4: questions = QuestionBank.getUnit4Questions(); break;
                case 5: questions = QuestionBank.getUnit5Questions(); break;
                case 6: questions = QuestionBank.getUnit6Questions(); break;
                case 7: questions = QuestionBank.getUnit7Questions(); break;
                case 8: questions = QuestionBank.getUnit8Questions(); break;
                case 9: questions = QuestionBank.getUnit9Questions(); break;
                case 10: questions = QuestionBank.getUnit10Questions(); break;
                default: return Collections.emptyList();
            }
        }

        // Shuffle and limit to requested count
        Collections.shuffle(questions);
        return questions.stream().limit(count).collect(Collectors.toList());
    }

    private static MessageEmbed createTestEmbed(TestSession session) {
        Question current = session.getCurrentQuestion();
        int currentIndex = session.getCurrentIndex();
        int total = session.getTotalQuestions();
        String userAnswer = session.getUserAnswerByIndex(currentIndex);

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Question " + (currentIndex + 1) + " of " + total)
                .setDescription(session.getUser().getAsMention() + ", " + current.getQuestion())
                .addField("A", current.getOptionA(), false)
                .addField("B", current.getOptionB(), false)
                .addField("C", current.getOptionC(), false)
                .addField("D", current.getOptionD(), false)
                .setFooter("Your answer: " + (userAnswer != null ? userAnswer : "__"));


        return embed.build();
    }

    private static void showPreviewReview(ButtonInteractionEvent event, TestSession session) {
        int currentIndex = session.getCurrentIndex();
        Question question = session.getQuestions().get(currentIndex);
        String userAnswer = session.getUserAnswerByIndex(currentIndex);

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Preview - Question " + (currentIndex + 1) + "/" + session.getTotalQuestions())
                .setDescription(question.getQuestion())
                .addField("Your Answer", userAnswer != null ? userAnswer : "Not answered yet", false)
                .setFooter("Submit the test to see correct answers");

        // Navigation buttons
        List<Button> buttons = new ArrayList<>();

        if (currentIndex > 0) {
            buttons.add(Button.primary("test_preview_prev", "◀ Previous"));
        }

        if (currentIndex < session.getTotalQuestions() - 1) {
            buttons.add(Button.primary("test_preview_next", "Next ▶"));
        }

        buttons.add(Button.primary("test_preview_back", "↩ Back to Test"));

        event.getHook().editOriginalEmbeds(embed.build())
                .setComponents(ActionRow.of(buttons))
                .queue();
    }

    private static List<ActionRow> createActionRows(TestSession session) {
        List<Button> answerButtons = Arrays.asList(
                Button.secondary("test_answer_a", "A"),
                Button.secondary("test_answer_b", "B"),
                Button.secondary("test_answer_c", "C"),
                Button.secondary("test_answer_d", "D")
        );

        List<Button> navButtons = new ArrayList<>();
        if (session.getCurrentIndex() > 0) {
            navButtons.add(Button.primary("test_prev", "◀ Previous"));
        }
        if (session.getCurrentIndex() < session.getTotalQuestions() - 1) {
            navButtons.add(Button.primary("test_next", "Next ▶"));
        } else {
            navButtons.add(Button.success("test_submit", "✅ Submit Test"));
            navButtons.add(Button.secondary("test_preview_review", "👀 Preview Answers"));
        }

        return Arrays.asList(
                ActionRow.of(answerButtons),
                ActionRow.of(navButtons)
        );
    }

    private static void sendEphemeralReply(ButtonInteractionEvent event, String message) {
        if (event.isAcknowledged()) {
            event.getHook().sendMessage(message).setEphemeral(true).queue();
        } else {
            event.reply(message).setEphemeral(true).queue();
        }
    }

    // This should be different for all subjects.
    public static String getScoreMessage(double percentage) {
        if (percentage >= Constants.scorePercents[0]) {
            return "Your score likely earns you a **5**.";
        } else if (percentage >= Constants.scorePercents[1]) {
            return "Your score likely earns you a **4**.";
        } else if (percentage >= Constants.scorePercents[2]) {
            return "Your score likely earns you a **3**.";
        } else if (percentage >= Constants.scorePercents[3]) {
            return "Your score likely earns you a **2**.";
        } else {
            return "Your score likely earns you a **1**.";
        }
    }

    public static String getOtherScoreMessage(double percentage) {
        if (percentage >= Constants.scorePercents[0]) {
            return Constants.FIVE_SCORE_MESSAGES.get((int)(Math.random() * Constants.FIVE_SCORE_MESSAGES.size()));
        } else if (percentage >= Constants.scorePercents[1]) {
            return Constants.FOUR_SCORE_MESSAGES.get((int)(Math.random() * Constants.FOUR_SCORE_MESSAGES.size()));
        } else if (percentage >= Constants.scorePercents[2]) {
            return Constants.THREE_SCORE_MESSAGES.get((int)(Math.random() * Constants.THREE_SCORE_MESSAGES.size()));
        } else if (percentage >= Constants.scorePercents[3]) {
            return Constants.TWO_SCORE_MESSAGES.get((int)(Math.random() * Constants.TWO_SCORE_MESSAGES.size()));
        } else {
            return Constants.ONE_SCORE_MESSAGES.get((int)(Math.random() * Constants.ONE_SCORE_MESSAGES.size()));
        }
    }

    // Test method so far
    private static void editPreviousTestMessage(SlashCommandInteractionEvent event, TestSession previousTest, Message newTestMessage) {
        try {
            String newTestLink = String.format("[Continue to your new test](%s)",
                    newTestMessage.getJumpUrl());

            event.getJDA().getTextChannelById(previousTest.getChannelId())
                    .editMessageEmbedsById(previousTest.getMessageId(),
                            new EmbedBuilder()
                                    .setTitle("⏰ Test Session Ended")
                                    .setDescription(String.format(
                                            "%s, this test was ended because you started a new one.\n%s",
                                            event.getUser().getAsMention(),
                                            newTestLink))
                                    .setColor(new Color(255, 82, 82))
                                    .setThumbnail(event.getUser().getEffectiveAvatarUrl())
                                    .setFooter("Session ended • " +
                                                    Instant.now()
                                                            .atZone(ZoneId.of("America/New_York"))
                                                            .format(DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a")),
                                            "https://cdn.jsdelivr.net/gh/twitter/twemoji@14.0.2/assets/72x72/23f0.png")
                                    .build()
                    ).setComponents(Collections.emptyList()).queue();
        } catch (Exception e) {
            System.out.println("Failed to edit previous test message: " + e.getMessage());
        }
    }
}