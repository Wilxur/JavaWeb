package com.example.news.controller;

import com.example.news.model.User;
import com.example.news.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserService();

    /**
     * GET：访问 /login 时，展示登录页面
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp")
                .forward(request, response);
    }

    /**
     * POST：提交登录表单时，处理登录逻辑
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. 接收表单参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 2. 调用业务层判断是否能登录
        User user = userService.login(username, password);

        // 3. 根据业务结果处理
        if (user != null) {
            // 登录成功：写入 Session
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", user);

            // 重定向到新闻首页
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            // 登录失败：设置错误信息
            request.setAttribute("error", "用户名或密码错误");

            // 转发回登录页
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp")
                    .forward(request, response);
        }
    }
}