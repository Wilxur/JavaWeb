package com.example.video_platform.dao.impl;

import com.example.video_platform.dao.PlayLogDao;
import com.example.video_platform.model.PlayLog;
import com.example.video_platform.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PlayLogDaoImpl implements PlayLogDao {

    @Override
    public void insert(PlayLog log) {
        String sql = "INSERT INTO play_log(uid, video_id, event_type) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, log.getUid());
            ps.setLong(2, log.getVideoId());
            ps.setString(3, log.getEventType());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

