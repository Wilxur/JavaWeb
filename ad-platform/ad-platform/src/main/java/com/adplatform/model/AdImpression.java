package com.adplatform.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AdImpression implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer adId;
    private String uid;
    private String site; // 网站标识：shopping/video/news
    private String pageCategory;
    private LocalDateTime shownAt;

    public AdImpression() {}

    public AdImpression(Long id, Integer adId, String uid, String site,
                        String pageCategory, LocalDateTime shownAt) {
        this.id = id;
        this.adId = adId;
        this.uid = uid;
        this.site = site;
        this.pageCategory = pageCategory;
        this.shownAt = shownAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getAdId() { return adId; }
    public void setAdId(Integer adId) { this.adId = adId; }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getSite() { return site; }
    public void setSite(String site) { this.site = site; }

    public String getPageCategory() { return pageCategory; }
    public void setPageCategory(String pageCategory) { this.pageCategory = pageCategory; }

    public LocalDateTime getShownAt() { return shownAt; }
    public void setShownAt(LocalDateTime shownAt) { this.shownAt = shownAt; }

    @Override
    public String toString() {
        return "AdImpression{id=" + id + ", adId=" + adId + ", uid='" + uid + "'}";
    }
}