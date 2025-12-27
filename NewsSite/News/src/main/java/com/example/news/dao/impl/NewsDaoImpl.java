package com.example.news.dao.impl;

import com.example.news.dao.NewsDao;
import com.example.news.model.News;
import com.example.news.util.DBUtil;

import java.util.List;

/**
 * 新闻 DAO 实现
 */
public class NewsDaoImpl implements NewsDao {

    /* ========= C：发布新闻 ========= */
    private static final String SQL_INSERT =
            "INSERT INTO news (title, content, category_id) VALUES (?, ?, ?)";

    /* ========= R：新闻列表 ========= */
    private static final String SQL_FIND_ALL =
            "SELECT n.id, n.title, c.name AS category, " +
                    "n.publish_time, n.view_count " +
                    "FROM news n " +
                    "JOIN category c ON n.category_id = c.id " +
                    "ORDER BY n.publish_time DESC";

    /* ========= R：按分类查询 ========= */
    private static final String SQL_FIND_BY_CATEGORY =
            "SELECT n.id, n.title, c.name AS category, " +
                    "n.publish_time, n.view_count " +
                    "FROM news n " +
                    "JOIN category c ON n.category_id = c.id " +
                    "WHERE n.category_id = ? " +
                    "ORDER BY n.publish_time DESC";

    /* ========= R：新闻详情 ========= */
    private static final String SQL_FIND_BY_ID =
            "SELECT n.id, n.title, n.content, n.category_id, c.name AS category, " +
                    "n.publish_time, n.view_count " +
                    "FROM news n " +
                    "JOIN category c ON n.category_id = c.id " +
                    "WHERE n.id = ?";

    /* ========= U：更新新闻 ========= */
    private static final String SQL_UPDATE =
            "UPDATE news SET title = ?, content = ?, category_id = ? WHERE id = ?";

    /* ========= D：删除新闻 ========= */
    private static final String SQL_DELETE =
            "DELETE FROM news WHERE id = ?";

    @Override
    public int insert(News news) {
        return DBUtil.executeUpdate(
                SQL_INSERT,
                news.getTitle(),
                news.getContent(),
                news.getCategoryId()
        );
    }

    @Override
    public List<News> findAll() {
        return DBUtil.executeQuery(SQL_FIND_ALL, rs -> {
            News news = new News();
            news.setId(rs.getInt("id"));
            news.setTitle(rs.getString("title"));
            news.setCategory(rs.getString("category"));
            news.setPublishTime(rs.getTimestamp("publish_time"));
            news.setViewCount(rs.getInt("view_count"));
            return news;
        });
    }

    @Override
    public List<News> findByCategoryId(int categoryId) {
        return DBUtil.executeQuery(SQL_FIND_BY_CATEGORY, rs -> {
            News news = new News();
            news.setId(rs.getInt("id"));
            news.setTitle(rs.getString("title"));
            news.setCategory(rs.getString("category"));
            news.setPublishTime(rs.getTimestamp("publish_time"));
            news.setViewCount(rs.getInt("view_count"));
            return news;
        }, categoryId);
    }

    @Override
    public News findById(int id) {
        List<News> list = DBUtil.executeQuery(SQL_FIND_BY_ID, rs -> {
            News news = new News();
            news.setId(rs.getInt("id"));
            news.setTitle(rs.getString("title"));
            news.setContent(rs.getString("content"));
            news.setCategoryId(rs.getInt("category_id"));
            news.setCategory(rs.getString("category"));
            news.setPublishTime(rs.getTimestamp("publish_time"));
            news.setViewCount(rs.getInt("view_count"));
            return news;
        }, id);

        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int update(News news) {
        return DBUtil.executeUpdate(
                SQL_UPDATE,
                news.getTitle(),
                news.getContent(),
                news.getCategoryId(),
                news.getId()
        );
    }

    @Override
    public int deleteById(int id) {
        return DBUtil.executeUpdate(SQL_DELETE, id);
    }
}