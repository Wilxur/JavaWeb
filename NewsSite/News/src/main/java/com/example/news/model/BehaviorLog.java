package com.example.news.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BehaviorLog {

    private Integer id;           // 主键
    private String uid;           // 匿名用户 UID
    private Integer newsId;       // 新闻 ID
    private Integer categoryId;   // 分类 ID
    private String action;        // 行为类型
    private Integer duration;     // 停留时长（秒）
    private String userAgent;     // 浏览器信息
    private String ipAddress;     // 用户 IP
    private LocalDateTime createdAt; // 创建时间
}