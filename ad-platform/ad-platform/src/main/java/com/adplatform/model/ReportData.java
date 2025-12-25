package com.adplatform.model;

import java.io.Serializable;

public class ReportData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String date;        // 日期: 2025-12-17
    private Long impressions;   // 展示量: 1200
    private Long clicks;        // 点击量: 36
    private Double ctr;         // 点击率: 3.00

    public ReportData() {}

    public ReportData(String date, Long impressions, Long clicks, Double ctr) {
        this.date = date;
        this.impressions = impressions;
        this.clicks = clicks;
        this.ctr = ctr;
    }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public Long getImpressions() { return impressions; }
    public void setImpressions(Long impressions) { this.impressions = impressions; }

    public Long getClicks() { return clicks; }
    public void setClicks(Long clicks) { this.clicks = clicks; }

    public Double getCtr() { return ctr; }
    public void setCtr(Double ctr) { this.ctr = ctr; }
}