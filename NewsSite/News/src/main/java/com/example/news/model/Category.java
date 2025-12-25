package com.example.news.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 新闻分类实体（Day 3）
 * 只负责数据库字段映射，不包含业务逻辑
 */
@Data
public class Category {

    /**
     * 分类ID
     */
    private Integer id;

    /**
     * 分类名称（如：科技、财经）
     */
    private String name;

    /**
     * 创建时间
     */
    private Timestamp createdAt;
}