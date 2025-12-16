package com.example.news.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * 新闻实体类
 * 对应数据库表：news
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {

    private Integer newsId;
    private String title;
    private String content;
    private String author;
    private Integer categoryId;
    private Integer viewCount;
    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;

    /** 扩展字段：分类名称（连表查询用） */
    private String categoryName;

    /** 新增新闻时使用 */
    public News(String title, String content, String author, Integer categoryId) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.categoryId = categoryId;
        this.viewCount = 0;
    }
}