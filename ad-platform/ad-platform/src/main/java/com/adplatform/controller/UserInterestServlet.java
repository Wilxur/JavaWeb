package com.adplatform.controller;

import com.adplatform.service.UserInterestService;
import com.adplatform.service.impl.UserInterestServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/user/interest")
public class UserInterestServlet extends HttpServlet {
    private UserInterestService interestService = new UserInterestServiceImpl();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 设置CORS
        String origin = request.getHeader("Origin");
        if (origin != null) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> result = new HashMap<>();

        try {
            // 读取JSON请求体
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                sb.append(line);
            }
            Map<String, String> data = gson.fromJson(sb.toString(), Map.class);

            String uid = data.get("uid");
            String category = data.get("category");

            if (uid == null || category == null) {
                result.put("success", false);
                result.put("message", "参数缺失");
            } else {
                // 更新兴趣（页面访问权重+10）
                interestService.updateInterest(uid, category, 10);
                result.put("success", true);
                result.put("message", "兴趣更新成功");
            }

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "错误: " + e.getMessage());
        }

        response.getWriter().write(gson.toJson(result));
    }

    // 处理OPTIONS预检请求
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String origin = req.getHeader("Origin");
        if (origin != null) {
            resp.setHeader("Access-Control-Allow-Origin", origin);
        }
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(200);
    }
}