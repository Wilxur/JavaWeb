package com.adplatform.service;

import com.adplatform.model.ReportFilter;
import com.adplatform.model.User;
import java.util.Map;

public interface ReportService {
    Map<String, Object> generateReport(User currentUser, ReportFilter filter);
}