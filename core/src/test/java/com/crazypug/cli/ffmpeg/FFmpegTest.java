package com.crazypug.cli.ffmpeg;

import com.crazypug.cli.ffmpeg.enums.EncodingFormat;

import java.io.File;
import java.util.stream.Collectors;

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
                .addInputVideoPath("core/build/video/3.mp4")
                .encodingFormat(EncodingFormat.COPY)
                .outputVideoPath(file_3);

//        FFprobeInfo mediaInfo = new FFprobeCommand().getMediaInfo(path);
//        System.out.println(JsonUtil.writePrettyString(mediaInfo));

        System.out.println(fFmpegCommandBuilder.getCommand().stream().collect(Collectors.joining(" ")));
        FFmpegCommand fFmpegCommand = new FFmpegCommand();

        fFmpegCommand.executeCommand(fFmpegCommandBuilder.getCommand())
                .forEach(System.out::println);


    }


}
