package com.Discord.DiscordBot.A_IndividualMethods;

import com.Discord.DiscordBot.Sessions.TestSession;
import com.Discord.DiscordBot.Units.Question;

public class CalculatePoints {

    // Easy: 1-3
    // Medium: 2-4
    // Hard: 3-5
    public static int calculatePoints(Question question) {
        int points;
        switch (question.getQuestionDifficulty()) {
            case "easy":
                points = (int)(Math.random()*3)+1;
                break;
            case "medium":
                points = (int)(Math.random()*3)+2;
                break;
            case "hard":
                points = (int)(Math.random()*3)+3;
                break;
            default:
                points = 1; // In case a scary bug happens lol
                System.out.println("bug with calculatePoints in CalculatePoints");
                break; // Break here just in case of more bugs
        }
        return points;
    }

    public static int calculateTestPoints(TestSession session) {
        int totalPoints = 0;
        for (int i = 0; i < session.getQuestionList().size(); i++) {
            Question question = session.getQuestionByIndex(i);
            if (question.getCorrectAnswer().equals(session.getUserAnswerByIndex(i))) {
                totalPoints += calculatePoints(question);
            }
        }
        return totalPoints;
    }

}
