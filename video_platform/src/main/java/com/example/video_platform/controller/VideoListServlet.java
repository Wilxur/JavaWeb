package com.example.video_platform.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 视频列表：转发到 videos.jsp
 * 先用占位数据跑通页面，后续替换为 service 查询数据库
 */
@WebServlet("/videos")
public class VideoListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 占位数据（后续换成 VideoService.findAll/findByCategory）
        List<Map<String, Object>> videos = new ArrayList<>();
        videos.add(Map.of("id", 1L, "title", "示例视频-1", "category", "默认分类"));
        videos.add(Map.of("id", 2L, "title", "示例视频-2", "category", "默认分类"));

        req.setAttribute("videos", videos);
        req.getRequestDispatcher("/jsp/videos.jsp").forward(req, resp);
    }
}
