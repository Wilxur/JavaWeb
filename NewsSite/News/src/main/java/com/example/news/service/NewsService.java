package com.example.news.service;

import com.example.news.dao.NewsDao;
import com.example.news.model.News;

import java.util.List;

/**
 * 新闻业务逻辑层
 * 职责：封装业务逻辑，调用 DAO 层
 */
public class NewsService {

    private NewsDao newsDao = new NewsDao();

    /**
     * 获取所有新闻列表
     */
    public List<News> getAllNews() {
        return newsDao.findAll();
    }

    /**
     * 根据分类获取新闻
     */
    public List<News> getNewsByCategory(int categoryId) {
        return newsDao.findByCategory(categoryId);
    }

    /**
     * 根据ID获取新闻详情
     */
    public News getNewsById(int newsId) {
        return newsDao.findById(newsId);
    }

    /**
     * 添加新闻
     * @return 新增的新闻ID，失败返回-1
     */
    public int addNews(News news) {
        // 业务校验
        if (news.getTitle() == null || news.getTitle().trim().isEmpty()) {
            return -1;
        }
        if (news.getContent() == null || news.getContent().trim().isEmpty()) {
            return -1;
        }

        // 设置默认作者
        if (news.getAuthor() == null || news.getAuthor().trim().isEmpty()) {
            news.setAuthor("匿名");
        }

        // 初始化浏览量
        news.setViewCount(0);

        return newsDao.insert(news);
    }

    /**
     * 更新新闻
     */
    public boolean updateNews(News news) {
        if (news.getNewsId() == null) {
            return false;
        }
        return newsDao.update(news);
    }

    /**
     * 删除新闻
     */
    public boolean deleteNews(int newsId) {
        return newsDao.deleteById(newsId);
    }

    /**
     * 增加浏览量（用户阅读时调用，为广告推荐提供数据）
     */
    public void incrementViewCount(int newsId) {
        newsDao.incrementViewCount(newsId);
    }
}