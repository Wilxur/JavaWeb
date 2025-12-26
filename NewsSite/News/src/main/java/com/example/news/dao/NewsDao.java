package com.example.news.dao;

import com.example.news.model.News;

import java.util.List;

/**
 * 新闻数据访问接口（CRUD 能力定义）
 */
public interface NewsDao {

    /* ========= C ========= */
    int insert(News news);

    /* ========= R ========= */
    List<News> findAll();

    News findById(int id);

    /* ========= U ========= */
    int update(News news);

    /* ========= D ========= */
    int deleteById(int id);
}