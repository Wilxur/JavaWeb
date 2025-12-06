package com.example.news.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 新闻实体类
 */
public class News implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer newsId;
    private String title;
    private String content;
    private String author;
    private Integer categoryId;
    private Integer viewCount;
    private Timestamp publishedAt;
    private Timestamp updatedAt;

    // 关联属性（不对应数据库字段，用于显示）
    private String categoryName;

    public News() {}

    public News(String title, String content, String author, Integer categoryId) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.categoryId = categoryId;
        this.viewCount = 0;
    }

    // Getter 和 Setter
    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Timestamp getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Timestamp publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "News{" +
                "newsId=" + newsId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", viewCount=" + viewCount +
                '}';
    }
}