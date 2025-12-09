package com.example.news.service;

import com.example.news.dao.BehaviorDao;
import com.example.news.model.BehaviorLog;

public class BehaviorService {

    private final BehaviorDao behaviorDao = new BehaviorDao();

    public void report(BehaviorLog log) {
        behaviorDao.insert(log);
    }
}