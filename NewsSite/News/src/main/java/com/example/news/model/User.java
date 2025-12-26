package com.example.news.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {

    private Integer id;
    private String username;
    private String password;
    private Timestamp createdAt;

}