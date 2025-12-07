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

/**
 * 新闻列表 Servlet
 * URL: /news/list
 */
@WebServlet("/news/list")
public class NewsListServlet extends HttpServlet {

    private NewsService newsService = new NewsService();
    private CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. 获取请求参数（可选的分类ID）
        String categoryIdStr = request.getParameter("categoryId");

        // 2. 调用 Service 获取数据
        List<News> newsList;

        if (categoryIdStr != null && !categoryIdStr.trim().isEmpty()) {
            // 按分类查询
            int categoryId = Integer.parseInt(categoryIdStr);
            newsList = newsService.getNewsByCategory(categoryId);
        } else {
            // 查询所有
            newsList = newsService.getAllNews();
        }

        // 3. 获取所有分类（用于导航栏）
        List<Category> categoryList = categoryService.getAllCategories();

        // 4. 将数据存入 request 域
        request.setAttribute("newsList", newsList);
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("currentCategoryId", categoryIdStr);

        // 5. 转发到 JSP 页面
        request.getRequestDispatcher("/jsp/news-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // POST 请求也走 GET 逻辑
        doGet(request, response);
    }
}