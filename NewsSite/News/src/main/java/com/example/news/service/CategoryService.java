package com.example.news.service;

import com.example.news.dao.CategoryDao;
import com.example.news.dao.impl.CategoryDaoImpl;
import com.example.news.model.Category;

import java.util.List;

/**
 * Category 业务逻辑层
 */
public class CategoryService {

    private final CategoryDao categoryDao = new CategoryDaoImpl();

    /**
     * 查询全部分类
     */
    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }

    /**
     * 根据 ID 获取分类
     */
    public Category getCategoryById(int id) {
        return categoryDao.findById(id);
    }
}