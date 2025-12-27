package com.example.news.service;

import com.example.news.dao.NewsDao;
import com.example.news.dao.impl.NewsDaoImpl;
import com.example.news.model.News;

import java.util.List;

/**
 * 新闻业务层
 */
public class NewsService {

    private final NewsDao newsDao = new NewsDaoImpl();

    /**
     * 发布新闻
     */
    public boolean addNews(News news) {
        return newsDao.insert(news) > 0;
    }

    /**
     * 查询新闻列表
     */
    public List<News> getAllNews() {
        return newsDao.findAll();
    }

    /**
     * 查询新闻详情
     */
    public News getNewsById(int id) {
        return newsDao.findById(id);
    }
    /**
     * 删除新闻
     */
    public boolean deleteNews(int id) {
        int rows = newsDao.deleteById(id);
        return rows > 0;
    }

    /**
     * 更新新闻（编辑）
     */
    public boolean updateNews(News news) {
        return newsDao.update(news) > 0;
    }

    public List<News> getNewsByCategory(int categoryId) {
        return newsDao.findByCategoryId(categoryId);
    }
}