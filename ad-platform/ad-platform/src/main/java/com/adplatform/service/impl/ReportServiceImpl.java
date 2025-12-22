package com.adplatform.service.impl;

import com.adplatform.dao.ReportDao;
import com.adplatform.dao.impl.ReportDaoImpl;
import com.adplatform.model.ReportData;
import com.adplatform.model.ReportFilter;
import com.adplatform.model.User;
import com.adplatform.service.ReportService;
import java.util.*;
import java.time.LocalDate;

public class ReportServiceImpl implements ReportService {
    private ReportDao reportDao = new ReportDaoImpl();

    @Override
    public Map<String, Object> generateReport(User currentUser, ReportFilter filter) {
        Map<String, Object> report = new HashMap<>();
        int advertiserId = currentUser.getId();

        // 如果日期为空，默认最近7天
        if (filter.getStartDate() == null || filter.getEndDate() == null) {
            Map<String, String> dateRange = reportDao.getDateRange(advertiserId);
            String minDate = dateRange.get("minDate");
            String maxDate = dateRange.get("maxDate");

            // 如果数据库中没有数据，设置默认的最近7天
            if (minDate == null || maxDate == null) {
                LocalDate today = LocalDate.now();
                filter.setEndDate(today.toString());
                filter.setStartDate(today.minusDays(7).toString());
            } else {
                filter.setStartDate(minDate);
                filter.setEndDate(maxDate);
            }
        }

        // 获取统计数据
        List<ReportData> stats = reportDao.getAdStats(advertiserId, filter);

        // 获取TOP广告
        List<ReportData> topAds = reportDao.getTopAdsByCTR(advertiserId, 10, filter);

        // 计算汇总指标
        long totalImpressions = stats.stream().mapToLong(ReportData::getImpressions).sum();
        long totalClicks = stats.stream().mapToLong(ReportData::getClicks).sum();
        double avgCtr = totalImpressions > 0 ? (totalClicks * 100.0 / totalImpressions) : 0.0;

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalImpressions", totalImpressions);
        summary.put("totalClicks", totalClicks);
        summary.put("avgCtr", String.format("%.2f%%", avgCtr));

        report.put("stats", stats);
        report.put("summary", summary);
        report.put("filter", filter);
        report.put("topAds", topAds);

        return report;
    }
}