package com.Discord.DiscordBot.Z_Units;

import static com.Discord.DiscordBot.Units.QuestionBank.unit10Questions;

public class Unit10 {

    public static int numUnit10Questions;

    public static void initializeUnit10Questions() {

        // unit4Questions.add(new Question(
        //                "What is printed?\n```java\nint[] nums = {1, 2, 3, 4};\nSystem.out.println(nums[2]);\n```",
        //                "1", "2", "3", "4",
        //                "C", 4, 3000, "easy"));

        numUnit10Questions = unit10Questions.size();
        System.out.printf("There are %d questions in unit 10%n", numUnit10Questions);

    }

}
