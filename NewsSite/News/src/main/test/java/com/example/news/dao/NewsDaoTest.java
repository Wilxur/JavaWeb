package com.example.news;

import com.example.news.dao.NewsDao;
import com.example.news.model.News;
import org.junit.jupiter.api.Test;

import java.util.List;

public class NewsDaoTest {

    @Test
    public void testFindAll() {
        NewsDao dao = new NewsDao();
        List<News> list = dao.findAll();

        System.out.println("查询到新闻数量：" + list.size());
        for (News n : list) {
            System.out.println(n.getId() + " - " + n.getTitle());
        }
    }

    @Test
    public void testFindById() {
        NewsDao dao = new NewsDao();
        News news = dao.findById(1);

        if (news != null) {
            System.out.println("找到新闻：" + news.getTitle());
        } else {
            System.out.println("未找到 id=1 的新闻");
        }
    }
}