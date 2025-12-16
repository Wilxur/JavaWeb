package com.example.video_platform.controller;

import com.example.video_platform.service.LogService;
import com.example.video_platform.service.impl.LogServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/log")
public class LogServlet extends HttpServlet {

    private final LogService logService = new LogServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String uid = (String) req.getAttribute("uid");
        Long videoId = Long.parseLong(req.getParameter("videoId"));
        String eventType = req.getParameter("eventType");

        logService.log(uid, videoId, eventType);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
