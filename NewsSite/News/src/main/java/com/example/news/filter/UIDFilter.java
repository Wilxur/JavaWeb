package com.example.news.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

/**
 * 统一匿名用户 UID 生成 Filter
 */
@WebFilter("/*")
public class UIDFilter implements Filter {

    private static final String COOKIE_NAME = "news_uid";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        Cookie[] cookies = req.getCookies();
        String uid = null;

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (COOKIE_NAME.equals(c.getName())) {
                    uid = c.getValue();
                    break;
                }
            }
        }

        // 不存在 UID → 自动生成
        if (uid == null) {
            uid = "NEWS_" + UUID.randomUUID().toString();

            Cookie cookie = new Cookie(COOKIE_NAME, uid);
            cookie.setMaxAge(365 * 24 * 60 * 60); // 1 年
            cookie.setPath("/");

            resp.addCookie(cookie);
        }

        chain.doFilter(request, response);
    }
}