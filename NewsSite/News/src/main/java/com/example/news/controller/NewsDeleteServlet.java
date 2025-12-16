package com.example.news.controller;

import com.example.news.service.NewsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/news/delete")
public class NewsDeleteServlet extends HttpServlet {

    private final NewsService newsService = new NewsService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        newsService.deleteNews(id);

        resp.sendRedirect(req.getContextPath() + "/news/list");
    }
}