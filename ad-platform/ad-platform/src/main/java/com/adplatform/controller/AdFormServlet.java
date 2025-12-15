package com.adplatform.controller;

import com.adplatform.model.Ad;
import com.adplatform.model.User;
import com.adplatform.service.AdService;
import com.adplatform.service.impl.AdServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet({"/ad/new", "/ad/edit"})  // 两个路径：新增和编辑
public class AdFormServlet extends HttpServlet {
    private AdService adService = new AdServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 验证登录
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User currentUser = (User) session.getAttribute("user");

        // 判断是新增还是编辑
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            // 编辑：查询广告并填充表单
            int id = Integer.parseInt(idParam);
            Ad ad = adService.findById(id, currentUser.getId());

            if (ad == null) {
                // 无权访问或广告不存在
                response.sendRedirect(request.getContextPath() + "/ads?error=notfound");
                return;
            }

            request.setAttribute("ad", ad);
        }

        // 转发到表单页面（新增时ad为null，编辑时有值）
        request.getRequestDispatcher("/WEB-INF/views/ad-form.jsp").forward(request, response);
    }
}