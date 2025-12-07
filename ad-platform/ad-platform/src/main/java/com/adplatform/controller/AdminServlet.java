package com.adplatform.controller;

import com.adplatform.model.Ad;
import com.adplatform.service.AdService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class AdminServlet extends HttpServlet {
    private AdService adService = new AdService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo();

        // **关键修复**：直接访问JSP文件时不检查登录
        String uri = req.getRequestURI();
        if (uri.endsWith(".jsp")) {
            // 直接转发到JSP，不经过Servlet逻辑
            req.getRequestDispatcher(req.getServletPath() + path).forward(req, resp);
            return;
        }

        // 原来的登录检查逻辑
        if (!isLoggedIn(req) && !"/login".equals(path)) {
            resp.sendRedirect(req.getContextPath() + "/admin/login.jsp");
            return;
        }

        try {
            switch (path) {
                case "/dashboard":
                    req.getRequestDispatcher("/admin/dashboard.jsp").forward(req, resp);
                    break;
                case "/ad/list":
                    handleAdList(req, resp);
                    break;
                case "/ad/add":
                    req.getRequestDispatcher("/admin/ad-add.jsp").forward(req, resp);
                    break;
                case "/logout":
                    req.getSession().invalidate();
                    resp.sendRedirect(req.getContextPath() + "/admin/login.jsp");
                    break;
                default:
                    resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            }
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("Error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo();

        if ("/ad/add".equals(path)) {
            handleAdAdd(req, resp);
        } else if ("/login".equals(path)) {
            handleLogin(req, resp);
        }
    }

    private void handleAdList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Ad> ads = adService.getAllAds();
        req.setAttribute("ads", ads);
        req.getRequestDispatcher("/admin/ad-list.jsp").forward(req, resp);
    }

    private void handleAdAdd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Ad ad = new Ad();
        ad.setAdvertiserId(1);
        ad.setTitle(req.getParameter("title"));
        ad.setAdType(req.getParameter("adType"));
        ad.setContent(req.getParameter("content"));
        ad.setCategory(req.getParameter("category"));
        ad.setTargetUrl(req.getParameter("targetUrl"));

        int adId = adService.addAd(ad);
        if (adId > 0) {
            resp.sendRedirect(req.getContextPath() + "/admin/ad/list?success=true");
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/ad/add?error=true");
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if ("admin".equals(username) && "123456".equals(password)) {
            req.getSession().setAttribute("admin", username);
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/login.jsp?error=true");
        }
    }

    private boolean isLoggedIn(HttpServletRequest req) {
        return req.getSession().getAttribute("admin") != null;
    }
}