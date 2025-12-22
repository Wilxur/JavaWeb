package com.example.video_platform.service;

import com.example.video_platform.model.Video;

import java.util.List;

public interface VideoService {
    void upload(Video v);
    Video getById(long id);
    List<Video> list(Long categoryId);
}
