package com.example.news.controller;

import com.example.news.model.News;
import com.example.news.service.NewsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/detail")
public class DetailServlet extends HttpServlet {

    private final NewsService newsService = new NewsService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        News news = newsService.getNewsById(id);

        if (news == null) {
            resp.sendError(404, "新闻不存在");
            return;
        }

        req.setAttribute("news", news);
        req.getRequestDispatcher("/WEB-INF/jsp/detail.jsp")
                .forward(req, resp);
    }
}