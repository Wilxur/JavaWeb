package com.example.news.test;

import com.example.news.model.News;
import com.example.news.service.NewsService;

public class NewsServiceTest {

    public static void main(String[] args) {

        NewsService newsService = new NewsService();

        News news = new News();
        news.setTitle("Service 测试新闻");
        news.setContent("通过 NewsService.addNews 插入");
        news.setCategoryId(1);

        boolean success = newsService.addNews(news);
        System.out.println("addNews result = " + success);

        System.out.println("news count = " + newsService.getAllNews().size());
    }
}