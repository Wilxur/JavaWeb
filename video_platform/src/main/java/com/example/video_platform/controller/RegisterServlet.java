package com.example.video_platform.controller;

import com.example.video_platform.service.UserService;
import com.example.video_platform.service.impl.UserServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String captcha = req.getParameter("captcha");
        String sessionCaptcha = (String) req.getSession().getAttribute("captcha");

        try {
            userService.register(username, password, captcha, sessionCaptcha);
            resp.sendRedirect(req.getContextPath() + "/login"); // 走 LoginPageServlet
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/register?error=" + encode(e.getMessage()));
        }
    }

    private String encode(String s) {
        return s == null ? "" : s.replace(" ", "%20");
    }
}
