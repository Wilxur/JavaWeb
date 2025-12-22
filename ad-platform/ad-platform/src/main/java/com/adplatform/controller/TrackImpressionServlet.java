package com.adplatform.controller;

import com.adplatform.service.UserInterestService;
import com.adplatform.service.impl.UserInterestServiceImpl;
import com.adplatform.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/api/track/impression")
public class TrackImpressionServlet extends HttpServlet {
    private UserInterestService interestService = new UserInterestServiceImpl();

    // 1x1透明GIF (Base64编码)
    private static final byte[] GIF_DATA = {
            0x47, 0x49, 0x46, 0x38, 0x39, 0x61, 0x01, 0x00, 0x01, 0x00,
            (byte) 0x80, 0x00, 0x00, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            0x00, 0x00, 0x00, 0x21, (byte) 0xf9, 0x04, 0x01, 0x00,
            0x00, 0x00, 0x00, 0x2c, 0x00, 0x00, 0x00, 0x00, 0x01,
            0x00, 0x01, 0x00, 0x00, 0x02, 0x02, 0x44, 0x01, 0x00, 0x3b
    };

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. 设置响应为GIF图片
        response.setContentType("image/gif");
        response.setContentLength(GIF_DATA.length);

        try {
            // 2. 获取参数
            String uid = request.getParameter("uid");
            int adId = Integer.parseInt(request.getParameter("adId"));
            String site = request.getParameter("site");
            String category = request.getParameter("category");

            // 3. 记录展示日志（写入ad_impressions表）
            String sql = "INSERT INTO ad_impressions(ad_id, uid, site, page_category) VALUES(?, ?, ?, ?)";
            DBUtil.executeUpdate(sql, adId, uid, site, category);

            // 4. 更新用户兴趣（展示行为权重+1）
            if (uid != null && category != null) {
                interestService.updateInterest(uid, category, 1);
                // 衰减旧兴趣
                interestService.decayOldInterests(uid, category);
            }

        } catch (Exception e) {
            // 日志记录失败也不影响返回GIF
            e.printStackTrace();
        } finally {
            // 5. 返回1x1 GIF
            response.getOutputStream().write(GIF_DATA);
        }
    }
}