package com.example.news.dao;

import com.example.news.model.News;

import java.util.List;

public interface NewsDao {

    List<News> findAll();

    News findById(int id);
}