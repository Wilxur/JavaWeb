package com.example.news.controller;

import com.example.news.model.Category;
import com.example.news.model.News;
import com.example.news.service.CategoryService;
import com.example.news.service.NewsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/news/add")
public class NewsAddServlet extends HttpServlet {

    private final NewsService newsService = new NewsService();
    private final CategoryService categoryService = new CategoryService();

    /**
     * GET：展示发布页面
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Category> categories = categoryService.getAllCategories(); // 若你方法名不同就替换
        req.setAttribute("categories", categories);

        req.getRequestDispatcher("/WEB-INF/jsp/news/add.jsp")
                .forward(req, resp);
    }

    /**
     * POST：提交发布（入库）
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String categoryIdStr = req.getParameter("categoryId");

        // 最小校验：缺任何一个都不允许入库
        if (title == null || title.isBlank()
                || content == null || content.isBlank()
                || categoryIdStr == null || categoryIdStr.isBlank()) {

            req.setAttribute("error", "标题、内容、分类不能为空");
            doGet(req, resp); // 复用 doGet：重新带上分类列表再渲染页面
            return;
        }

        int categoryId;
        try {
            categoryId = Integer.parseInt(categoryIdStr);
        } catch (NumberFormatException e) {
            req.setAttribute("error", "分类参数不合法");
            doGet(req, resp);
            return;
        }

        News news = new News();
        news.setTitle(title.trim());
        news.setContent(content.trim());
        news.setCategoryId(categoryId);

        boolean ok = newsService.addNews(news);

        if (ok) {
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            req.setAttribute("error", "发布失败，请重试");
            doGet(req, resp);
        }
    }
}