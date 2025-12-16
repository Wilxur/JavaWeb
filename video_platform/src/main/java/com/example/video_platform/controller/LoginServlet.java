package com.example.video_platform.controller;

import com.example.video_platform.model.User;
import com.example.video_platform.service.UserService;
import com.example.video_platform.service.impl.UserServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/loginDo")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    /**
     * 防止用户 GET 访问 /loginDo 出现空白页
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.sendRedirect(req.getContextPath() + "/login");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String captcha = req.getParameter("captcha");
        String sessionCaptcha = (String) req.getSession().getAttribute("captcha");

        try {
            User user = userService.login(username, password, captcha, sessionCaptcha);

            if (user == null) {
                resp.sendRedirect(req.getContextPath() + "/login?error=login_failed");
                return;
            }

            // 登录成功
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/videos");

        } catch (Exception e) {
            // 所有异常统一处理，绝不留空白页
            resp.sendRedirect(req.getContextPath() + "/login?error=" + encode(e.getMessage()));
        }
    }

    private String encode(String msg) {
        if (msg == null) return "login_error";
        return msg.replace(" ", "%20");
    }
}
