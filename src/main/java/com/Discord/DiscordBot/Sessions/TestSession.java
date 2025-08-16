package com.Discord.DiscordBot.Sessions;
import com.Discord.DiscordBot.Units.Question;
import net.dv8tion.jda.api.entities.User;

import java.util.*;

public class TestSession {
    private final List<Question> questions;
    private final Map<Integer, String> userAnswers;
    private final User user;
    private int currentIndex;
    private long messageId;
    private long channelId;
    private boolean submitted = false;
    private volatile long lastActivityTime = System.currentTimeMillis();

    public TestSession(List<Question> questions, User user) {
        this.questions = new ArrayList<>(questions);
        this.userAnswers = new HashMap<>();
        this.currentIndex = 0;
        this.user = user;
    }

    public List<Question> getQuestionList() {
        return questions;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int n) {
        currentIndex = n;
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    public Question getCurrentQuestion() {
        return questions.get(currentIndex);
    }

    public String getUserAnswerByIndex(int index) {
        return userAnswers.get(index);
    }

    public Question getQuestionByIndex(int n) {
        return questions.get(n);
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public void setAnswer(int index, String answer) {
        userAnswers.put(index, answer);
    }

    public User getUser() { return user; }

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public void nextQuestion() {
        if (currentIndex < questions.size() - 1) {
            currentIndex++;
        }
    }

    public void previousQuestion() {
        if (currentIndex > 0) {
            currentIndex--;
        }
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public void setLastActivityTime(long time) {
        lastActivityTime = time;
    }

    public long getLastActivityTime() {
        return lastActivityTime;
    }

    public int calculateScore() {
        int score = 0;
        for (Map.Entry<Integer, String> entry : userAnswers.entrySet()) {
            if (questions.get(entry.getKey()).getCorrectAnswer().equalsIgnoreCase(entry.getValue())) {
                score++;
            }
        }
        return score;
    }

    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }

}