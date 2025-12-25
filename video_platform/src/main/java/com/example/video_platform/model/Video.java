package com.example.video_platform.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Video {
    private Long id;
    private String title;
    private String filePath;     // 存文件名（不是绝对路径），如 uuid_xxx.mp4
    private Long categoryId;
    private String categoryName; // 列表展示用
    private Long uploaderId;
    private String uploadTime;

}
