package com.adplatform.dao;

public interface DashboardDao {
    int getTotalAdCount();              // 有效广告总数
    int getTodayAdCount();              // 今日新增广告
    int getActiveUserCount();           // 活跃用户数
    long getTotalImpressionCount();     // 总展示次数
    long getTotalClickCount();          // 总点击次数
}