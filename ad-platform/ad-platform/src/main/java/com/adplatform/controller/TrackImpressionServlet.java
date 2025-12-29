package com.adplatform.controller;

import com.adplatform.service.UserInterestService;
import com.adplatform.service.impl.UserInterestServiceImpl;
import com.adplatform.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

    // ★★★ 添加CORS白名单（与AdApiServlet一致）★★★
    private static final Set<String> ALLOWED_ORIGINS = new HashSet<>(Arrays.asList(
            "http://localhost:8080",
            "http://10.100.164.34:8080",
            "http://10.100.164.16:8080",
            "http://10.100.164.17:8080",
            "http://10.100.164.18:8080"
    ));

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ★★★ 添加CORS头（关键修复）★★★
        String origin = request.getHeader("Origin");
        if (origin != null && ALLOWED_ORIGINS.contains(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        // 1. 设置响应为GIF图片
        response.setContentType("image/gif");
        response.setContentLength(GIF_DATA.length);

        try {
            // 2. 获取参数
            String uid = request.getParameter("uid");
            String adIdStr = request.getParameter("adId");
            String site = request.getParameter("site");
            String category = request.getParameter("category");

            // 3. 参数完整性检查
            if (adIdStr == null || site == null) {
                return; // 只返回GIF不记录日志
            }

            int adId = Integer.parseInt(adIdStr);

            // 4. 记录展示日志（写入ad_impressions表）
            String sql = "INSERT INTO ad_impressions(ad_id, uid, site, page_category) VALUES(?, ?, ?, ?)";
            DBUtil.executeUpdate(sql, adId, uid, site, category);

            // 5. 更新用户兴趣（展示行为权重+1）
            if (uid != null && category != null && !category.isEmpty()) {
                interestService.updateInterest(uid, category, 1);
                // 衰减旧兴趣
                interestService.decayOldInterests(uid, category);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 6. 返回1x1 GIF
            response.getOutputStream().write(GIF_DATA);
        }
    }

    // ★★★ 处理OPTIONS预检请求（关键修复）★★★
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String origin = request.getHeader("Origin");
        if (origin != null && ALLOWED_ORIGINS.contains(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, *");
        response.setStatus(200); // 预检请求返回200
    }
}