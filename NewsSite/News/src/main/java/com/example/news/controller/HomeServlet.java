package com.example.news.controller;

import com.example.news.model.News;
import com.example.news.service.NewsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private final NewsService newsService = new NewsService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<News> newsList = newsService.getAllNews();
        System.out.println("newsList size = " + newsList.size());
        req.setAttribute("newsList", newsList);

        req.getRequestDispatcher("/WEB-INF/jsp/home.jsp")
                .forward(req, resp);
    }
}