package com.example.news.service;

import com.example.news.dao.CategoryDao;
import com.example.news.model.Category;

import java.util.List;

/**
 * 分类业务逻辑层
 * 职责：封装业务逻辑，调用 DAO 层
 */
public class CategoryService {

    private CategoryDao categoryDao = new CategoryDao();

    /**
     * 获取所有分类
     */
    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }

    /**
     * 根据ID获取分类
     */
    public Category getCategoryById(int categoryId) {
        return categoryDao.findById(categoryId);
    }
}