package com.example.video_platform.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebFilter("/*")
public class UIDFilter implements Filter {

    private static final String COOKIE_NAME = "ad_platform_uid";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uid = null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (COOKIE_NAME.equals(c.getName())) {
                    uid = c.getValue();
                    break;
                }
            }
        }

        if (uid == null) {
            uid = UUID.randomUUID().toString();
            Cookie c = new Cookie(COOKIE_NAME, uid);
            c.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath());
            c.setMaxAge(60 * 60 * 24 * 365);
            resp.addCookie(c);
        }

        req.setAttribute("uid", uid);
        chain.doFilter(req, resp);
    }
}
