package com.crazypug.cli.ffmpeg;

import com.crazypug.cli.ffmpeg.enums.AudioEncodingFormat;
import com.crazypug.cli.ffmpeg.enums.VideoEncodingFormat;
import com.crazypug.utils.JsonUtil;

import java.io.File;

public class FFmpegTest {
    public static void main(String[] args) {


        String file_3 = "core/build/test-o.mp4";

        System.out.println(new File("").getAbsolutePath());

        File file = new File(file_3);
        if (file.exists()) {
            file.delete();
        }

        FFmpegCommandBuilder fFmpegCommandBuilder
                = new FFmpegCommandBuilder()
                .addInputVideoPath("core/build/video/1.mp4")
                .addInputVideoPath("core/build/video/2.mp4")
                .waterMark("core/build/video/1111.png")
                .audioEncodingFormat(AudioEncodingFormat.AAC)
                .videoEncodingFormat(VideoEncodingFormat.H264_VIDEO_TOOL_BOX)
                .outputVideoPath(file_3);
        System.out.println(JsonUtil.writePrettyString(fFmpegCommandBuilder));


//        FFprobeInfo mediaInfo = new FFprobeCommand().getMediaInfo(path);
//        System.out.println(JsonUtil.writePrettyString(mediaInfo));

//        System.out.println(fFmpegCommandBuilder.getCommand().stream().collect(Collectors.joining(" ")));
     FFmpegCommand fFmpegCommand = new FFmpegCommand();
//
     fFmpegCommand.executeAndGetProgress(1,fFmpegCommandBuilder)
       .forEach(System.out::println);


    }


}
