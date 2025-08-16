package com.Discord.DiscordBot.Sessions;

import com.Discord.DiscordBot.Constants;
import net.dv8tion.jda.api.entities.User;

import java.io.IOException;

public class UserProfile {

    private final User user;
    private int points;
    private String title;

    public UserProfile(User user, int points, String title) {
        this.user = user;
        this.points = points; // Could also just set this to 0 lol
        updateTitle(); // Updates the title
    }

    public void addPoints(int points) {
        this.points += points;
        updateTitle();
        saveToFile();
    }

    // Constants.titles has all info needed here
    public void updateTitle() {
        if (points >= Constants.pointTitles[4]) {
            title = Constants.titles[4];
        } else if (points >= Constants.pointTitles[3]) {
            title = Constants.titles[3];
        } else if (points >= Constants.pointTitles[2]) {
            title = Constants.titles[2];
        } else if (points >= Constants.pointTitles[1]) {
            title = Constants.titles[1];
        } else {
            title = Constants.titles[0]; // You technically dont have to put this here, but just in case I guess
        }
        saveToFile();
    }

    public User getUser() { return user; }
    public int getPoints() { return points; }
    public String getTitle() { return title; }

    private void saveToFile() {
        try {
            UserProfileManager.saveProfile(this);
        } catch (IOException e) {
            System.err.println("Failed to save profile: " + e.getMessage());
        }
    }

}
