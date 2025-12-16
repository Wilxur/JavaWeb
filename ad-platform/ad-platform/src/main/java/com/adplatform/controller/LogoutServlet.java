package com.adplatform.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. 获取当前Session（如果存在）
        HttpSession session = request.getSession(false);

        if (session != null) {
            // 2. 销毁Session（清除所有用户数据）
            session.invalidate();
        }

        // 3. 清除"记住我"Cookie
        Cookie usernameCookie = new Cookie("rememberUsername", "");
        usernameCookie.setMaxAge(0); // 立即过期
        usernameCookie.setPath(request.getContextPath());
        response.addCookie(usernameCookie);

        Cookie rememberCookie = new Cookie("rememberMe", "");
        rememberCookie.setMaxAge(0);
        rememberCookie.setPath(request.getContextPath());
        response.addCookie(rememberCookie);

        // 4. 重定向到登录页（带退出成功提示）
        response.sendRedirect(request.getContextPath() + "/login?loggedOut=true");
    }
}