package com.example.news.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 新闻实体（数据映射）
 */
@Data
public class News {

    private Integer id;
    private String title;
    private String content;

    // 分类：用于广告系统 meta 注入
    private String category;

    private Timestamp publishTime;
    private Integer viewCount;
}