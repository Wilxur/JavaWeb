package com.adplatform.controller;

import com.adplatform.model.Ad;
import com.adplatform.service.AdRecommendationService;
import com.adplatform.service.impl.AdRecommendationServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@WebServlet("/api/ad/get")
public class AdApiServlet extends HttpServlet {
    // CORS白名单（JDK 8兼容写法）
    private static final Set<String> ALLOWED_ORIGINS = new HashSet<>(Arrays.asList(
            "http://localhost:8080",
            "http://10.100.164.34:8080",
            "http://10.100.164.16:8080",
            "http://10.100.164.17:8080",
            "http://10.100.164.18:8080"
    ));

    private AdRecommendationService recommendationService = new AdRecommendationServiceImpl();
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
                    new JsonPrimitive(src.toString()))
            .create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("═══════════════════════════════════════");
        System.out.println("【AdApiServlet】收到请求，时间: " + new Date());
        System.out.println("【AdApiServlet】原始参数 category=" + request.getParameter("category"));
        System.out.println("【AdApiServlet】原始参数 site=" + request.getParameter("site"));
        System.out.println("【AdApiServlet】原始参数 uid=" + request.getParameter("uid"));

        String origin = request.getHeader("Origin");
        if (origin != null && ALLOWED_ORIGINS.contains(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json;charset=UTF-8");

        try {
            String mappedCategory = mapCategory(request.getParameter("category"));
            System.out.println("【AdApiServlet】映射结果: " + mappedCategory);

            String uid = getUidFromRequest(request);
            String pageCategory = request.getParameter("category"); // 接收中文如"社会"
            String site = request.getParameter("site");
            String callback = request.getParameter("callback");

            // ★★★ 关键修复：分类映射（在API层处理）★★★
            mappedCategory = mapCategory(pageCategory);
            System.out.println("【API分类映射】" + pageCategory + " → " + mappedCategory);

            if (uid == null || mappedCategory == null || site == null) {
                sendError(response, callback, "参数缺失: uid/category/site");
                return;
            }

            // 使用映射后的英文分类查询
            Ad ad = recommendationService.recommend(uid, mappedCategory, site);

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
            response.getWriter().write(callback != null ? callback + "(" + json + ");" : json);

        } catch (Exception e) {
            e.printStackTrace();
            sendError(response, request.getParameter("callback"), "系统错误: " + e.getMessage());
        }
    }

    /**
     * ★★★ 中文分类 → 英文广告分类映射（与sdk.js完全一致）★★★
     */
    private String mapCategory(String pageCategory) {
        System.out.println("   ├─【mapCategory】接收到: " + pageCategory);

        if (pageCategory == null) {
            System.out.println("   └─【mapCategory】返回默认值: electronics");
            return "electronics";
        }

        // 已经是英文分类？直接返回
        Set<String> englishCategories = new HashSet<>(Arrays.asList(
                "electronics", "clothing", "food", "beauty", "home", "sports"
        ));
        if (englishCategories.contains(pageCategory)) {
            return pageCategory;
        }

        // 中文映射（JDK 8兼容）
        Map<String, String> categoryMap = new HashMap<>();
        categoryMap.put("科技", "electronics");
        categoryMap.put("财经", "home");
        categoryMap.put("体育", "sports");
        categoryMap.put("娱乐", "beauty");
        categoryMap.put("社会", "home");
        categoryMap.put("教育", "electronics");
        categoryMap.put("电子产品", "electronics");
        categoryMap.put("服装鞋帽", "clothing");
        categoryMap.put("食品饮料", "food");
        categoryMap.put("美妆护肤", "beauty");
        categoryMap.put("家居用品", "home");
        categoryMap.put("运动户外", "sports");

        String mapped = categoryMap.get(pageCategory);
        if (mapped == null) {
            System.out.println("【警告】未知分类: " + pageCategory + "，使用默认electronics");
            return "electronics";
        }
        System.out.println("   └─【mapCategory】映射结果: " + mapped);
        return mapped;
    }

    private String getUidFromRequest(HttpServletRequest request) {
        String uid = request.getParameter("uid");
        if (uid != null && !uid.isEmpty()) return uid;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("ad_platform_uid".equals(cookie.getName())) return cookie.getValue();
            }
        }
        return null;
    }

    private void sendError(HttpServletResponse response, String callback, String message) throws IOException {
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("message", message);
        String json = gson.toJson(error);
        response.getWriter().write(callback != null ? callback + "(" + json + ");" : json);
    }
}