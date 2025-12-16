package com.adplatform.service;

import java.util.Map;

public interface DashboardService {
    /**
     * 获取仪表板统计数据
     * @return key-value格式的统计数据
     */
    Map<String, Object> getDashboardStats();
}