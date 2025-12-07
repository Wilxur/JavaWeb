package com.adplatform.model;

import java.util.List;

public class Ad {
    private int id;
    private int advertiserId;
    private String title;
    private String adType;
    private String content;
    private String category;
    private List<String> tags;
    private int status;
    private String targetUrl;
    private String impressionUrl;
    private String clickUrl;
    private String createdAt;

    public Ad() {
    }

    public Ad(int id, int advertiserId, String title, String adType, String content, String category, List<String> tags, int status, String targetUrl, String impressionUrl, String clickUrl, String createdAt) {
        this.id = id;
        this.advertiserId = advertiserId;
        this.title = title;
        this.adType = adType;
        this.content = content;
        this.category = category;
        this.tags = tags;
        this.status = status;
        this.targetUrl = targetUrl;
        this.impressionUrl = impressionUrl;
        this.clickUrl = clickUrl;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(int advertiserId) {
        this.advertiserId = advertiserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdType() {
        return adType;
    }

    public void setAdType(String adType) {
        this.adType = adType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getImpressionUrl() {
        return impressionUrl;
    }

    public void setImpressionUrl(String impressionUrl) {
        this.impressionUrl = impressionUrl;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
