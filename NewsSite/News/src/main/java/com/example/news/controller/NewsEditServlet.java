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

@WebServlet("/news/edit")
public class NewsEditServlet extends HttpServlet {

    private final NewsService newsService = new NewsService();
    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        News news = newsService.getNewsDetail(id);

        List<Category> categories = categoryService.getAllCategories();

        req.setAttribute("news", news);
        req.setAttribute("categories", categories);

        req.getRequestDispatcher("/jsp/news-edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(req.getParameter("id"));
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String author = req.getParameter("author");
        int categoryId = Integer.parseInt(req.getParameter("categoryId"));

        News news = new News();
        news.setNewsId(id);
        news.setTitle(title);
        news.setContent(content);
        news.setAuthor(author);
        news.setCategoryId(categoryId);

        newsService.updateNews(news);

        resp.sendRedirect(req.getContextPath() + "/news/detail?id=" + id);
    }
}