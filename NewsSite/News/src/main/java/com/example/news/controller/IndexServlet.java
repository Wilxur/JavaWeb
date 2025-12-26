package com.example.news.controller;

import com.example.news.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/")
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 不自动创建 session：没有就返回 null
        HttpSession session = request.getSession(false);
        User loginUser = null;

        if (session != null) {
            Object obj = session.getAttribute("loginUser");
            if (obj instanceof User) {
                loginUser = (User) obj;
            }
        }

        if (loginUser == null) {
            // 未登录：转发到登录页（login.jsp 在 WEB-INF 下，必须 forward）
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp")
                    .forward(request, response);
        } else {
            // 已登录：重定向到首页
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }
}