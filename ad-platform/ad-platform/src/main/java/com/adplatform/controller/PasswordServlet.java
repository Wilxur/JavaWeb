package com.adplatform.controller;

import com.adplatform.model.User;
import com.adplatform.service.UserService;
import com.adplatform.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet({"/password", "/password/change"})
public class PasswordServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    // GET：显示修改密码页面
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/password.jsp").forward(request, response);
    }

    // POST：处理密码修改
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User currentUser = (User) session.getAttribute("user");

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // 验证
        if (oldPassword == null || newPassword == null || confirmPassword == null) {
            request.setAttribute("error", "请填写所有密码字段");
            doGet(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "两次输入的新密码不一致");
            doGet(request, response);
            return;
        }

        if (newPassword.length() < 6) {
            request.setAttribute("error", "新密码长度不能少于6位");
            doGet(request, response);
            return;
        }

        // 修改密码
        boolean success = userService.changePassword(currentUser.getId(), oldPassword, newPassword);

        if (success) {
            // 成功：退出登录
            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/login?passwordChanged=true");
        } else {
            // 失败：旧密码错误
            request.setAttribute("error", "旧密码不正确");
            doGet(request, response);
        }
    }
}