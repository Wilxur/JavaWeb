package com.adplatform.dao.impl;

import com.adplatform.dao.ReportDao;
import com.adplatform.model.ReportData;
import com.adplatform.model.ReportFilter;
import com.adplatform.util.DBUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ReportDaoImpl implements ReportDao {

    private static final DBUtil.RowMapper<ReportData> ROW_MAPPER = rs -> {
        ReportData data = new ReportData();
        data.setDate(rs.getString("date"));
        data.setImpressions(rs.getLong("impressions"));
        data.setClicks(rs.getLong("clicks"));
        data.setCtr(rs.getDouble("ctr"));
        return data;
    };

    @Override
    public List<ReportData> getAdStats(int advertiserId, ReportFilter filter) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DATE(i.shown_at) AS `date`, ")
                .append("COUNT(i.id) AS impressions, ")
                .append("COUNT(c.id) AS clicks, ")
                .append("ROUND(COUNT(c.id) * 100.0 / COUNT(i.id), 2) AS ctr ")
                .append("FROM ad_impressions i ")
                .append("INNER JOIN ads a ON i.ad_id = a.id ")
                .append("LEFT JOIN ad_clicks c ON i.ad_id = c.ad_id AND i.uid = c.uid AND DATE(i.shown_at) = DATE(c.clicked_at) ")
                .append("WHERE a.advertiser_id = ? ");

        List<Object> params = new ArrayList<>();
        params.add(advertiserId);

        // 检查并添加开始日期（非空字符串）
        if (filter.getStartDate() != null && !filter.getStartDate().trim().isEmpty()) {
            sql.append("AND DATE(i.shown_at) >= ? ");
            params.add(filter.getStartDate());
        }

        // 检查并添加结束日期（非空字符串）
        if (filter.getEndDate() != null && !filter.getEndDate().trim().isEmpty()) {
            sql.append("AND DATE(i.shown_at) <= ? ");
            params.add(filter.getEndDate());
        }

        sql.append("GROUP BY DATE(i.shown_at) ")
                .append("ORDER BY `date` DESC");

        return DBUtil.executeQuery(sql.toString(), ROW_MAPPER, params.toArray());
    }

    @Override
    public List<ReportData> getTopAdsByCTR(int advertiserId, int limit, ReportFilter filter) {
        String sql = "SELECT '' AS `date`, a.id, a.title, " +
                "COUNT(DISTINCT i.id) AS impressions, " +
                "COUNT(DISTINCT c.id) AS clicks, " +
                "ROUND(COUNT(DISTINCT c.id) * 100.0 / COUNT(DISTINCT i.id), 2) AS ctr " +
                "FROM ads a " +
                "LEFT JOIN ad_impressions i ON a.id = i.ad_id " +
                "LEFT JOIN ad_clicks c ON a.id = c.ad_id " +
                "WHERE a.advertiser_id = ? ";

        List<Object> params = new ArrayList<>();
        params.add(advertiserId);

        // 检查并添加日期条件
        if (filter.getStartDate() != null && !filter.getStartDate().trim().isEmpty()) {
            sql += "AND i.shown_at >= ? ";
            params.add(filter.getStartDate());
        }
        if (filter.getEndDate() != null && !filter.getEndDate().trim().isEmpty()) {
            sql += "AND i.shown_at <= ? ";
            params.add(filter.getEndDate());
        }

        sql += "GROUP BY a.id, a.title " +
                "ORDER BY ctr DESC " +
                "LIMIT ?";

        params.add(limit);

        return DBUtil.executeQuery(sql, ROW_MAPPER, params.toArray());
    }

    @Override
    public Map<String, String> getDateRange(int advertiserId) {
        String sql = "SELECT MIN(DATE(shown_at)) AS min_date, MAX(DATE(shown_at)) AS max_date " +
                "FROM ad_impressions i " +
                "INNER JOIN ads a ON i.ad_id = a.id " +
                "WHERE a.advertiser_id = ?";

        List<Map<String, String>> result = DBUtil.executeQuery(sql, rs -> {
            Map<String, String> range = new HashMap<>();
            String minDate = rs.getString("min_date");
            String maxDate = rs.getString("max_date");
            // 如果数据库返回null，Map里存null而不是空字符串
            range.put("minDate", minDate);
            range.put("maxDate", maxDate);
            return range;
        }, advertiserId);

        // 如果查询不到数据，返回null值的Map
        if (result.isEmpty()) {
            Map<String, String> emptyRange = new HashMap<>();
            emptyRange.put("minDate", null);
            emptyRange.put("maxDate", null);
            return emptyRange;
        }
        return result.get(0);
    }
}