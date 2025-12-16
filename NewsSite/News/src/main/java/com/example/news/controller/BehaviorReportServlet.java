package com.example.news.controller;

import com.example.news.model.BehaviorLog;
import com.example.news.service.BehaviorService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * 用户行为上报接口
 */
@WebServlet("/behavior/report")
public class BehaviorReportServlet extends HttpServlet {

    private final BehaviorService behaviorService = new BehaviorService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        // 从请求参数获取行为数据
        String uid = req.getParameter("uid");
        String newsId = req.getParameter("newsId");
        String categoryId = req.getParameter("categoryId");
        String action = req.getParameter("action");
        String duration = req.getParameter("duration");

        BehaviorLog log = new BehaviorLog();
        log.setUid(uid);
        log.setNewsId(Integer.parseInt(newsId));
        log.setCategoryId(Integer.parseInt(categoryId));
        log.setAction(action);
        log.setDuration(Integer.parseInt(duration));
        log.setUserAgent(req.getHeader("User-Agent"));
        log.setIpAddress(req.getRemoteAddr());

        behaviorService.report(log);

        resp.getWriter().write("OK");
    }
}