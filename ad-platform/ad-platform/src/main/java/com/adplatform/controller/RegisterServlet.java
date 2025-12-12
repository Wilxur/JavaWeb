package com.adplatform.controller;

import com.adplatform.model.User;
import com.adplatform.service.UserService;
import com.adplatform.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 显示注册页面
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");

        // 1. 验证密码一致性
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "两次输入的密码不一致");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        // 2. 验证密码长度
        if (password.length() < 6) {
            request.setAttribute("error", "密码长度不能少于6位");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        // 3. 调用Service注册
        User user = userService.register(username, password, email);

        if (user != null) {
            // 注册成功，重定向到登录页（防止重复提交）
            response.sendRedirect(request.getContextPath() + "/login?registered=true");
        } else {
            // 注册失败（用户名已存在）
            request.setAttribute("error", "用户名 '" + username + "' 已存在，请使用其他用户名");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }
}