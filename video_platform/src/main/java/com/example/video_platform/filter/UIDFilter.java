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

    /**
     * 广告平台使用的匿名 UID
     * ⚠️ 注意：此 UID 与登录无任何关系
     */
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

        // 如果不存在，则生成一个新的匿名 UID（仅用于广告）
        if (uid == null || uid.isBlank()) {
            uid = UUID.randomUUID().toString();
            Cookie c = new Cookie(COOKIE_NAME, uid);

            // 全站可用（供广告 SDK 使用）
            c.setPath("/");

            // 一年有效期（广告画像需要）
            c.setMaxAge(60 * 60 * 24 * 365);

            // 非登录 Cookie，不设置 HttpOnly
            resp.addCookie(c);
        }

        // ⚠️ 仅作为“广告 UID”，不要与登录混用
        req.setAttribute("ad_uid", uid);

        chain.doFilter(req, resp);
    }
}
