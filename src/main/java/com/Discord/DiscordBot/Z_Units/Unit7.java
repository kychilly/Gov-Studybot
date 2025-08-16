package com.Discord.DiscordBot.Z_Units;

import static com.Discord.DiscordBot.Units.QuestionBank.unit7Questions;

public class Unit7 {

    public static int numUnit7Questions;

    public static void initializeUnit7Questions() {

        // unit4Questions.add(new Question(
        //                "What is printed?\n```java\nint[] nums = {1, 2, 3, 4};\nSystem.out.println(nums[2]);\n```",
        //                "1", "2", "3", "4",
        //                "C", 4, 3000, "easy"));

        numUnit7Questions = unit7Questions.size();
        System.out.printf("There are %d questions in unit 7%n", numUnit7Questions);

    }

}
