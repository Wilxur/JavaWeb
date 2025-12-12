package com.adplatform.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserInterest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String uid;
    private String category;
    private Integer score;
    private LocalDateTime lastUpdated;

    public UserInterest() {}

    public UserInterest(String uid, String category, Integer score, LocalDateTime lastUpdated) {
        this.uid = uid;
        this.category = category;
        this.score = score;
        this.lastUpdated = lastUpdated;
    }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }

    @Override
    public String toString() {
        return "UserInterest{uid='" + uid + "', category='" + category + "', score=" + score + "}";
    }
}