package com.example.shop;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter("/*")
public class CookieFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (httpRequest.getServletPath().equals("/login.jsp") ||
                httpRequest.getServletPath().equals("/login")) {

            String savedUsername = "";
            Cookie[] cookies = httpRequest.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("username".equals(cookie.getName())) {
                        savedUsername = cookie.getValue();
                        break;
                    }
                }
            }

            httpRequest.setAttribute("savedUsername", savedUsername);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 清理
    }
}