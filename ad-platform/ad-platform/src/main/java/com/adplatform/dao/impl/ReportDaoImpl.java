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
        sql.append("SELECT base.date, ")
                .append("base.impressions, ")
                .append("COALESCE(clicks.clicks, 0) AS clicks, ")
                .append("ROUND(COALESCE(clicks.clicks, 0) * 100.0 / base.impressions, 2) AS ctr ")
                .append("FROM (")
                .append("  SELECT DATE(i.shown_at) AS `date`, COUNT(DISTINCT i.id) AS impressions ")
                .append("  FROM ad_impressions i ")
                .append("  INNER JOIN ads a ON i.ad_id = a.id ")
                .append("  WHERE a.advertiser_id = ? ");

        List<Object> params = new ArrayList<>();
        params.add(advertiserId);

        // 添加日期条件（内层查询）
        if (filter.getStartDate() != null && !filter.getStartDate().trim().isEmpty()) {
            sql.append("    AND DATE(i.shown_at) >= ? ");
            params.add(filter.getStartDate());
        }
        if (filter.getEndDate() != null && !filter.getEndDate().trim().isEmpty()) {
            sql.append("    AND DATE(i.shown_at) <= ? ");
            params.add(filter.getEndDate());
        }

        sql.append("  GROUP BY DATE(i.shown_at)")
                .append(") base ")
                .append("LEFT JOIN (")
                .append("  SELECT DATE(c.clicked_at) AS `date`, COUNT(*) AS clicks ")
                .append("  FROM ad_clicks c ")
                .append("  INNER JOIN ads a ON c.ad_id = a.id ")
                .append("  WHERE a.advertiser_id = ? ");

        params.add(advertiserId); // 第二次添加

        // 为点击表添加相同的日期条件
        if (filter.getStartDate() != null && !filter.getStartDate().trim().isEmpty()) {
            sql.append("    AND DATE(c.clicked_at) >= ? ");
            params.add(filter.getStartDate());
        }
        if (filter.getEndDate() != null && !filter.getEndDate().trim().isEmpty()) {
            sql.append("    AND DATE(c.clicked_at) <= ? ");
            params.add(filter.getEndDate());
        }

        sql.append("  GROUP BY DATE(c.clicked_at)")
                .append(") clicks ON base.date = clicks.date ")
                .append("ORDER BY base.date DESC");

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