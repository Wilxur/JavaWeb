package com.adplatform.controller;

import com.adplatform.model.Ad;
import com.adplatform.model.User;
import com.adplatform.service.AdService;
import com.adplatform.service.impl.AdServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/ad/toggle")
public class AdToggleServlet extends HttpServlet {
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

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int newStatus = Integer.parseInt(request.getParameter("status")); // 1或0

            // 注意：这里需要增加权限验证！确保广告属于当前用户
            Ad ad = adService.findById(id, currentUser.getId());
            if (ad == null) {
                response.sendRedirect(request.getContextPath() + "/ads?error=nopermission");
                return;
            }

            boolean success = adService.toggleStatus(id, newStatus);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/ads");
            } else {
                response.sendRedirect(request.getContextPath() + "/ads?error=toggle");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/ads?error=exception");
        }
    }
}