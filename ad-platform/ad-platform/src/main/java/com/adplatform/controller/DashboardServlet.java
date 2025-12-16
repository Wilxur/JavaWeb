package com.adplatform.controller;

import com.adplatform.model.User;
import com.adplatform.service.DashboardService;
import com.adplatform.service.impl.DashboardServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DashboardService dashboardService;

    @Override
    public void init() throws ServletException {
        dashboardService = new DashboardServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. Session验证，防止未登录访问
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            // 未登录，重定向到登录页
            response.sendRedirect(request.getContextPath() + "/login?redirect=dashboard");
            return;
        }

        // 2. 获取当前登录用户
        User currentUser = (User) session.getAttribute("user");

        // 3. 获取统计数据
        Map<String, Object> stats = dashboardService.getDashboardStats();

        // 4. 设置Request属性供JSP使用
        request.setAttribute("stats", stats);
        request.setAttribute("currentUser", currentUser);

        // 5. 转发到JSP页面
        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }
}