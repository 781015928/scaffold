package com.crazypug.cli.ffmpeg;

import cn.hutool.core.util.NumberUtil;
import com.crazypug.cli.ffmpeg.enums.AudioEncodingFormat;
import com.crazypug.cli.ffmpeg.enums.VideoEncodingFormat;
import com.crazypug.cli.ffmpeg.model.FFprobeInfo;
import com.crazypug.utils.JsonUtil;

import java.io.File;

public class FFProfeTest {
    public static void main(String[] args) {


        String file_3 = "core/build/test-o.mp4";

        System.out.println(new File("").getAbsolutePath());

        File file = new File(file_3);
        if (file.exists()) {
            file.delete();
        }

        FFprobeCommand fFprobeCommand = new FFprobeCommand();

        FFprobeInfo mediaInfo = fFprobeCommand.getMediaInfo("core/build/video/1.mp4");
        FFprobeInfo mediaInfo1 = fFprobeCommand.getMediaInfo("core/build/video/2.mp4");


        String duration = mediaInfo.getVideoInfo().getDuration();
        String duration1 = mediaInfo1.getVideoInfo().getDuration();


        System.out.println(JsonUtil.writePrettyString(mediaInfo));
        System.out.println(JsonUtil.writePrettyString(mediaInfo1));

        System.out.println(NumberUtil.add(duration1,duration1));

    }


}
