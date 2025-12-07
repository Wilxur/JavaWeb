package com.example.news.controller;

import com.example.news.model.News;
import com.example.news.service.NewsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 新闻详情 Servlet
 * URL: /news/detail?id=xxx
 */
@WebServlet("/news/detail")
public class NewsDetailServlet extends HttpServlet {

    private NewsService newsService = new NewsService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. 获取新闻ID
        String idStr = request.getParameter("id");

        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/news/list");
            return;
        }

        try {
            int newsId = Integer.parseInt(idStr);

            // 2. 增加浏览量
            newsService.incrementViewCount(newsId);

            // 3. 获取新闻详情
            News news = newsService.getNewsById(newsId);

            if (news == null) {
                response.sendRedirect(request.getContextPath() + "/news/list");
                return;
            }

            // 4. 存入 request
            request.setAttribute("news", news);

            // 5. 转发到详情页
            request.getRequestDispatcher("/jsp/news-detail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/news/list");
        }
    }
}