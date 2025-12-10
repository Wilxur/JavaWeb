package com.example.news.dao.impl;

import com.example.news.dao.NewsDao;
import com.example.news.model.News;
import com.example.news.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewsDaoImpl implements NewsDao {

    @Override
    public List<News> findAll() {
        List<News> list = new ArrayList<>();

        String sql =
                "SELECT n.*, c.category_name " +
                        "FROM news n LEFT JOIN category c ON n.category_id = c.category_id " +
                        "ORDER BY n.published_at DESC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(extractNews(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }

        return list;
    }

    @Override
    public List<News> findByCategory(int categoryId) {
        List<News> list = new ArrayList<>();

        String sql =
                "SELECT n.*, c.category_name " +
                        "FROM news n LEFT JOIN category c ON n.category_id = c.category_id " +
                        "WHERE n.category_id = ? ORDER BY n.published_at DESC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, categoryId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(extractNews(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }

        return list;
    }

    @Override
    public News findById(int id) {
        String sql =
                "SELECT n.*, c.category_name " +
                        "FROM news n LEFT JOIN category c ON n.category_id = c.category_id " +
                        "WHERE n.news_id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        News news = null;

        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                news = extractNews(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }

        return news;
    }

    @Override
    public void incrementViewCount(int id) {
        String sql = "UPDATE news SET view_count = view_count + 1 WHERE news_id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, stmt, conn);
        }
    }

    @Override
    public void insert(News n) {
        String sql =
                "INSERT INTO news (title, content, author, category_id) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, n.getTitle());
            stmt.setString(2, n.getContent());
            stmt.setString(3, n.getAuthor());
            stmt.setInt(4, n.getCategoryId());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, stmt, conn);
        }
    }

    @Override
    public void update(News n) {
        String sql =
                "UPDATE news SET title=?, content=?, author=?, category_id=? WHERE news_id=?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, n.getTitle());
            stmt.setString(2, n.getContent());
            stmt.setString(3, n.getAuthor());
            stmt.setInt(4, n.getCategoryId());
            stmt.setInt(5, n.getNewsId());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, stmt, conn);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM news WHERE news_id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, stmt, conn);
        }
    }

    /**
     * 新闻对象映射方法（复用）
     */
    private News extractNews(ResultSet rs) throws Exception {
        News n = new News();
        n.setNewsId(rs.getInt("news_id"));
        n.setTitle(rs.getString("title"));
        n.setContent(rs.getString("content"));
        n.setAuthor(rs.getString("author"));
        n.setCategoryId(rs.getInt("category_id"));
        n.setViewCount(rs.getInt("view_count"));
        n.setPublishedAt(rs.getTimestamp("published_at").toLocalDateTime());
        n.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        n.setCategoryName(rs.getString("category_name"));
        return n;
    }
}