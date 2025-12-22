package com.example.video_platform.dao.impl;

import com.example.video_platform.dao.VideoDao;
import com.example.video_platform.model.Video;
import com.example.video_platform.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VideoDaoImpl implements VideoDao {

    @Override
    public int insert(Video v) {
        String sql = "INSERT INTO video(title, file_path, category_id, uploader_id) VALUES (?, ?, ?, ?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, v.getTitle());
            ps.setString(2, v.getFilePath());
            ps.setLong(3, v.getCategoryId());
            if (v.getUploaderId() == null) ps.setObject(4, null);
            else ps.setLong(4, v.getUploaderId());
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Video findById(long id) {
        String sql = """
                SELECT v.id, v.title, v.file_path, v.category_id, c.name AS category_name, v.uploader_id, v.upload_time
                FROM video v
                JOIN category c ON v.category_id = c.id
                WHERE v.id = ?
                """;
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return map(rs);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Video> findAll() {
        String sql = """
                SELECT v.id, v.title, v.file_path, v.category_id, c.name AS category_name, v.uploader_id, v.upload_time
                FROM video v
                JOIN category c ON v.category_id = c.id
                ORDER BY v.id DESC
                """;
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Video> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Video> findByCategory(long categoryId) {
        String sql = """
                SELECT v.id, v.title, v.file_path, v.category_id, c.name AS category_name, v.uploader_id, v.upload_time
                FROM video v
                JOIN category c ON v.category_id = c.id
                WHERE v.category_id = ?
                ORDER BY v.id DESC
                """;
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Video> list = new ArrayList<>();
                while (rs.next()) list.add(map(rs));
                return list;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Video map(ResultSet rs) throws Exception {
        Video v = new Video();
        v.setId(rs.getLong("id"));
        v.setTitle(rs.getString("title"));
        v.setFilePath(rs.getString("file_path"));
        v.setCategoryId(rs.getLong("category_id"));
        v.setCategoryName(rs.getString("category_name"));
        v.setUploaderId((Long) rs.getObject("uploader_id"));
        v.setUploadTime(String.valueOf(rs.getTimestamp("upload_time")));
        return v;
    }
}
