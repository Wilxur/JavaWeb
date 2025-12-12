package com.adplatform.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AnonymousUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private String uid;
    private LocalDateTime createdAt;

    public AnonymousUser() {}

    public AnonymousUser(String uid, LocalDateTime createdAt) {
        this.uid = uid;
        this.createdAt = createdAt;
    }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "AnonymousUser{uid='" + uid + "'}";
    }
}