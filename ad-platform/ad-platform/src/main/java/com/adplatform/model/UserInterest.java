package com.adplatform.model;

public class UserInterest {
    private String uid;
    private String category;
    private int score;
    private String lastUpdated;

    public UserInterest() {
    }

    public UserInterest(String uid, String category, int score, String lastUpdated) {
        this.uid = uid;
        this.category = category;
        this.score = score;
        this.lastUpdated = lastUpdated;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
