package com.example.shop;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        // 验证数据
        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("error", "用户名不能为空！");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "密码不能为空！");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "两次输入的密码不一致！");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // 检查用户名是否已存在
        User existingUser = userDAO.findByUsername(username);
        if (existingUser != null) {
            request.setAttribute("error", "用户名已存在！");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // 创建新用户
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setPhone(phone);

        if (userDAO.create(newUser)) {
            // 注册成功，自动登录
            User savedUser = userDAO.findByUsername(username);
            HttpSession session = request.getSession();
            session.setAttribute("user", savedUser);
            session.setAttribute("isLoggedIn", true);

            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            request.setAttribute("error", "注册失败，请重试！");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}