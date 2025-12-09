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

@WebServlet("/news/add")
public class NewsAddServlet extends HttpServlet {

    private final NewsService newsService = new NewsService();
    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Category> categories = categoryService.getAllCategories();
        req.setAttribute("categories", categories);

        req.getRequestDispatcher("/jsp/news-add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String author = req.getParameter("author");
        int categoryId = Integer.parseInt(req.getParameter("categoryId"));

        News news = new News(title, content, author, categoryId);

        newsService.addNews(news);

        resp.sendRedirect(req.getContextPath() + "/news/list");
    }
}