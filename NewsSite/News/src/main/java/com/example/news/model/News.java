package com.example.news.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 新闻实体（数据映射）
 * - 写入数据库：使用 categoryId
 * - 页面展示 / 广告：使用 category
 */
@Data
public class News {

    private Integer id;

    // 发布时填写
    private String title;
    private String content;
    private Integer categoryId;

    // 查询时 JOIN 得到
    private String category;

    // 数据库自动生成
    private Timestamp publishTime;
    private Integer viewCount;
}