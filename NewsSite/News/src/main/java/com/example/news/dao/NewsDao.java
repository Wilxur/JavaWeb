package com.example.news.dao;

import com.example.news.model.News;
import com.example.news.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 新闻数据访问层
 */
public class NewsDao {

    /**
     * 添加新闻
     * @return 生成的主键ID，失败返回 -1
     */
    public int insert(News news) {
        String sql = "INSERT INTO news (title, content, author, category_id, view_count) VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, news.getTitle());
            ps.setString(2, news.getContent());
            ps.setString(3, news.getAuthor());
            ps.setInt(4, news.getCategoryId());
            ps.setInt(5, news.getViewCount() != null ? news.getViewCount() : 0);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }

        return -1;
    }

    /**
     * 删除新闻
     */
    public boolean deleteById(int newsId) {
        String sql = "DELETE FROM news WHERE news_id = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, newsId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps);
        }

        return false;
    }

    /**
     * 更新新闻
     */
    public boolean update(News news) {
        String sql = "UPDATE news SET title = ?, content = ?, author = ?, category_id = ? WHERE news_id = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, news.getTitle());
            ps.setString(2, news.getContent());
            ps.setString(3, news.getAuthor());
            ps.setInt(4, news.getCategoryId());
            ps.setInt(5, news.getNewsId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps);
        }

        return false;
    }

    /**
     * 根据 ID 查询（带分类名称）
     */
    public News findById(int newsId) {
        String sql = "SELECT n.*, c.category_name FROM news n " +
                "LEFT JOIN category c ON n.category_id = c.category_id " +
                "WHERE n.news_id = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, newsId);
            rs = ps.executeQuery();

            if (rs.next()) {
                return mapRowToNews(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }

        return null;
    }

    /**
     * 查询所有新闻
     */
    public List<News> findAll() {
        String sql = "SELECT n.*, c.category_name FROM news n " +
                "LEFT JOIN category c ON n.category_id = c.category_id " +
                "ORDER BY n.published_at DESC";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<News> list = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRowToNews(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }

        return list;
    }

    /**
     * 根据分类查询
     */
    public List<News> findByCategory(int categoryId) {
        String sql = "SELECT n.*, c.category_name FROM news n " +
                "LEFT JOIN category c ON n.category_id = c.category_id " +
                "WHERE n.category_id = ? " +
                "ORDER BY n.published_at DESC";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<News> list = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, categoryId);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRowToNews(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }

        return list;
    }

    /**
     * 增加浏览量（广告推荐用）
     */
    public boolean incrementViewCount(int newsId) {
        String sql = "UPDATE news SET view_count = view_count + 1 WHERE news_id = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, newsId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps);
        }

        return false;
    }

    /**
     * 映射 ResultSet 到 News 对象
     */
    private News mapRowToNews(ResultSet rs) throws SQLException {
        News news = new News();
        news.setNewsId(rs.getInt("news_id"));
        news.setTitle(rs.getString("title"));
        news.setContent(rs.getString("content"));
        news.setAuthor(rs.getString("author"));
        news.setCategoryId(rs.getInt("category_id"));
        news.setViewCount(rs.getInt("view_count"));
        news.setPublishedAt(rs.getTimestamp("published_at"));
        news.setUpdatedAt(rs.getTimestamp("updated_at"));
        news.setCategoryName(rs.getString("category_name"));
        return news;
    }
}