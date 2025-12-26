package com.example.video_platform.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 登录拦截器
 * 仅拦截上传相关操作
 */
@WebFilter({"/upload", "/uploadVideo"})
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 判断是否登录
        if (req.getSession().getAttribute("user") == null) {
            // 未登录，跳转到登录页
            resp.sendRedirect(req.getContextPath() + "/login?error=please_login");
            return;
        }

        // 已登录，放行
        chain.doFilter(request, response);
    }
}
