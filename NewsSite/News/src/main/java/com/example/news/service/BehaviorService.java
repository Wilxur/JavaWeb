package com.example.news.service;

import com.example.news.dao.BehaviorDao;
import com.example.news.dao.impl.BehaviorDaoImpl;
import com.example.news.model.BehaviorLog;

public class BehaviorService {

    private final BehaviorDao behaviorDao = new BehaviorDaoImpl();

    public void report(BehaviorLog log) {
        behaviorDao.insert(log);
    }
}