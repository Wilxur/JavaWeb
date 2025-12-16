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

@WebServlet("/ads")  // 访问路径：/ads
public class AdListServlet extends HttpServlet {
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

        // 查询当前用户的所有广告
        List<Ad> ads = adService.findMyAds(currentUser.getId());

        // 设置到request
        request.setAttribute("ads", ads);

        // 转发到列表页
        request.getRequestDispatcher("/WEB-INF/views/ads.jsp").forward(request, response);
    }
}