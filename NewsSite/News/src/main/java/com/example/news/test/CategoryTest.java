package com.example.news.test;

import com.example.news.dao.CategoryDao;
import com.example.news.dao.impl.CategoryDaoImpl;
import com.example.news.model.Category;

import java.util.List;

/**
 * Day 3 分类模块测试
 */
public class CategoryTest {

    public static void main(String[] args) {

        CategoryDao categoryDao = new CategoryDaoImpl();
        List<Category> categories = categoryDao.findAll();

        for (Category c : categories) {
            System.out.println(c.getId() + " - " + c.getName());
        }
    }
}