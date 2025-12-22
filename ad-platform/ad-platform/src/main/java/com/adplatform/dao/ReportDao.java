package com.adplatform.dao;

import com.adplatform.model.ReportData;
import com.adplatform.model.ReportFilter;
import java.util.List;
import java.util.Map;

public interface ReportDao {
    List<ReportData> getAdStats(int advertiserId, ReportFilter filter);
    List<ReportData> getTopAdsByCTR(int advertiserId, int limit, ReportFilter filter);
    Map<String, String> getDateRange(int advertiserId);
}