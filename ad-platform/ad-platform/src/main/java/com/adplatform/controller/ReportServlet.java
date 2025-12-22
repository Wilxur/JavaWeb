package com.adplatform.controller;

import com.adplatform.model.ReportFilter;
import com.adplatform.model.User;
import com.adplatform.service.ReportService;
import com.adplatform.service.impl.ReportServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/reports")
public class ReportServlet extends HttpServlet {
    private ReportService reportService = new ReportServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 验证登录
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User currentUser = (User) session.getAttribute("user");

        // 获取筛选参数
        ReportFilter filter = new ReportFilter();
        filter.setStartDate(request.getParameter("startDate"));
        filter.setEndDate(request.getParameter("endDate"));

        // 生成报表
        Map<String, Object> report = reportService.generateReport(currentUser, filter);

        // 转发到JSP
        request.setAttribute("report", report);
        request.getRequestDispatcher("/WEB-INF/views/reports.jsp").forward(request, response);
    }
}