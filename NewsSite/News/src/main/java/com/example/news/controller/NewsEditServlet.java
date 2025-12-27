package com.example.news.controller;

import com.example.news.model.News;
import com.example.news.service.NewsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/news/edit")
public class NewsEditServlet extends HttpServlet {

    private final NewsService newsService = new NewsService();

    /**
     * GET：打开编辑页面，回显新闻数据
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. 登录校验
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // 2. 获取新闻 id
        String idStr = req.getParameter("id");
        if (idStr == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "缺少新闻 id");
            return;
        }

        int id = Integer.parseInt(idStr);

        // 3. 查询新闻
        News news = newsService.getNewsById(id);
        if (news == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "新闻不存在");
            return;
        }

        // 4. 转发到编辑页面
        req.setAttribute("news", news);
        req.getRequestDispatcher("/WEB-INF/jsp/news/edit.jsp")
                .forward(req, resp);
    }

    /**
     * POST：提交修改，更新新闻
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        // 1. 登录校验
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // 2. 接收表单参数
        String idStr = req.getParameter("id");
        String title = req.getParameter("title");
        String content = req.getParameter("content");

        // 3. 参数校验
        if (idStr == null || title == null || content == null
                || title.isBlank() || content.isBlank()) {

            req.setAttribute("error", "标题和内容不能为空");
            doGet(req, resp);
            return;
        }

        int id = Integer.parseInt(idStr);

        // 4. 组装 News 对象
        News news = new News();
        news.setId(id);
        news.setTitle(title.trim());
        news.setContent(content.trim());

        // 5. 调用业务层更新
        boolean success = newsService.updateNews(news);

        // 6. 跳转
        if (success) {
            // 更新成功 → 回到详情页
            resp.sendRedirect(req.getContextPath() + "/detail?id=" + id);
        } else {
            req.setAttribute("error", "更新失败，请重试");
            doGet(req, resp);
        }
    }
}