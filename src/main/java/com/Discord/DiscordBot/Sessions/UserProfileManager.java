package com.Discord.DiscordBot.Sessions;

import com.Discord.DiscordBot.Constants;
import com.google.gson.*;
import net.dv8tion.jda.api.entities.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserProfileManager {
    private static final String PROFILES_FILE = "profiles/all_profiles.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static Map<String, JsonObject> profilesCache = new HashMap<>();

    static {
        // Create profiles directory if it doesn't exist
        new File("profiles").mkdirs();
        loadAllProfiles();
    }

    private static void loadAllProfiles() {
        File file = new File(PROFILES_FILE);
        if (!file.exists()) {
            return;
        }

        try (FileReader reader = new FileReader(file)) {
            JsonObject allProfiles = gson.fromJson(reader, JsonObject.class);
            for (Map.Entry<String, JsonElement> entry : allProfiles.entrySet()) {
                profilesCache.put(entry.getKey(), entry.getValue().getAsJsonObject());
            }
        } catch (IOException e) {
            System.err.println("Failed to load profiles: " + e.getMessage());
        }
    }

    private static void saveAllProfiles() {
        try (FileWriter writer = new FileWriter(PROFILES_FILE)) {
            JsonObject allProfiles = new JsonObject();
            for (Map.Entry<String, JsonObject> entry : profilesCache.entrySet()) {
                allProfiles.add(entry.getKey(), entry.getValue());
            }
            gson.toJson(allProfiles, writer);
        } catch (IOException e) {
            System.err.println("Failed to save profiles: " + e.getMessage());
        }
    }

    public static void saveProfile(UserProfile profile) throws IOException {
        JsonObject profileJson = new JsonObject();
        profileJson.addProperty("userId", profile.getUser().getId());
        profileJson.addProperty("points", profile.getPoints());
        profileJson.addProperty("title", profile.getTitle());

        profilesCache.put(profile.getUser().getId(), profileJson);
        saveAllProfiles();
    }

    public static UserProfile loadProfile(User user) throws IOException {
        JsonObject profileJson = profilesCache.get(user.getId());
        if (profileJson == null) {
            return new UserProfile(user, 0, Constants.titles[0]);
        }

        int points = profileJson.get("points").getAsInt();
        String title = profileJson.get("title").getAsString();
        return new UserProfile(user, points, title);
    }

    public static boolean profileExists(User user) {
        return profilesCache.containsKey(user.getId());
    }
}