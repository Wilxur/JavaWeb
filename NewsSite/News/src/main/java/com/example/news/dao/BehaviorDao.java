package com.example.news.dao;

import com.example.news.model.BehaviorLog;

public interface BehaviorDao {

    /**
     * 插入一条用户行为记录（只定义方法）
     */
    void insert(BehaviorLog log);
}