package com.Discord.DiscordBot.Z_Units;

import static com.Discord.DiscordBot.Units.QuestionBank.unit8Questions;

public class Unit8 {

    public static int numUnit8Questions;

    public static void initializeUnit8Questions() {

        // unit4Questions.add(new Question(
        //                "What is printed?\n```java\nint[] nums = {1, 2, 3, 4};\nSystem.out.println(nums[2]);\n```",
        //                "1", "2", "3", "4",
        //                "C", 4, 3000, "easy"));

        numUnit8Questions = unit8Questions.size();
        System.out.printf("There are %d questions in unit 8%n", numUnit8Questions);

    }

}
