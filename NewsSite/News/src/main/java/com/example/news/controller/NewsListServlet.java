package com.example.news.controller;

import com.example.news.model.News;
import com.example.news.model.Category;
import com.example.news.service.NewsService;
import com.example.news.service.CategoryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/news/list")
public class NewsListServlet extends HttpServlet {

    private final NewsService newsService = new NewsService();
    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 获取全部新闻
        List<News> newsList = newsService.getAllNews();

        // 获取分类（用于筛选）
        List<Category> categories = categoryService.getAllCategories();

        req.setAttribute("newsList", newsList);
        req.setAttribute("categories", categories);

        req.getRequestDispatcher("/jsp/news-list.jsp").forward(req, resp);
    }
}