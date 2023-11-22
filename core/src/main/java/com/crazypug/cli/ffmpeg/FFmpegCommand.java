package com.crazypug.cli.ffmpeg;


import com.crazypug.cli.Command;
import com.crazypug.cli.ffmpeg.enums.EncodingFormat;
import com.crazypug.cli.ffmpeg.model.FFmpegProgress;
import com.crazypug.cli.ffmpeg.model.FFprobeInfo;
import com.crazypug.utils.JsonUtil;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FFmpegCommand extends Command {

    private static final Pattern FILTER_MORE_THAN_PATTERN = Pattern.compile(".*Last message repeated.*|.*More than.*|.*libx264.*|Opening.*|.*Past duration.*|.*hls,applehttp.*|.*http.*Opening.*");


    //frame=204873 fps=3413 q=23.0 size= 1168637kB time=01:53:56.86 bitrate=1400.3kbits/s speed= 114x
    // size=  375040kB time=00:45:50.58 bitrate=1117.0kbits/s speed=1.37e+03x
    //frame=    0 fps=0.0 q=0.0 size=       0kB time=00:00:00.34 bitrate=   1.1kbits/s dup=1 drop=0 speed=21.8x
    private static final Pattern FRAME_PATTERN = Pattern.compile(".*size=([\\s\\S]*)time=(.*?)bitrate=([\\s\\S]*/s) speed=(.*?)x");
    //进度


    private long intervalSeconds = -1;

    private long lastProgressTime = -1;


    public FFmpegCommand() {
    }

    @Override
    public Stream<String> executeCommand(List<String> commands) {
        return super.executeCommand(commands)
                .filter(this::filterOutUselessLogs);
    }


    public Stream<FFmpegProgress> executeAndGetProgress(List<String> commands) {
        return executeAndGetProgress(commands);
    }


    public Stream<FFmpegProgress> executeAndGetProgress(long intervalSeconds,List<String> commands) {
        this.intervalSeconds = intervalSeconds;
        return super.executeCommand(commands)
                .filter(this::filterFramePattern)
                .map(this::getProgress);
    }

    private FFmpegProgress getProgress(String info) {
        Matcher matcher = FRAME_PATTERN.matcher(info);
        matcher.find();
        return FFmpegProgress.builder()
                .size(matcher.group(1).trim())
                .time(matcher.group(2).trim())
                .bitrate(matcher.group(3).trim())
                .speed(Float.parseFloat(matcher.group(4).trim()))
                .build();
    }

    private boolean filterFramePattern(String info) {
        if (intervalSeconds != -1) {
            if (lastProgressTime != -1) {
                long timeInterval = System.currentTimeMillis() - lastProgressTime;
                if (timeInterval > this.intervalSeconds * 1000) {

                    lastProgressTime = System.currentTimeMillis();

                    return FRAME_PATTERN.matcher(info).find();
                } else {

                    return false;
                }
//ffmpeg -i /Users/czg/Desktop/The.Gods.Ⅰ.Kingdom.of.Storms.2023.Chinese.HQCAM.x264-TearsHD.mp4 -acodec copy  -vcodec copy  -y /Users/czg/Desktop/AKS489cdd43-8b70-49fe-84bd-60e58ccfc9c7.mp4
            } else {
                lastProgressTime = System.currentTimeMillis();
                return FRAME_PATTERN.matcher(info).find();
            }
        }
        return FRAME_PATTERN.matcher(info).find();
    }

    private boolean filterOutUselessLogs(String info) {
        return !FILTER_MORE_THAN_PATTERN.matcher(info).find();
    }



    @Override
    public String getBinary() {
        return "ffmpeg";
    }
}
