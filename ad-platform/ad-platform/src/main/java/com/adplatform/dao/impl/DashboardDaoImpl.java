package com.adplatform.dao.impl;

import com.adplatform.dao.DashboardDao;
import com.adplatform.util.DBUtil;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDaoImpl implements DashboardDao {

    // 单行结果映射器
    private static final DBUtil.RowMapper<Integer> INT_MAPPER = rs -> rs.getInt(1);
    private static final DBUtil.RowMapper<Long> LONG_MAPPER = rs -> rs.getLong(1);

    @Override
    public int getTotalAdCount() {
        String sql = "SELECT COUNT(*) FROM ads WHERE status = 1";
        return DBUtil.executeQuery(sql, INT_MAPPER).stream().findFirst().orElse(0);
    }

    @Override
    public int getTodayAdCount() {
        String sql = "SELECT COUNT(*) FROM ads WHERE DATE(created_at) = CURDATE()";
        return DBUtil.executeQuery(sql, INT_MAPPER).stream().findFirst().orElse(0);
    }

    @Override
    public int getActiveUserCount() {
        String sql = "SELECT COUNT(DISTINCT uid) FROM user_interests WHERE score > 0";
        return DBUtil.executeQuery(sql, INT_MAPPER).stream().findFirst().orElse(0);
    }

    @Override
    public long getTotalImpressionCount() {
        String sql = "SELECT COUNT(*) FROM ad_impressions";
        return DBUtil.executeQuery(sql, LONG_MAPPER).stream().findFirst().orElse(0L);
    }

    @Override
    public long getTotalClickCount() {
        String sql = "SELECT COUNT(*) FROM ad_clicks";
        return DBUtil.executeQuery(sql, LONG_MAPPER).stream().findFirst().orElse(0L);
    }
}