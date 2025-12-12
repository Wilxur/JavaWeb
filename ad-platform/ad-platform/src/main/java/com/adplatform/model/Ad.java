package com.adplatform.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Ad implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer advertiserId;
    private String title;
    private String adType; // "text", "image", "video"
    private String content;
    private String category;
    private String tags; // JSON字符串格式，如：["科技","数码"]
    private Integer status; // 1:有效 0:无效
    private LocalDateTime createdAt;

    // 无参构造函数
    public Ad() {}

    // 全参构造函数（可选）
    public Ad(Integer id, Integer advertiserId, String title, String adType,
              String content, String category, String tags, Integer status, LocalDateTime createdAt) {
        this.id = id;
        this.advertiserId = advertiserId;
        this.title = title;
        this.adType = adType;
        this.content = content;
        this.category = category;
        this.tags = tags;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getter和Setter方法
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getAdvertiserId() { return advertiserId; }
    public void setAdvertiserId(Integer advertiserId) { this.advertiserId = advertiserId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAdType() { return adType; }
    public void setAdType(String adType) { this.adType = adType; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Ad{id=" + id + ", title='" + title + "', category='" + category + "'}";
    }
}