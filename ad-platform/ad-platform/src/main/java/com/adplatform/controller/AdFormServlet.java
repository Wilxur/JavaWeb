package com.adplatform.controller;

import com.adplatform.model.Ad;
import com.adplatform.model.User;
import com.adplatform.service.AdService;
import com.adplatform.service.impl.AdServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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

        // ★★★ 准备预定义分类选项 ★★★
        List<Map<String, String>> categories = List.of(
                Map.of("value", "electronics", "label", "电子产品"),
                Map.of("value", "clothing", "label", "服装鞋帽"),
                Map.of("value", "food", "label", "食品饮料"),
                Map.of("value", "beauty", "label", "美妆护肤"),
                Map.of("value", "home", "label", "家居用品"),
                Map.of("value", "sports", "label", "运动户外")
        );
        request.setAttribute("categories", categories);

        // 转发到表单页面（新增时ad为null，编辑时有值）
        request.getRequestDispatcher("/WEB-INF/views/ad-form.jsp").forward(request, response);
    }
}