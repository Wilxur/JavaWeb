package com.example.news.controller;

import com.example.news.service.NewsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/news/delete")
public class DeleteNewsServlet extends HttpServlet {

    private final NewsService newsService = new NewsService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1️⃣ 登录校验（双保险，不能只信 JSP）
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // 2️⃣ 获取新闻 id
        int id = Integer.parseInt(req.getParameter("id"));

        // 3️⃣ 执行删除
        boolean success = newsService.deleteNews(id);

        // 4️⃣ 删除后统一回首页
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}