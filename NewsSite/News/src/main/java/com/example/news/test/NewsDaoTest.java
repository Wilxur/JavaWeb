package com.example.news.test;

import com.example.news.dao.NewsDao;
import com.example.news.dao.impl.NewsDaoImpl;
import com.example.news.model.News;

import java.util.List;

public class NewsDaoTest {

    public static void main(String[] args) {

        NewsDao newsDao = new NewsDaoImpl();

        System.out.println("===== 1. 测试 insert =====");

        News news = new News();
        news.setTitle("DAO 测试新闻");
        news.setContent("这是通过 NewsDaoTest 插入的一条测试新闻");
        news.setCategoryId(1); // 确保 category 表中存在 id=1

        int rows = newsDao.insert(news);
        System.out.println("insert rows = " + rows);

        System.out.println("\n===== 2. 测试 findAll =====");

        List<News> list = newsDao.findAll();
        System.out.println("news count = " + list.size());

        for (News n : list) {
            System.out.println(
                    n.getId() + " | " +
                            n.getCategory() + " | " +
                            n.getTitle()
            );
        }
    }
}