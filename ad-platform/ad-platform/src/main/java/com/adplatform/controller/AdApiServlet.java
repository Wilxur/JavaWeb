package com.adplatform.controller;

import com.adplatform.model.Ad;
import com.adplatform.service.AdRecommendationService;
import com.adplatform.service.impl.AdRecommendationServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

@WebServlet("/api/ad/get")
public class AdApiServlet extends HttpServlet {
    private AdRecommendationService recommendationService = new AdRecommendationServiceImpl();

    // ✅ 修复：注册LocalDateTime序列化器
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
                @Override
                public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
                    return new JsonPrimitive(src.toString()); // 转为字符串
                }
            })
            .create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. 设置CORS头（允许跨域）
        String origin = request.getHeader("Origin");
        if (origin != null && (origin.contains("localhost") || origin.contains("com"))) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json;charset=UTF-8");

        try {
            // 2. 获取参数（优先Cookie，其次URL参数）
            String uid = getUidFromRequest(request);
            String pageCategory = request.getParameter("category");
            String callback = request.getParameter("callback"); // JSONP支持

            // 3. 参数校验
            if (uid == null || pageCategory == null) {
                sendError(response, callback, "参数缺失: uid或category");
                return;
            }

            // 4. 调用推荐算法
            Ad ad = recommendationService.recommend(uid, pageCategory);

            // 5. 构建响应
            Map<String, Object> result = new HashMap<>();
            if (ad != null) {
                result.put("success", true);
                result.put("ad", ad);
                result.put("adId", ad.getId());
            } else {
                result.put("success", false);
                result.put("message", "暂无可用广告");
            }

            // 6. 输出JSON/JSONP
            String json = gson.toJson(result);
            if (callback != null) {
                response.getWriter().write(callback + "(" + json + ");");
            } else {
                response.getWriter().write(json);
            }

        } catch (Exception e) {
            e.printStackTrace();
            sendError(response, request.getParameter("callback"), "系统错误: " + e.getMessage());
        }
    }

    /**
     * 从Cookie或URL参数获取UID
     */
    private String getUidFromRequest(HttpServletRequest request) {
        // 优先从URL参数获取（兼容非Cookie场景）
        String uid = request.getParameter("uid");
        if (uid != null && !uid.isEmpty()) {
            return uid;
        }

        // 其次从Cookie读取
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("ad_platform_uid".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void sendError(HttpServletResponse response, String callback, String message) throws IOException {
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("message", message);

        String json = gson.toJson(error);
        if (callback != null) {
            response.getWriter().write(callback + "(" + json + ");");
        } else {
            response.getWriter().write(json);
        }
    }
}