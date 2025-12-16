package com.example.video_platform.dao;

import com.example.video_platform.model.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();
}
