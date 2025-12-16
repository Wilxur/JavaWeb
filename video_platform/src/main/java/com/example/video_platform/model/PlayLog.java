package com.example.video_platform.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PlayLog {
    private Long id;
    private String uid;
    private Long videoId;
    private String eventType;
    private LocalDateTime eventTime;
}
