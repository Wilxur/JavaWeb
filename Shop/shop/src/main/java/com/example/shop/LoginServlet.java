package com.example.shop;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
            if (isLoggedIn != null && isLoggedIn) {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
        }

        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String captcha = request.getParameter("captcha");
        String remember = request.getParameter("remember");

        // 验证码验证
        HttpSession session = request.getSession();
        String sessionCaptcha = (String) session.getAttribute("captcha");
        Long captchaTime = (Long) session.getAttribute("captchaTime");

        session.removeAttribute("captcha");
        session.removeAttribute("captchaTime");

        boolean isCaptchaValid = false;
        if (sessionCaptcha != null && captchaTime != null) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - captchaTime) <= 5 * 60 * 1000) {
                isCaptchaValid = sessionCaptcha.equalsIgnoreCase(captcha);
            }
        }

        if (!isCaptchaValid) {
            request.setAttribute("error", "验证码错误或已过期！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // 从数据库验证用户
        User user = userDAO.findByUsername(username);

        if (user != null && password.equals(user.getPassword())) {
            // 登录成功
            session.setAttribute("user", user);
            session.setAttribute("isLoggedIn", true);

            if ("on".equals(remember)) {
                Cookie usernameCookie = new Cookie("username", username);
                usernameCookie.setMaxAge(7 * 24 * 60 * 60);
                usernameCookie.setPath("/");
                response.addCookie(usernameCookie);
            } else {
                Cookie usernameCookie = new Cookie("username", "");
                usernameCookie.setMaxAge(0);
                usernameCookie.setPath("/");
                response.addCookie(usernameCookie);
            }

            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            request.setAttribute("error", "用户名或密码错误！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}