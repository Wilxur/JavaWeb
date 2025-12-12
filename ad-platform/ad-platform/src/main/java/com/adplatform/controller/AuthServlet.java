package com.adplatform.controller;

import com.adplatform.model.User;
import com.adplatform.service.UserService;
import com.adplatform.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet({"/auth", "/login"})
public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        // 初始化Service层
        userService = new UserServiceImpl();
    }

    /**
     * GET请求：显示登录页面
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 如果用户已登录，直接跳转到后台
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        // 否则显示登录页面
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    /**
     * POST请求：处理登录验证
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置请求编码（防止中文乱码）
        request.setCharacterEncoding("UTF-8");

        // 1. 获取表单参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe"); // "on"表示选中

        // 2. 参数基本校验
        if (username == null || username.trim().isEmpty() ||
                password == null || password.isEmpty()) {
            request.setAttribute("error", "用户名和密码不能为空");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        try {
            // 3. 调用Service层进行登录验证
            User user = userService.login(username.trim(), password);

            if (user != null) {
                // 4. 登录成功
                HttpSession session = request.getSession();

                // 将User对象存入Session（后续页面需要用户信息）
                session.setAttribute("user", user);
                session.setAttribute("username", user.getUsername());
                session.setAttribute("role", user.getRole());

                // 5. 处理"记住我"功能
                if ("on".equals(rememberMe)) {
                    // 创建Cookie，有效期7天
                    Cookie usernameCookie = new Cookie("rememberUsername", user.getUsername());
                    usernameCookie.setMaxAge(7 * 24 * 60 * 60); // 7天
                    usernameCookie.setPath(request.getContextPath());

                    Cookie rememberCookie = new Cookie("rememberMe", "true");
                    rememberCookie.setMaxAge(7 * 24 * 60 * 60);
                    rememberCookie.setPath(request.getContextPath());

                    response.addCookie(usernameCookie);
                    response.addCookie(rememberCookie);
                } else {
                    // 删除Cookie
                    Cookie usernameCookie = new Cookie("rememberUsername", "");
                    usernameCookie.setMaxAge(0);
                    usernameCookie.setPath(request.getContextPath());

                    Cookie rememberCookie = new Cookie("rememberMe", "");
                    rememberCookie.setMaxAge(0);
                    rememberCookie.setPath(request.getContextPath());

                    response.addCookie(usernameCookie);
                    response.addCookie(rememberCookie);
                }

                // 6. 重定向到管理后台首页
                String redirectUrl = request.getContextPath() + "/dashboard";

                // 如果有原始请求地址，登录后跳转回去
                String originalUrl = (String) session.getAttribute("originalUrl");
                if (originalUrl != null) {
                    session.removeAttribute("originalUrl");
                    redirectUrl = originalUrl;
                }

                response.sendRedirect(redirectUrl);

            } else {
                // 7. 登录失败
                // 记录失败次数（可选）
                HttpSession session = request.getSession();
                Integer failCount = (Integer) session.getAttribute("loginFailCount");
                if (failCount == null) {
                    failCount = 0;
                }
                session.setAttribute("loginFailCount", ++failCount);

                request.setAttribute("error", "用户名或密码错误");
                request.setAttribute("username", username); // 回显用户名
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            // 8. 异常处理
            e.printStackTrace(); // 生产环境应使用日志框架
            request.setAttribute("error", "系统错误，请稍后再试：" + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }

    /**
     * 注销登录
     */
    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 销毁Session
        }

        // 清除记住我Cookie
        Cookie usernameCookie = new Cookie("rememberUsername", "");
        usernameCookie.setMaxAge(0);
        usernameCookie.setPath(request.getContextPath());
        response.addCookie(usernameCookie);

        Cookie rememberCookie = new Cookie("rememberMe", "");
        rememberCookie.setMaxAge(0);
        rememberCookie.setPath(request.getContextPath());
        response.addCookie(rememberCookie);

        // 重定向到登录页
        response.sendRedirect(request.getContextPath() + "/login?loggedOut=true");
    }
}