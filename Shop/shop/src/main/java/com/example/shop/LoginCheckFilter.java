package com.example.shop;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class LoginCheckFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        if (requestURI.contains("/login") ||
                requestURI.contains("/captcha") ||
                requestURI.contains(".css") ||
                requestURI.contains(".js") ||
                requestURI.contains(".jpg") ||
                requestURI.contains(".png")) {
            chain.doFilter(request, response);
            return;
        }


        if (requestURI.endsWith("/index.jsp") || requestURI.endsWith("/shop/")) {
            HttpSession session = httpRequest.getSession(false);
            if (session == null || session.getAttribute("isLoggedIn") == null ||
                    !(Boolean) session.getAttribute("isLoggedIn")) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 清理
    }
}