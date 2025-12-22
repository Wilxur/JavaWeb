package com.adplatform.controller;

import com.adplatform.service.UserInterestService;
import com.adplatform.service.impl.UserInterestServiceImpl;
import com.adplatform.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/api/track/click")
public class TrackClickServlet extends HttpServlet {
    private UserInterestService interestService = new UserInterestServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1. 获取参数
            String uid = request.getParameter("uid");
            int adId = Integer.parseInt(request.getParameter("adId"));
            String site = request.getParameter("site");
            String category = request.getParameter("category");
            String redirectUrl = request.getParameter("redirect"); // 落地页

            // 2. 记录点击日志
            String sql = "INSERT INTO ad_clicks(ad_id, uid, site) VALUES(?, ?, ?)";
            DBUtil.executeUpdate(sql, adId, uid, site);

            // 3. 更新用户兴趣（点击行为权重+5）
            if (uid != null && category != null) {
                interestService.updateInterest(uid, category, 5);
            }

            // 4. 302重定向到广告落地页
            if (redirectUrl != null && !redirectUrl.isEmpty()) {
                response.sendRedirect(redirectUrl);
            } else {
                response.sendRedirect(request.getContextPath() + "/");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "点击追踪失败");
        }
    }
}