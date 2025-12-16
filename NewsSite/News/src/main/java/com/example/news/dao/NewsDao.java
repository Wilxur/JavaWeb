package com.example.news.dao;

import com.example.news.model.News;
import java.util.List;

public interface NewsDao {

    /**
     * 查询全部新闻（含分类名称）
     */
    List<News> findAll();

    /**
     * 按分类查询
     */
    List<News> findByCategory(int categoryId);

    /**
     * 根据 ID 查询详情
     */
    News findById(int id);

    /**
     * 浏览量 +1
     */
    void incrementViewCount(int id);

    /**
     * 新增新闻
     */
    void insert(News news);

    /**
     * 更新新闻
     */
    void update(News news);

    /**
     * 删除新闻
     */
    void delete(int id);
}