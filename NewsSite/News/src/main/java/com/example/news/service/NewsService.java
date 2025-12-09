package com.example.news.service;

import com.example.news.dao.NewsDao;
import com.example.news.model.News;

import java.util.List;

/**
 * News 业务逻辑层
 */
public class NewsService {

    private final NewsDao newsDao = new NewsDao();

    /**
     * 查询全部新闻（含分类名称）
     */
    public List<News> getAllNews() {
        return newsDao.findAll();
    }

    /**
     * 按分类查询
     */
    public List<News> getNewsByCategory(int categoryId) {
        return newsDao.findByCategory(categoryId);
    }

    /**
     * 查询新闻详情
     */
    public News getNewsDetail(int id) {

        // 1. 浏览量 +1（线程安全）
        newsDao.incrementViewCount(id);

        // 2. 再查询新闻详情
        return newsDao.findById(id);
    }

    /**
     * 新增新闻
     */
    public void addNews(News news) {

        // 简单参数检查
        if (news.getTitle() == null || news.getTitle().isEmpty()) {
            throw new RuntimeException("新闻标题不能为空");
        }
        if (news.getContent() == null || news.getContent().isEmpty()) {
            throw new RuntimeException("新闻内容不能为空");
        }

        newsDao.insert(news);
    }

    /**
     * 编辑新闻
     */
    public void updateNews(News news) {
        newsDao.update(news);
    }

    /**
     * 删除新闻
     */
    public void deleteNews(int id) {
        newsDao.delete(id);
    }
}