package com.example.video_platform.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 广告代理接口
 * 后续这里将调用广告平台 API
 */
@WebServlet("/ad")
public class AdProxyServlet extends HttpServlet {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json;charset=UTF-8");

        // 模拟广告数据（后续由广告平台返回）
        Map<String, Object> ad = Map.of(
                "adId", "AD_001",
                "adVideoUrl", "media?path=ad_test.mp4",
                "duration", 2
        );

        mapper.writeValue(resp.getOutputStream(), ad);
    }
}
