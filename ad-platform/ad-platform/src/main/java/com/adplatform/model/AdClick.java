package com.adplatform.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AdClick implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer adId;
    private String uid;
    private String site; // 网站标识：shopping/video/news
    private LocalDateTime clickedAt;

    public AdClick() {}

    public AdClick(Long id, Integer adId, String uid, String site, LocalDateTime clickedAt) {
        this.id = id;
        this.adId = adId;
        this.uid = uid;
        this.site = site;
        this.clickedAt = clickedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getAdId() { return adId; }
    public void setAdId(Integer adId) { this.adId = adId; }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getSite() { return site; }
    public void setSite(String site) { this.site = site; }

    public LocalDateTime getClickedAt() { return clickedAt; }
    public void setClickedAt(LocalDateTime clickedAt) { this.clickedAt = clickedAt; }

    @Override
    public String toString() {
        return "AdClick{id=" + id + ", adId=" + adId + ", uid='" + uid + "'}";
    }
}