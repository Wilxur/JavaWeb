package com.example.news.dao.impl;

import com.example.news.dao.BehaviorDao;
import com.example.news.model.BehaviorLog;
import com.example.news.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BehaviorDaoImpl implements BehaviorDao {

    private static final String SQL_INSERT =
            "INSERT INTO behavior_log " +
                    "(uid, news_id, category_id, action, duration, user_agent, ip_address) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    @Override
    public void insert(BehaviorLog log) {

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);

            stmt.setString(1, log.getUid());
            stmt.setInt(2, log.getNewsId());
            stmt.setInt(3, log.getCategoryId());
            stmt.setString(4, log.getAction());
            stmt.setInt(5, log.getDuration());
            stmt.setString(6, log.getUserAgent());
            stmt.setString(7, log.getIpAddress());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, stmt, conn);
        }
    }
}