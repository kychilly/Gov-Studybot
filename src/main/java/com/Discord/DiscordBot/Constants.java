package com.Discord.DiscordBot;

import com.Discord.DiscordBot.Z_Units.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class Constants {

    // Change these prefixes to the said AP prefix. Ex: for AP HAG, change prefix to !hag and slashPrefix to hag.
    public static String prefix = "!gov";
    public static String slashPrefix = "gov"; // Yea you have to put a / before this everytime

    public static int numUnits = 6; // The number of units the course has. Max of 25 units or else options crash

    public static int unitQuestionTimeoutInMinutes = 3; // (No Change) - Should be 3
    public static int unitQuestionIntervalCheckInSeconds = 30; // (No Change) - Should be 30

    public static int testTimeoutInMinutes = 30; // (No Change) - Should be 30
    public static int testIntervalCheckInMinutes = 5; // (No Change) - Should be 5

    public static int reportCooldown = 10 * 1000 * 60; // (No Change) - 10 minutes(or 600,000ms) for report cooldown

    public static int percentageFor3 = 60; // The percent you need to score a 3 or higher on the AP test

    public static String[] titles = {"Level 1 Sleeper", "Level 2 Learner", "Level 3 Casual", "Level 4 Honored", "Level 5 AP Deity"}; // (No Change) - Profile Titles

    public static int[] pointTitles = {0, 100, 250, 500, 1000}; // (No Change) - Points until next title

    public static int[] scorePercents = {82, 75, 60, 44}; // The percents to score a 5, 4, 3, and 2

    // (No Change) - Collegeboard logo
    public static String collegeBoardThumbnail = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQN2KG3StxIW0KW9EZP4pbSYbW-_1pJw9YtdR3HsZnyGTu1as-kFCJusA-qDROINnG9vJI&usqp=CAU";

    // Picture of the AP course(AP classroom picture)
    public static String APPicture = "https://media.discordapp.net/attachments/1186115783013711894/1406090139368951878/image.png?ex=68a132fd&is=689fe17d&hm=fb7d52a370798443f144503f96ac7ee56c61baf22214fb0e6134220610d9ce50&=&format=webp&quality=lossless&width=911&height=507";

    // To invite the bot to your server
    public static String botInviteLink = "https://discord.com/oauth2/authorize?client_id=1405351632190967939&permissions=2147503104&integration_type=0&scope=bot";

    // (No Change) - Integrated AI prompt
    public static String COMMAND_GUIDE_PROMPT =
            "You are the built-in help guide and study assistant for the " + slashPrefix.toUpperCase() + "-Studybot.\n" +
                    "\nYour two main roles are:\n" +
                    "1. Command Guide — Explain only the specific command(s) the user asks about: how they work, required/optional parameters, and give at least one example.\n" +
                    "2. Study Helper — Answer user questions related to AP " + prefix + " content.\n" +
                    "\nNotes:\n" +
                    "- Users may sometimes ask off-topic questions. You may answer politely, but don’t be mean. Only mention your dual purpose if they go off-topic.\n" +
                    "- Keep responses under 1000 characters.\n" +
                    "- Present information in a clear, concise format.\n" +
                    "- When commands have parameters, explain what they do and what happens if left blank.\n" +
                    "- Never use, repeat, or acknowledge offensive/derogatory language (including disguised/bypassed slurs). Always check responses for safety.\n" +
                    "\nCommand details:\n" +
                    "- " + prefix + "<number>: Sends a question from the specified AP" + Constants.slashPrefix.toUpperCase() + " unit (1–" + numUnits + "). Example: !" + prefix + "1 sends a Unit 1 question.\n" +
                    "- /" + slashPrefix + "-practice-question: Slash version of " + prefix + "<number>. Optional param: unit (1–" + numUnits + "). Blank → random unit.\n" +
                    "- /" + slashPrefix + "-test: Starts a quiz. Optional params: number of questions (must be 5, 10, 15, 20, or 50; default 10), unit (1–" + numUnits + "), blank → all units).\n" +
                    "- " + prefix + "-questionbank: Shows all questions.\n" +
                    "- " + prefix + "-info: Overview of the AP course.\n" +
                    "- " + prefix + "-resources: Study resources.\n" +
                    "- " + prefix + "-report <Your message>: Report a bug or issue with the bot. There is a " +
                    "- /" + slashPrefix + "-help: Lists all commands.\n" +
                    "- /" + slashPrefix + "-ask: Ask AI a question directly.\n" +
                    "- /" + slashPrefix + "-profile: Displays your profile. Optional param: @mention of another user to view their profile.\nHere is their response: ";

    // Change this with whatever AP course this bot is - no need to change botInviteLink, as its already changed
    public static MessageEmbed createResourcesEmbed() {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("📚 AP " + Constants.slashPrefix.toUpperCase() + " Study Resources")
                .setDescription("Here are some valuable resources to help you succeed in AP U.S. Government and Politics:")
                .setColor(0x3498db)
                .setThumbnail(Constants.collegeBoardThumbnail)

                // Official College Board Resources
                .addField("🎓 Official College Board Resources",
                        "• [Course Overview](https://apstudents.collegeboard.org/courses/ap-united-states-government-and-politics)\n" +
                                "• [Course Description](https://apcentral.collegeboard.org/pdf/ap-us-government-and-politics-course-and-exam-description.pdf)\n" +
                                "• [Past Exam Questions](https://apcentral.collegeboard.org/courses/ap-united-states-government-and-politics/exam)\n" +
                                "• [AP Classroom](https://myap.collegeboard.org/) (Requires teacher login)", false)

                // Video Tutorials
                .addField("📺 Video Tutorials",
                        "• [Khan Academy AP Gov](https://www.khanacademy.org/humanities/ap-us-government-and-politics)\n" +
                                "• [Heimler's History](https://www.youtube.com/c/HeimlersHistory)\n" +
                                "• [Carey LaManna](https://www.youtube.com/c/CareyLaManna)", false)

                // Practice Sites
                .addField("📝 Practice & Study Sites",
                        "• [AP Gov Practice Questions](https://www.albert.io/ap-us-government)\n" +
                                "• [Fiveable AP Gov Study Guides](https://fiveable.me/ap-gov)\n" +
                                "• [C-SPAN Classroom](https://www.c-span.org/classroom/)", false)

                // Additional Help
                .addField("🆘 Additional Help",
                        "• [Congress.gov](https://www.congress.gov/)\n" +
                                "• [SCOTUSblog](https://www.scotusblog.com/)\n" +
                                "• [Oyez Supreme Court Cases](https://www.oyez.org/)", false)

                // Constitution Resources
                .addField("📜 Constitution Resources",
                        "• [Interactive Constitution](https://constitutioncenter.org/interactive-constitution)\n" +
                                "• [National Archives](https://www.archives.gov/founding-docs)", false)

                // Bot Invite
                .addField("🤖 Bot Invite",
                        "[Invite this bot to your server](" + Constants.botInviteLink + ")", false)

                .setFooter("Good luck with your studies! 🍀");

        return embed.build();
    }

    // (No Change) - The total number of questions in the units.
    public static int sum;

    // Change this to whatever AP course info you want
    public static void SendAvailableQuestions(MessageReceivedEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("📚 Available AP Gov Practice Questions 📚");
        embed.setThumbnail(Constants.APPicture);
        embed.setColor(0x3498db);

        embed.setDescription(String.format(
                "**Total Questions:** %d\n\nUse `!" + Constants.slashPrefix + "<number>`,  `/" + Constants.slashPrefix + "-practice-question`, or `/" +
                        Constants.slashPrefix + "-test` to start practicing!\n----------------------------------------------------", sum
        ));

        embed.addField("**Unit 1:** Foundations of American Democracy",
                String.format(
                        "🔹 **%d questions**\nCovers the Constitution, Federalism, and the philosophical foundations of U.S. government.\n\u200B",
                        Unit1.numUnit1Questions),
                false);

        embed.addField("**Unit 2:** Interactions Among Branches",
                String.format(
                        "🔹 **%d questions**\nFocuses on Congress, the Presidency, the Judiciary, and the bureaucracy.\n\u200B",
                        Unit2.numUnit2Questions),
                false);

        embed.addField("**Unit 3:** Civil Liberties and Civil Rights",
                String.format(
                        "🔹 **%d questions**\nExamines the Bill of Rights, Supreme Court cases, and the struggle for civil rights.\n\u200B",
                        Unit3.numUnit3Questions),
                false);

        embed.addField("**Unit 4:** American Political Ideologies and Beliefs",
                String.format(
                        "🔹 **%d questions**\nAnalyzes political socialization, public opinion, and political ideologies.\n\u200B",
                        Unit4.numUnit4Questions),
                false);

        embed.addField("**Unit 5:** Political Participation",
                String.format(
                        "🔹 **%d questions**\nCovers voting behavior, political parties, elections, and interest groups.\n\u200B",
                        Unit5.numUnit5Questions),
                false);

        embed.addField("**Unit 6:** Policy Making and Contemporary Issues",
                String.format(
                        "🔹 **%d questions**\nFocuses on domestic, economic, and foreign policy issues.\n\n" +
                                "[Visit Our Website](https://customdiscordbots.org)\n" +
                                "Contact <@840216337119969301> for any questions",
                        Unit6.numUnit6Questions),
                false);
        embed.setFooter("All questions are student-created! Please report if you see any mistakes!");

        event.getChannel().sendMessageEmbeds(embed.build()).queue();
    }


    // (No Change) - Possible end of test messages
    public static final ArrayList<String> FIVE_SCORE_MESSAGES = new ArrayList<>(Arrays.asList("Great job! You're at the top of your game!", "Excellent work! Keep it up!", "Fantastic effort! You nailed it!", "Outstanding performance! Well done!", "You crushed it! Congratulations!", "Superb! You're truly excellent!", "Amazing job! You're a star!", "Bravo! That’s an impressive score!", "You did wonderfully! Keep shining!", "Exceptional! You're one of the best!", "Phenomenal work! Proud of you!", "Top-notch job! Keep soaring!", "Magnificent! You’re on fire!", "Splendid! You should be proud!", "Impressive! You worked hard and it shows!", "Remarkable! Keep pushing those limits!", "Wonderful! You aced it!", "Superb performance! Way to go!", "Terrific! You set the bar high!", "Brilliant! You deserve all the praise!"));
    public static final ArrayList<String> FOUR_SCORE_MESSAGES = new ArrayList<>(Arrays.asList("Good job! You're doing really well!", "Nice work! Keep it up!", "Solid effort! You're almost there!", "Well done! You're on the right track!", "Great progress! Keep pushing forward!", "You're doing a good job! Stay focused!", "Good going! A bit more and you’ll ace it!", "Nice job! Keep building on this!", "Well played! You're making steady progress!", "Good work! Keep practicing!", "You're doing well! Keep it consistent!", "Strong performance! Keep improving!", "You're getting there! Keep at it!", "Good effort! Keep striving!", "Nice results! You're showing great potential!", "Well done! Keep aiming higher!", "Good job! You're almost at the top!", "Solid work! Keep moving forward!", "You're doing a great job! Keep it steady!", "Good going! You're making progress!"));
    public static final ArrayList<String> THREE_SCORE_MESSAGES = new ArrayList<>(Arrays.asList("Good effort! Keep working and you'll improve!", "You're making progress! Stay motivated!", "Not bad! Keep practicing and you'll get better!", "Keep it up! Every bit of practice helps!", "You're on the right path! Don't give up!", "Nice try! Keep pushing yourself!", "Decent work! Keep aiming higher!", "You're getting there! Keep at it!", "Solid attempt! Keep learning!", "Keep improving! You're doing okay!", "Keep focused! Improvement takes time!", "You're doing fine! Stay consistent!", "Good start! Keep building your skills!", "You're making steady progress! Keep going!", "Keep practicing! You're on your way!", "You're doing alright! Keep pushing!", "Keep working hard! You can do this!", "Don't stop now! Practice makes perfect!", "You're on your way! Stay determined!", "Nice progress! Keep it up!"));
    public static final ArrayList<String> TWO_SCORE_MESSAGES = new ArrayList<>(Arrays.asList("Keep trying! Practice will help you improve!", "Don't be discouraged! Keep working hard!", "You can do better! Keep pushing forward!", "Stay motivated! Improvement is a journey!", "Keep practicing! You'll get there!", "Hang in there! Keep putting in effort!", "Keep at it! Every step counts!", "Don't give up! Keep learning!", "Focus and practice! You can improve!", "Keep going! Persistence pays off!", "Stay positive! Keep working on your skills!", "Keep striving! You're making progress!", "Don't lose hope! Keep practicing!", "Work hard! Improvement takes time!", "Keep focused! You'll get better!", "Stay determined! Keep trying!", "Keep moving forward! You can improve!", "Keep practicing! Believe in yourself!", "Stay patient! Progress is progress!", "Keep pushing! You're learning!"));
    public static final ArrayList<String> ONE_SCORE_MESSAGES = new ArrayList<>(Arrays.asList("Don't give up! You can improve!", "Keep trying! Every effort counts!", "Stay positive! Practice helps!", "Keep working! You’ll get better!", "Don’t lose hope! Keep going!", "Keep pushing! Improvement takes time!", "Stay determined! Keep practicing!", "Keep at it! Progress is progress!", "Believe in yourself! Keep trying!", "Keep focused! You’re learning!", "Keep moving forward! Don’t quit!", "Every step helps! Stay strong!", "Work hard! You’ll improve!", "Keep practicing! You can do it!", "Stay motivated! Keep pushing!", "Keep going! Don’t be discouraged!", "Stay patient! You’re making progress!", "Keep striving! You’re getting there!", "Stay positive! Keep at it!", "Don’t give up! You’ve got this!"));

    // Good luck with your studies!
}
