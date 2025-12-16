package com.example.video_platform.service;

public interface LogService {

    /**
     * 记录播放 / 广告日志
     *
     * @param uid 匿名用户ID
     * @param videoId 视频ID
     * @param eventType 事件类型（VIDEO_START / AD_START / AD_END）
     */
    void log(String uid, Long videoId, String eventType);
}
