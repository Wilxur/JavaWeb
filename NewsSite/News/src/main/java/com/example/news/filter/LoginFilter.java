package com.example.news.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * 登录校验过滤器
 * 作用：
 *  - 未登录用户不能访问 /home
 *  - 未登录用户不能访问 /news/* 下的功能（发布 / 编辑 / 删除）
 */
@WebFilter(urlPatterns = {"/home", "/news/*"})
public class LoginFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        // 向下转型，拿到 HTTP 专属能力
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 不创建新 session，只获取已有的
        HttpSession session = req.getSession(false);

        // 判断是否已登录（唯一标准）
        boolean loggedIn =
                session != null &&
                        session.getAttribute("loginUser") != null;

        if (loggedIn) {
            // 已登录：放行，继续访问目标资源
            chain.doFilter(request, response);
        } else {
            // 未登录：重定向到登录页
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}