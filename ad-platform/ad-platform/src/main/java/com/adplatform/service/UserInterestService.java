package com.adplatform.service;

public interface UserInterestService {
    // 更新用户兴趣（展示+1，点击+5，页面访问+10）
    void updateInterest(String uid, String category, int increment);

    // 衰减旧兴趣（除当前分类外，score-1）
    void decayOldInterests(String uid, String excludeCategory);
}