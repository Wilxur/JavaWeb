package com.example.video_platform.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlayLog {
    private Long id;
    private String uid;
    private Long videoId;
    private String eventType;

}
