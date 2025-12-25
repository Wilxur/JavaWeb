package com.example.news.dao.impl;

import com.example.news.dao.NewsDao;
import com.example.news.model.News;
import com.example.news.util.DBUtil;

import java.util.List;

public class NewsDaoImpl implements NewsDao {

    /**
     * 新闻列表：只查必要字段 + 分类名
     */
    private static final String SQL_FIND_ALL =
            "SELECT n.id, n.title, c.name AS category, " +
                    "n.publish_time, n.view_count " +
                    "FROM news n " +
                    "JOIN category c ON n.category_id = c.id " +
                    "ORDER BY n.publish_time DESC";

    /**
     * 新闻详情：查完整内容 + 分类名
     */
    private static final String SQL_FIND_BY_ID =
            "SELECT n.id, n.title, n.content, c.name AS category, " +
                    "n.publish_time, n.view_count " +
                    "FROM news n " +
                    "JOIN category c ON n.category_id = c.id " +
                    "WHERE n.id = ?";

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
    public News findById(int id) {
        List<News> list = DBUtil.executeQuery(SQL_FIND_BY_ID, rs -> {
            News news = new News();
            news.setId(rs.getInt("id"));
            news.setTitle(rs.getString("title"));
            news.setContent(rs.getString("content"));
            news.setCategory(rs.getString("category"));
            news.setPublishTime(rs.getTimestamp("publish_time"));
            news.setViewCount(rs.getInt("view_count"));
            return news;
        }, id);

        return list.isEmpty() ? null : list.get(0);
    }
}