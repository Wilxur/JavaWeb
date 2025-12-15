package com.adplatform.controller;

import com.adplatform.model.User;
import com.adplatform.service.AdService;
import com.adplatform.service.impl.AdServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/ad/delete")
public class AdDeleteServlet extends HttpServlet {
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
            boolean success = adService.delete(id, currentUser.getId());

            if (success) {
                response.sendRedirect(request.getContextPath() + "/ads?deleted=true");
            } else {
                response.sendRedirect(request.getContextPath() + "/ads?error=delete");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/ads?error=exception");
        }
    }
}