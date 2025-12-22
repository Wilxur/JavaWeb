package com.adplatform.model;

import java.io.Serializable;

public class ReportFilter implements Serializable {
    private static final long serialVersionUID = 1L;

    private String startDate;
    private String endDate;
    private Integer adId;  // 预留字段，暂时不用

    public ReportFilter() {}

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public Integer getAdId() { return adId; }
    public void setAdId(Integer adId) { this.adId = adId; }
}