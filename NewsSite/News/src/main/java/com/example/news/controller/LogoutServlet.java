package com.example.news.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. 获取当前 session（如果存在）
        HttpSession session = request.getSession(false);

        // 2. 如果 session 存在，直接销毁
        if (session != null) {
            session.invalidate();
        }

        // 3. 跳回登录页
        response.sendRedirect(request.getContextPath() + "/login");
    }
}