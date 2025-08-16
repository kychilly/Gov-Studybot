package com.Discord.DiscordBot.Units;

import com.Discord.DiscordBot.Constants;
import com.Discord.DiscordBot.Z_Units.*;

import java.util.ArrayList;

public class QuestionBank {

    // Some units may not be initialized, which is ok. This is only a template, delete any unneeded lists
    public static ArrayList<Question> unit1Questions = new ArrayList<>();
    public static ArrayList<Question> unit2Questions = new ArrayList<>();
    public static ArrayList<Question> unit3Questions = new ArrayList<>();
    public static ArrayList<Question> unit4Questions = new ArrayList<>();
    public static ArrayList<Question> unit5Questions = new ArrayList<>();
    public static ArrayList<Question> unit6Questions = new ArrayList<>();
    public static ArrayList<Question> unit7Questions = new ArrayList<>();
    public static ArrayList<Question> unit8Questions = new ArrayList<>();
    public static ArrayList<Question> unit9Questions = new ArrayList<>();
    public static ArrayList<Question> unit10Questions = new ArrayList<>();

    public static ArrayList<Question> questionBank = new ArrayList<>();

    public QuestionBank() {
        initializeQuestions();
    }

    private void initializeQuestions() {
        Unit1.initializeUnit1Questions();
        Unit2.initializeUnit2Questions();
        Unit3.initializeUnit3Questions();
        Unit4.initializeUnit4Questions();
        Unit5.initializeUnit5Questions();
        Unit6.initializeUnit6Questions();
        Unit7.initializeUnit7Questions();
        Unit8.initializeUnit8Questions();
        Unit9.initializeUnit9Questions();
        Unit10.initializeUnit10Questions();
        initializeQuestionBank();
    }

    public static ArrayList<Question> getQuestionBank() { return questionBank; }
    public static ArrayList<Question> getUnit1Questions() {
        return unit1Questions;
    }
    public static ArrayList<Question> getUnit2Questions() {
        return unit2Questions;
    }
    public static ArrayList<Question> getUnit3Questions() {
        return unit3Questions;
    }
    public static ArrayList<Question> getUnit4Questions() { return unit4Questions; }
    public static ArrayList<Question> getUnit5Questions() {
        return unit5Questions;
    }
    public static ArrayList<Question> getUnit6Questions() {
        return unit6Questions;
    }
    public static ArrayList<Question> getUnit7Questions() {
        return unit7Questions;
    }
    public static ArrayList<Question> getUnit8Questions() {
        return unit8Questions;
    }
    public static ArrayList<Question> getUnit9Questions() {
        return unit9Questions;
    }
    public static ArrayList<Question> getUnit10Questions() {
        return unit10Questions;
    }

    public void initializeQuestionBank() {
        questionBank.addAll(unit1Questions);
        questionBank.addAll(unit2Questions);
        questionBank.addAll(unit3Questions);
        questionBank.addAll(unit4Questions);
        questionBank.addAll(unit5Questions);
        questionBank.addAll(unit6Questions);
        questionBank.addAll(unit7Questions);
        questionBank.addAll(unit8Questions);
        questionBank.addAll(unit9Questions);
        questionBank.addAll(unit10Questions);
        Constants.sum = Unit1.numUnit1Questions + Unit2.numUnit2Questions + Unit3.numUnit3Questions
                + Unit4.numUnit4Questions + Unit5.numUnit5Questions + Unit6.numUnit6Questions + Unit7.numUnit7Questions
                + Unit8.numUnit8Questions + Unit9.numUnit9Questions + Unit10.numUnit10Questions;
    }

    public static ArrayList<Question> getSpecificQuestionMethod(int n) {
        if (n == 1) {
            return unit1Questions;
        } else if (n == 2) {
            return unit2Questions;
        } else if (n == 3) {
            return unit3Questions;
        } else if (n == 4) {
            return unit4Questions;
        } else if (n == 5) {
            return unit5Questions;
        } else if (n == 6) {
            return unit6Questions;
        } else if (n == 7) {
            return unit7Questions;
        } else if (n == 8) {
            return unit8Questions;
        } else if (n == 9) {
            return unit9Questions;
        } else if (n == 10) {
            return unit10Questions;
        }
        System.out.println("bug in QuestionBank line 61 getSpecificQuestionMethod(int n)");
        return unit4Questions;
    }

    // Get random question given the ArrayList(unit1Questions gets random from unit1Questions)
    public static Question getRandomQuestion(ArrayList<Question> questions, int prevQuestionId) {
        if (questions.isEmpty()) {
            System.out.println("Make more questions lol");
            return null;
        }
        // Makes sure the question is not the same one from before
        Question question;
        do {
            question = questions.get((int) (Math.random() * questions.size()));
        } while (question.getQuestionId() == prevQuestionId);
        return question;
    }
}