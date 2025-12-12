package com.adplatform.service.impl;

import com.adplatform.dao.DashboardDao;
import com.adplatform.dao.impl.DashboardDaoImpl;
import com.adplatform.service.DashboardService;
import java.util.HashMap;
import java.util.Map;

public class DashboardServiceImpl implements DashboardService {
    private DashboardDao dashboardDao = new DashboardDaoImpl();

    @Override
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        // 基础统计数据
        stats.put("totalAds", dashboardDao.getTotalAdCount());
        stats.put("todayAds", dashboardDao.getTodayAdCount());
        stats.put("activeUsers", dashboardDao.getActiveUserCount());
        stats.put("totalImpressions", dashboardDao.getTotalImpressionCount());
        stats.put("totalClicks", dashboardDao.getTotalClickCount());

        // 计算点击率（CTR）
        long impressions = dashboardDao.getTotalImpressionCount();
        long clicks = dashboardDao.getTotalClickCount();
        double ctr = impressions > 0 ? (clicks * 100.0 / impressions) : 0.0;
        stats.put("ctr", String.format("%.2f%%", ctr));

        return stats;
    }
}