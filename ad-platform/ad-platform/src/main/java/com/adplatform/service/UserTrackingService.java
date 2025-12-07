package com.adplatform.service;

import com.adplatform.dao.UserInterestDao;
import java.util.UUID;

public class UserTrackingService {
    private UserInterestDao userDao = new UserInterestDao();

    public String generateUid() {
        return UUID.randomUUID().toString().replace("-", "") +
                Long.toHexString(System.currentTimeMillis());
    }

    public void ensureUserExists(String uid) {
        if (uid != null && !uid.isEmpty()) {
            userDao.ensureUserExists(uid);
        }
    }

    public void updateUserInterest(String uid, String category, String action) {
        if (uid == null || category == null) return;

        int score = calculateScore(action);
        userDao.updateInterest(uid, category, score);
    }

    private int calculateScore(String action) {
        switch (action) {
            case "purchase": return 10;
            case "click": return 5;
            case "view": return 2;
            default: return 1;
        }
    }
}