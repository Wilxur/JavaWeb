package com.example.news.dao;

import com.example.news.model.Category;
import java.util.List;

public interface CategoryDao {

    /**
     * 查询所有新闻分类
     */
    List<Category> findAll();
}