package com.adplatform.controller;

import com.adplatform.model.Ad;
import com.adplatform.service.AdService;
import com.adplatform.service.UserTrackingService;
import com.adplatform.util.JsonUtil;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/*")
public class AdApiServlet extends HttpServlet {
    private AdService adService = new AdService();
    private UserTrackingService trackingService = new UserTrackingService();

    private void setCorsHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Max-Age", "3600");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        setCorsHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setCorsHeaders(resp);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getPathInfo();
        try {
            switch (path) {
                case "/ad/get":
                    handleGetAd(req, resp);
                    break;
                case "/user/init":
                    handleInitUser(req, resp);
                    break;
                case "/ad/impression":
                    handleImpression(req, resp);
                    break;
                case "/ad/click":
                    handleClick(req, resp);
                    break;
                default:
                    resp.setStatus(404);
                    resp.getWriter().write("{\"success\":false,\"message\":\"API not found\"}");
            }
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"success\":false,\"message\":\"Server error\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setCorsHeaders(resp);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getPathInfo();
        try {
            if ("/user/behavior".equals(path)) {
                handleBehavior(req, resp);
            } else {
                resp.setStatus(404);
                resp.getWriter().write("{\"success\":false,\"message\":\"API not found\"}");
            }
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"success\":false,\"message\":\"Server error\"}");
        }
    }

    private void handleGetAd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uid = req.getParameter("uid");
        String site = req.getParameter("site");
        String pageCategory = req.getParameter("pageCategory");
        String adType = req.getParameter("adType");
        int limit = Integer.parseInt(req.getParameter("limit"));

        List<Ad> ads = adService.getPersonalizedAds(uid, site, pageCategory, adType, limit);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", ads);

        resp.getWriter().write(JsonUtil.toJson(result));
    }

    private void handleInitUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uid = req.getParameter("uid");
        if (uid == null || uid.isEmpty()) {
            uid = trackingService.generateUid();
        }
        trackingService.ensureUserExists(uid);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("uid", uid);

        resp.getWriter().write(JsonUtil.toJson(result));
    }

    private void handleImpression(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int adId = Integer.parseInt(req.getParameter("adId"));
        String uid = req.getParameter("uid");
        String site = req.getParameter("site");
        String pageCategory = req.getParameter("pageCategory");

        adService.logImpression(adId, uid, site, pageCategory);
        resp.getWriter().write("{\"success\":true}");
    }

    private void handleClick(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int adId = Integer.parseInt(req.getParameter("adId"));
        String uid = req.getParameter("uid");
        String site = req.getParameter("site");
        String targetUrl = req.getParameter("target");

        adService.logClick(adId, uid, site);

        if (targetUrl != null && !targetUrl.isEmpty()) {
            resp.sendRedirect(targetUrl);
        } else {
            resp.getWriter().write("{\"success\":true}");
        }
    }

    private void handleBehavior(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuilder json = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            json.append(line);
        }

        Map<String, Object> data = JsonUtil.fromJson(json.toString(), Map.class);
        String uid = (String) data.get("uid");
        String category = (String) data.get("category");
        String action = (String) data.get("action");

        trackingService.updateUserInterest(uid, category, action);
        resp.getWriter().write("{\"success\":true}");
    }
}