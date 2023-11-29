package com.crazypug.cli.ffmpeg;

import cn.hutool.core.util.NumberUtil;
import com.crazypug.cli.ffmpeg.enums.AudioEncodingFormat;
import com.crazypug.cli.ffmpeg.enums.VideoEncodingFormat;
import com.crazypug.cli.ffmpeg.model.FFmpegProgress;
import com.crazypug.cli.ffmpeg.model.FFprobeInfo;
import com.crazypug.utils.JsonUtil;

import java.io.File;
import java.time.LocalTime;

public class FFmpeg2Test {
    public static void main(String[] args) {


        String file_3 = "core/build/test-o.mp4";

        System.out.println(new File("").getAbsolutePath());

        File file = new File(file_3);
        if (file.exists()) {
            file.delete();
        }

        FFmpegCommandBuilder fFmpegCommandBuilder
                = new FFmpegCommandBuilder()
                .addInputVideoPath("core/build/MXSPS-184_01.wmv")
                .addInputVideoPath("core/build/MXSPS-184_02.wmv")
                .audioEncodingFormat(AudioEncodingFormat.AAC)
                .videoEncodingFormat(VideoEncodingFormat.H264)
                .outputVideoPath(file_3);
        FFprobeCommand fFprobeCommand = new FFprobeCommand();
        Number number = fFmpegCommandBuilder.getCurrentInputVideoPath()
                .stream()
                .map(fFprobeCommand::getMediaInfo)
                .map(FFprobeInfo::getVideoInfo)
                .map(FFprobeInfo.MediaInfo::getDuration)
                .map(it -> NumberUtil.parseNumber(it, NumberUtil.newBigInteger("0")))
                .reduce(NumberUtil::add).orElse(NumberUtil.newBigInteger("0"));
        FFmpegCommand fFmpegCommand = new FFmpegCommand();
        fFmpegCommand.executeAndGetProgress(5, fFmpegCommandBuilder)
                .map(it ->

                        {

                            System.out.println(it);

                            return it;
                        }
                )
                .map(FFmpegProgress::getTime)
                .map(LocalTime::parse)
                .map(LocalTime::toSecondOfDay)
                .forEach(it -> {
                    ;
                    System.out.println(it);

                });


    }


}
