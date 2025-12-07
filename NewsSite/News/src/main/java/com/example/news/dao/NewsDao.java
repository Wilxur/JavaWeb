package com.example.news.dao;

import com.example.news.model.News;
import com.example.news.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewsDao {

    // 必须要有无参构造
    public NewsDao() {}

    // 查询所有新闻
    public List<News> findAll() {
        List<News> list = new ArrayList<>();
        String sql = "SELECT * FROM news ORDER BY published_at DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                News n = new News(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("publish_time")
                );
                list.add(n);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 根据ID查询新闻
    public News findById(int id) {
        String sql = "SELECT * FROM news WHERE news_id=?";
        News n = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    n = new News(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getTimestamp("publish_time")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;
    }
}