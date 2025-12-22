package com.example.video_platform.service.impl;

import com.example.video_platform.dao.VideoDao;
import com.example.video_platform.dao.impl.VideoDaoImpl;
import com.example.video_platform.model.Video;
import com.example.video_platform.service.VideoService;

import java.util.List;

public class VideoServiceImpl implements VideoService {

    private final VideoDao videoDao = new VideoDaoImpl();

    @Override
    public void upload(Video v) {
        videoDao.insert(v);
    }

    @Override
    public Video getById(long id) {
        return videoDao.findById(id);
    }

    @Override
    public List<Video> list(Long categoryId) {
        if (categoryId == null) return videoDao.findAll();
        return videoDao.findByCategory(categoryId);
    }
}
