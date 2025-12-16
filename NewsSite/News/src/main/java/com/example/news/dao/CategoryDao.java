package com.example.news.dao;

import com.example.news.model.Category;
import java.util.List;

public interface CategoryDao {

    /**
     * 查询全部分类
     */
    List<Category> findAll();

    /**
     * 根据 ID 查询分类
     */
    Category findById(int id);
}