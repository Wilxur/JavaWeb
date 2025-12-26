package com.example.news.controller;

import com.example.news.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserService userService = new UserService();

    /**
     * GET：展示注册页面
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/register.jsp")
                .forward(request, response);
    }

    /**
     * POST：处理注册逻辑
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Step 1：接收参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Step 2：调用业务层注册
        boolean success = userService.register(username, password);

        // Step 3：根据结果跳转
        if (success) {
            // 注册成功：跳转到登录页（而不是首页）
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            // 注册失败：用户名已存在
            request.setAttribute("error", "用户名已存在，请换一个");

            request.getRequestDispatcher("/WEB-INF/jsp/register.jsp")
                    .forward(request, response);
        }
    }
}