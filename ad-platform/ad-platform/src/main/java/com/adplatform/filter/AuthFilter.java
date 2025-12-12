package com.adplatform.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({"/dashboard", "/ads", "/users", "/reports", "/settings"})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        // 检查Session中是否有用户
        if (session != null && session.getAttribute("user") != null) {
            // 已登录，放行
            chain.doFilter(request, response);
        } else {
            // 未登录，保存原始请求URL，重定向到登录页
            String requestURL = req.getRequestURL().toString();
            String queryString = req.getQueryString();
            if (queryString != null) {
                requestURL += "?" + queryString;
            }

            // 保存到Session（如果Session存在）
            HttpSession newSession = req.getSession(true);
            newSession.setAttribute("originalUrl", requestURL);

            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}