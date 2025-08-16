package com.Discord.DiscordBot.Units;

public class CheckQuestionAnswer {

    // Putting this here cause everywhere else is too crowded + this class has nothing
    public static String[] wrongAnswers = {
            "**Oops**, not quite right! Try again!",
            "**Close**, but not the correct answer!",
            "**That's not it**, but don’t give up!",
            "**Incorrect**, but keep going—you got this!",
            "**Not the right answer**, but nice try!",
            "**That’s a miss**, but you’ll get it next time!",
            "**Almost there**, but not quite!",
            "**Not correct**, but I believe in you!",
            "**Wrong answer**, but keep trying!",
            "**That’s not the one**, but you’re learning!",
            "**Incorrect**, but every mistake gets you closer!",
            "**Not this time**, but don’t stop now!",
            "**That’s a wrong turn**—let’s try again!",
            "**Not the answer** we’re looking for, but keep going!",
            "**A little off**—try once more!",
            "**Not quite**, but you’re making progress!",
            "**Incorrect**, but you’re on the right track!",
            "**That’s a no**, but persistence pays off!",
            "**Not right**, but you’ll figure it out!",
            "**Wrong this time**, but next time could be it!",
            "**Not the solution**, but keep experimenting!",
            "**That’s a miss**, but you’re improving!",
            "**Incorrect**, but you’re getting warmer!",
            "**Not the answer**, but don’t lose hope!",
            "**That’s not correct**, but you’re doing great!"
    };

    // This class literally just checks the question answer. Yes, single method in single class
    public static boolean checkAnswer(Question question, String answer) {
        if (question == null || answer == null || answer.isEmpty()) {
            return false;
        }
        return question.isCorrect(answer);
    }

}
