package com.example.news.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * 新闻分类实体类
 * 对应数据库表：category
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    private Integer categoryId;
    private String categoryName;
    private String description;
    private LocalDateTime createdAt;

    /** 新增分类时使用 */
    public Category(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }
}