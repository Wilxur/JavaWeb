package com.adplatform.controller;

import com.adplatform.model.Ad;
import com.adplatform.model.User;
import com.adplatform.service.AdService;
import com.adplatform.service.impl.AdServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/ad/save")  // 提交保存（新增+编辑）
public class AdSaveServlet extends HttpServlet {
    private AdService adService = new AdServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // 验证登录
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User currentUser = (User) session.getAttribute("user");

        try {
            // 获取表单参数
            String idParam = request.getParameter("id");
            String title = request.getParameter("title");
            String adType = request.getParameter("adType");
            String content = request.getParameter("content");
            String category = request.getParameter("category");
            String tags = request.getParameter("tags");

            // ★★★ 全面验证：所有必填字段不能为空 ★★★
            if (title == null || title.trim().isEmpty() ||
                    content == null || content.trim().isEmpty() ||
                    category == null || category.trim().isEmpty() ||
                    tags == null || tags.trim().isEmpty()) {

                request.setAttribute("error", "所有字段都必须填写");
                request.setAttribute("ad", createAdFromParams(idParam, title, adType, content, category, tags));
                request.getRequestDispatcher("/WEB-INF/views/ad-form.jsp").forward(request, response);
                return;
            }

            // 验证JSON格式（简单检查）
            if (!tags.trim().startsWith("[") || !tags.trim().endsWith("]")) {
                request.setAttribute("error", "标签格式错误，必须是JSON数组格式，如：[\"科技\",\"数码\"]");
                request.setAttribute("ad", createAdFromParams(idParam, title, adType, content, category, tags));
                request.getRequestDispatcher("/WEB-INF/views/ad-form.jsp").forward(request, response);
                return;
            }

            // 创建Ad对象
            Ad ad = createAdFromParams(idParam, title, adType, content, category, tags);
            ad.setAdvertiserId(currentUser.getId());

            // 保存
            boolean success = adService.save(ad, currentUser.getId());

            if (success) {
                response.sendRedirect(request.getContextPath() + "/ads");
            } else {
                request.setAttribute("error", "保存失败，请检查权限");
                request.getRequestDispatcher("/WEB-INF/views/ad-form.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "系统错误：" + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/ad-form.jsp").forward(request, response);
        }
    }

    // 辅助方法：从参数创建Ad对象
    private Ad createAdFromParams(String idParam, String title, String adType, String content, String category, String tags) {
        Ad ad = new Ad();
        if (idParam != null && !idParam.isEmpty()) {
            ad.setId(Integer.parseInt(idParam));
        }
        ad.setTitle(title);
        ad.setAdType(adType);
        ad.setContent(content);
        ad.setCategory(category);
        ad.setTags(tags);
        ad.setStatus(1); // 默认上线
        return ad;
    }
}