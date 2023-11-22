package com.crazypug.cli.ffmpeg.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class FFmpegProgress {

    private String size;
    private String time;
    private String bitrate;
    private float speed;

}
