package com.Discord.DiscordBot.Units;

public class Question {
    private final String question;
    private final String optionA;
    private final String optionB;
    private final String optionC;
    private final String optionD;
    private final String correctAnswer;
    private final int unit;
    private final int questionId;
    private final String difficulty;

    public Question(String question, String optionA, String optionB,
                    String optionC, String optionD, String correctAnswer, int unit, int questionId, String difficulty) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer.toUpperCase();
        this.unit = unit;
        this.questionId = questionId;
        this.difficulty = difficulty;
    }

    // Getters
    public String getQuestion() { return question; }
    public String getOptionA() { return optionA; }
    public String getOptionB() { return optionB; }
    public String getOptionC() { return optionC; }
    public String getOptionD() { return optionD; }
    public String getCorrectAnswer() { return correctAnswer; }
    public int getUnit() { return unit; }
    public int getQuestionId() { return questionId; }
    public String getQuestionDifficulty() { return difficulty; }

    public boolean isCorrect(String answer) {
        return answer.equalsIgnoreCase(correctAnswer);
    }
}