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
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

@WebServlet("/api/ad/get")
public class AdApiServlet extends HttpServlet {

    // ★★★ 定义可信的站点列表（根据你的实际IP和端口修改）★★★
    private static final Set<String> ALLOWED_ORIGINS = Set.of(
            "http://localhost:8080",
            "http://10.100.164.34:8080",
            "http://10.100.164.16:8080",
            "http://10.100.164.17:8080",
            "http://10.100.164.18:8080"
    );

    private AdRecommendationService recommendationService = new AdRecommendationServiceImpl();
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
                    new JsonPrimitive(src.toString()))
            .create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ★★★ 修复CORS判断 ★★★
        String origin = request.getHeader("Origin");
        if (origin != null && ALLOWED_ORIGINS.contains(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }

        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json;charset=UTF-8");

        try {
            String uid = getUidFromRequest(request);
            String pageCategory = request.getParameter("category");
            String site = request.getParameter("site"); // ★★★ 新增站点参数
            String callback = request.getParameter("callback");

            // 参数校验
            if (uid == null || pageCategory == null || site == null) {
                sendError(response, callback, "参数缺失: uid或category或site");
                return;
            }

            // 调用推荐算法（传入site）
            Ad ad = recommendationService.recommend(uid, pageCategory, site);

            Map<String, Object> result = new HashMap<>();
            if (ad != null) {
                result.put("success", true);
                result.put("ad", ad);
                result.put("adId", ad.getId());
            } else {
                result.put("success", false);
                result.put("message", "暂无可用广告");
            }

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

    // ★★★ 处理OPTIONS预检请求（浏览器会自动发）★★★
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
        response.setStatus(200); // 预检请求返回200即可
    }

    /**
     * 从Cookie或URL参数获取UID（保持原有逻辑）
     */
    private String getUidFromRequest(HttpServletRequest request) {
        String uid = request.getParameter("uid");
        if (uid != null && !uid.isEmpty()) {
            return uid;
        }

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