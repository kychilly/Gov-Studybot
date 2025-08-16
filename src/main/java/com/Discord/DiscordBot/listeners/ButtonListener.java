package com.Discord.DiscordBot.listeners;

import com.Discord.DiscordBot.Constants;
import com.Discord.DiscordBot.Sessions.UserProfile;
import com.Discord.DiscordBot.Sessions.UserProfileManager;
import com.Discord.DiscordBot.TextCommands.QuestionBankTextCommand;
import com.Discord.DiscordBot.Units.*;
import com.Discord.DiscordBot.commands.HelpCommand;
import com.Discord.DiscordBot.commands.TestCommand;
import com.Discord.DiscordBot.A_IndividualMethods.CalculatePoints;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageEditBuilder;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.Discord.DiscordBot.Units.ActiveQuestionTracker.getUserByMessageId;
import static com.Discord.DiscordBot.Units.CheckQuestionAnswer.wrongAnswers;

public class ButtonListener extends ListenerAdapter {

    // The names really should be "previous questions choices"
    public static Map<User, Question> incorrectUserQuestions = new HashMap<>();
    public static Map<User, String> incorrectUserAnswers = new HashMap<>();
    public static Map<Long, User> incorrectMessageIds = new HashMap<>();


    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        String buttonId = event.getComponentId();
        User user = event.getUser();

        if (user.isBot()) return;

        // Defer the reply immediately to prevent interaction failure
        event.deferEdit().queue();

        try {
            if (buttonId.startsWith("answer_")) {
                handleAnswer(event, user, buttonId); // Code for button answer
            } else if (buttonId.equals("new_question")) {
                handleNewQuestion(event, user); // For entirely new question
            } else if (buttonId.equals("review_question")) {
                handleReviewQuestion(event, user); // For review button
            } else if (buttonId.startsWith("qbank_")) {
                QuestionBankTextCommand.handleButtonInteraction(event); // Question bank command
            } else if (buttonId.startsWith("test_")) {
                TestCommand.handleButtonInteraction(event); // Test command
            } else if (buttonId.startsWith("help_")) {
                HelpCommand.handleButtonInteraction(event);
            }

        } catch (Exception e) {
            event.getHook().sendMessage("An error occurred while processing your request.")
                    .setEphemeral(true).queue();
            event.getChannel().sendMessage("YOU HAVE A MASSIVE BUG: " + e).queue();
        }
    }

    private void handleAnswer(ButtonInteractionEvent event, User user, String buttonId) {
        if (!ActiveQuestionTracker.hasActiveQuestion(user)) {
            event.getHook().sendMessage("You don't have an active question. Use `" + Constants.prefix + "<number>` or `/" + Constants.slashPrefix + "-practice-question` to get started!")
                    .setEphemeral(true).queue();
            return;
        }
        // Now, check if this is your active question
        if (!user.equals(getUserByMessageId(event.getMessage().getIdLong()))) { // Checks if the current user isnt equal to whatever the hashmap had in store for that specific message
            event.getHook().sendMessage("This is not your active question to answer!")
                    .setEphemeral(true).queue();
            return;
        }

        String answer = buttonId.substring("answer_".length());
        Question question = ActiveQuestionTracker.getActiveQuestion(user);
        boolean isCorrect = CheckQuestionAnswer.checkAnswer(question, answer);

        // Stores the user's previous questions and answers for possible review (name possibly misleading)
        incorrectUserQuestions.put(user, question);
        incorrectUserAnswers.put(user, answer);
        incorrectMessageIds.put(event.getMessageIdLong(), user);

        // Points!!!
        // Going to take it from ActiveQuestions, can also take it from incorrectUserQuestions for future ref
        int points = isCorrect ? CalculatePoints.calculatePoints(ActiveQuestionTracker.getActiveQuestion(user)) : 0;

        if (isCorrect) {
            try {
                UserProfile profile = UserProfileManager.loadProfile(user);
                profile.addPoints(points);
            } catch (IOException e) {
                System.err.println("Failed to update profile: " + e.getMessage());
            }
        }

        // Build the sussy embed
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle(isCorrect ? "Correct Answer! 🎉" : "Incorrect Answer ❌")
                .setColor(isCorrect ? Color.GREEN : Color.RED)
                .setDescription(isCorrect
                        ? user.getAsMention() + " **Well done!** You got it right!"
                        : getRandomWrongAnswer())
                .addField("", // Empty field name
                        "Your answer: " + String.format("||%s) %s||",
                                incorrectUserAnswers.get(user).toUpperCase(),
                                getAnswerText(question, incorrectUserAnswers.get(user))) +
                                "\n" + // Newline instead of a new field
                                "Correct answer: " + String.format("||%s) %s||",
                                question.getCorrectAnswer(),
                                getAnswerText(question, question.getCorrectAnswer())),
                        false
                )
                .setFooter(String.format("Points Earned: %d   |   (ID: %d)", points, incorrectUserQuestions.get(user).getQuestionId()));

        MessageEditBuilder messageBuilder = new MessageEditBuilder()
                .setEmbeds(embedBuilder.build());

        // Sets buttons up for result
            messageBuilder.setActionRow(
                    Button.primary("new_question", "Try Another Question"),
                    Button.danger("review_question", "Review Question")
            );

        // FIRST update the message, THEN remove from tracking
        event.getHook().editOriginal(messageBuilder.build()).queue(success -> {
            // Runs AFTER the message is updated (
            ActiveQuestionTracker.removeActiveQuestionByButtonAnswer(user, event.getMessageIdLong());
        });
    }

    private void handleReviewQuestion(ButtonInteractionEvent event, User user) {

        Message message = event.getMessage();
        long messageID = message.getIdLong(); // This is the messageID of what the message the button was attached to was

        Question question = incorrectUserQuestions.get(user);
        if (question == null) {
            handleActiveQuestion(event, user);
            return;
        }
        if (!user.equals(incorrectMessageIds.get(messageID))) {
            event.getHook().sendMessage("This is not your question to review!")
                    .setEphemeral(true).queue();
            return;
        }

        int unit = question.getUnit();

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle(String.format("Unit %d Question Review (%s)", unit, question.getQuestionDifficulty()))
                .setColor(Color.YELLOW)
                .setDescription(user.getAsMention() + ", " + question.getQuestion())
                .addField("Options:", // Single field or else sus choice placement
                        "A) " + question.getOptionA() + "\n" +
                                "B) " + question.getOptionB() + "\n" +
                                "C) " + question.getOptionC() + "\n" +
                                "D) " + question.getOptionD(),
                        false
                )
                .addField("", // Empty field name
                        "Your answer: " + String.format("||%s) %s||",
                                incorrectUserAnswers.get(user).toUpperCase(),
                                getAnswerText(question, incorrectUserAnswers.get(user))) +
                                "\n" + // Newline instead of a new field
                                "Correct answer: " + String.format("||%s) %s||",
                                question.getCorrectAnswer(),
                                getAnswerText(question, question.getCorrectAnswer())),
                        false
                )
                .setFooter(String.format("(ID: %d)", incorrectUserQuestions.get(user).getQuestionId()));

        MessageEditBuilder messageBuilder = new MessageEditBuilder()
                .setEmbeds(embedBuilder.build());
        messageBuilder.setActionRow(
                Button.primary("new_question", "Try Another Question"));

        event.getHook().editOriginal(messageBuilder.build()).queue();
    }

    private void handleNewQuestion(ButtonInteractionEvent event, User user) {

        // Checks to make sure before you get to click again, if you have an active question. If so, dont let them get a new question
        if (ActiveQuestionTracker.hasActiveQuestion(user)) {
            handleActiveQuestion(event, user);
            return;
        }

        // A bit of spagetti code, gets the same unit for the new question button
        // Implementing different question ID
        int unit;
        int prevQuestionId = -1;

        // Try to get last incorrect question
        Question previousQuestion = incorrectUserQuestions.get(user);
        if (previousQuestion != null) {
            unit = previousQuestion.getUnit();
            prevQuestionId = previousQuestion.getQuestionId();
        } else {
            // If no previous question, pick a random unit
            unit = (int)(Math.random() * Constants.numUnits) + 1;
        }

        Question question;
        if (unit == 1) {
            question = QuestionBank.getRandomQuestion(QuestionBank.getUnit1Questions(), prevQuestionId);
        } else if (unit == 2) {
            question = QuestionBank.getRandomQuestion(QuestionBank.getUnit2Questions(), prevQuestionId);
        } else if (unit == 3) {
            question = QuestionBank.getRandomQuestion(QuestionBank.getUnit3Questions(), prevQuestionId);
        } else if (unit == 4) {
            question = QuestionBank.getRandomQuestion(QuestionBank.getUnit4Questions(), prevQuestionId);
        } else if (unit == 5) {
            question = QuestionBank.getRandomQuestion(QuestionBank.getUnit3Questions(), prevQuestionId);
        } else if (unit == 6) {
            question = QuestionBank.getRandomQuestion(QuestionBank.getUnit3Questions(), prevQuestionId);
        } else if (unit == 7) {
            question = QuestionBank.getRandomQuestion(QuestionBank.getUnit3Questions(), prevQuestionId);
        } else if (unit == 8) {
            question = QuestionBank.getRandomQuestion(QuestionBank.getUnit3Questions(), prevQuestionId);
        } else if (unit == 9) {
            question = QuestionBank.getRandomQuestion(QuestionBank.getUnit3Questions(), prevQuestionId);
        }  else if (unit == 10) {
            question = QuestionBank.getRandomQuestion(QuestionBank.getUnit3Questions(), prevQuestionId);
        } else {
            event.getChannel().sendMessage("I HAVE A MASSIVE BUG IN handleNewQuestion in ButtonListener. \nUnit is " + unit).queue();
            return;
        }

        if (incorrectUserAnswers.get(user) != null) { // Should always remove last(never null unless first interaction)
            incorrectUserAnswers.remove(user);
            incorrectUserQuestions.remove(user);
            // Also remove the user from incorrectMessageIds (needs a better method here)
            Long entryToRemove = -1L;
            for (Map.Entry<Long, User> entry : incorrectMessageIds.entrySet()) {
                if (entry.getValue().equals(user)) {
                    entryToRemove = entry.getKey();
                }
            }
            if (entryToRemove == -1L) {
                event.getChannel().sendMessage("MASSIVE BUG, PLEASE DONT DO WHATEVER U JUST DID LOL (line 237 or so of buttonListener handleNewQuestion").queue();
            }
            incorrectMessageIds.remove(entryToRemove);
        }


        if (question == null) {
            event.getHook().sendMessage("No more questions available for Unit 1.")
                    .setEphemeral(true).queue();
            return;
        }

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle(String.format("Unit %s Question (%s)", unit, question.getQuestionDifficulty()))
                .setColor(Color.BLUE)
                .setDescription(user.getAsMention() + ", " + question.getQuestion())
                .addField("Options:", // Single field or else sus choice placement
                        "A) " + question.getOptionA() + "\n" +
                                "B) " + question.getOptionB() + "\n" +
                                "C) " + question.getOptionC() + "\n" +
                                "D) " + question.getOptionD(),
                        false
                )
                .setFooter(String.format("Choose the correct answer below (ID: %d)", question.getQuestionId()));

        event.getChannel().sendMessageEmbeds(embedBuilder.build())
                .addActionRow(
                        Button.primary("answer_A", "A"),
                        Button.primary("answer_B", "B"),
                        Button.primary("answer_C", "C"),
                        Button.primary("answer_D", "D")
                )
                .queue(sentMessage -> {
                    ActiveQuestionTracker.addActiveQuestion(user, question, sentMessage.getIdLong(), question.getQuestionId(), event.getChannelIdLong());
                });
    }

    // Needs to not reply since, if it is another person's interaction, replies twice
    public static void handleActiveQuestion(ButtonInteractionEvent event, User user) {
        // Since reply is already deferred, use getHook() directly
        InteractionHook hook = event.getHook();
        hook.setEphemeral(true); // Ensure ephemeral if not already set

        Long messageId = ActiveQuestionTracker.getMessageIdForUser(user);
        Long channelId = ActiveQuestionTracker.getChannelIdForUser(user);

        // Cleanup if message not found
        if (messageId == null || channelId == null) {
            ActiveQuestionTracker.removeActiveQuestion(user, messageId);
            hook.sendMessageEmbeds(
                    new EmbedBuilder()
                            .setDescription("You don't have a question to review! Get a question with `" + Constants.prefix + "<number>` or `/" + Constants.slashPrefix + "-practice-question`!")
                            .setColor(0xFFA500)
                            .build()
            ).queue();
            return;
        }

        // Build jump URL
        String jumpUrl = String.format("https://discord.com/channels/%s/%d/%d",
                event.getGuild().getId(),
                channelId,
                messageId);

        // Send embed through the existing hook
        hook.sendMessageEmbeds(
                new EmbedBuilder()
                        .setColor(0xFFA500)
                        .setThumbnail(user.getEffectiveAvatarUrl())
                        .setDescription(String.format(
                                "%s, you already have an active question!\n\n" +
                                        "[➔ Jump to your question](%s)\n\n" +
                                        "Please answer it before requesting a new one.",
                                user.getAsMention(),
                                jumpUrl))
                        .build()
        ).queue();
    }

    public static String getAnswerText(Question question, String option) {
        return switch (option.toUpperCase()) {
            case "A" -> question.getOptionA();
            case "B" -> question.getOptionB();
            case "C" -> question.getOptionC();
            case "D" -> question.getOptionD();
            default -> "";
        };
    }

    private String getRandomWrongAnswer() {
        return wrongAnswers[((int)(Math.random()*wrongAnswers.length))];
    }

}