package com.example.news.service;

import com.example.news.dao.CategoryDao;
import com.example.news.dao.impl.CategoryDaoImpl;
import com.example.news.model.Category;

import java.util.List;

public class CategoryService {

    private final CategoryDao categoryDao = new CategoryDaoImpl();

    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }
}