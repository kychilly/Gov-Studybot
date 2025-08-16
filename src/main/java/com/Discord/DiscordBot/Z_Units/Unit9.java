package com.Discord.DiscordBot.Z_Units;

import static com.Discord.DiscordBot.Units.QuestionBank.unit9Questions;

public class Unit9 {

    public static int numUnit9Questions;

    public static void initializeUnit9Questions() {

        // unit4Questions.add(new Question(
        //                "What is printed?\n```java\nint[] nums = {1, 2, 3, 4};\nSystem.out.println(nums[2]);\n```",
        //                "1", "2", "3", "4",
        //                "C", 4, 3000, "easy"));

        numUnit9Questions = unit9Questions.size();
        System.out.printf("There are %d questions in unit 9%n", numUnit9Questions);

    }

}
