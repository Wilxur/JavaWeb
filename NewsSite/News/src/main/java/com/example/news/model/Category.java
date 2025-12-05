package com.example.news.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 新闻分类实体类
 * 对应数据库表：category
 */
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer categoryId;      // 分类ID
    private String categoryName;     // 分类名称
    private String description;      // 分类描述
    private Timestamp createdAt;     // 创建时间

    // 无参构造方法（必须有）
    public Category() {}

    // 全参构造方法（方便创建对象）
    public Category(Integer categoryId, String categoryName, String description, Timestamp createdAt) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.createdAt = createdAt;
    }

    // Getter 和 Setter 方法
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // toString 方法（方便调试）
    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}