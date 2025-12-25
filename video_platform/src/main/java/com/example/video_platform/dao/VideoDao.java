package com.example.video_platform.dao;

import com.example.video_platform.model.Video;

import java.util.List;

public interface VideoDao {
    int insert(Video v);
    Video findById(long id);
    List<Video> findAll();
    List<Video> findByCategory(long categoryId);
}
