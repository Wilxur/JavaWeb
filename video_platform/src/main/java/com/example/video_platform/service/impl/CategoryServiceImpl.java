package com.example.video_platform.service.impl;

import com.example.video_platform.dao.CategoryDao;
import com.example.video_platform.dao.impl.CategoryDaoImpl;
import com.example.video_platform.model.Category;
import com.example.video_platform.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> listAll() {
        return categoryDao.findAll();
    }
}
