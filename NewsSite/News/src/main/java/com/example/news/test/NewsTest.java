package com.example.news.test;

import com.example.news.dao.NewsDao;
import com.example.news.dao.impl.NewsDaoImpl;
import com.example.news.model.News;

import java.util.List;

/**
 * Day 4 新闻模块测试
 */
public class NewsTest {

    public static void main(String[] args) {

        NewsDao newsDao = new NewsDaoImpl();

        /*List<News> list = newsDao.findLatest(5);
        for (News n : list) {
            System.out.println(n.getId() + " | " + n.getTitle());
        }*/
    }
}