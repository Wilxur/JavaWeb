package com.example.news.controller;

import com.example.news.model.News;
import com.example.news.service.NewsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/news/detail")
public class NewsDetailServlet extends HttpServlet {

    private final NewsService newsService = new NewsService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idStr = req.getParameter("id");
        int id = Integer.parseInt(idStr);

        // 浏览量+1 并返回新闻详情
        News news = newsService.getNewsDetail(id);

        req.setAttribute("news", news);
        req.getRequestDispatcher("/jsp/news-detail.jsp").forward(req, resp);
    }
}