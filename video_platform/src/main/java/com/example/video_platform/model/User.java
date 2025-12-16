package com.example.video_platform.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    private Long id;
    private String username;
    private String password; // 存加密后的
    private String createTime;

}
