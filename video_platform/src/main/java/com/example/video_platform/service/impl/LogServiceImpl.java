package com.example.video_platform.service.impl;

import com.example.video_platform.dao.PlayLogDao;
import com.example.video_platform.dao.impl.PlayLogDaoImpl;
import com.example.video_platform.model.PlayLog;
import com.example.video_platform.service.LogService;

public class LogServiceImpl implements LogService {

    private final PlayLogDao playLogDao = new PlayLogDaoImpl();

    @Override
    public void log(String uid, Long videoId, String eventType) {
        PlayLog log = new PlayLog();
        log.setUid(uid);
        log.setVideoId(videoId);
        log.setEventType(eventType);
        playLogDao.insert(log);
    }
}
