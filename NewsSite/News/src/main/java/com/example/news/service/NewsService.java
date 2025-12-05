package com.example.news.service;

import com.example.news.dao.NewsDao;
import com.example.news.model.News;

import java.util.List;

public class NewsService {

    private NewsDao newsDao = new NewsDao();

    public List<News> getAllNews() {
        return newsDao.findAll();
    }

    public News getNewsById(int id) {
        return newsDao.findById(id);
    }
}