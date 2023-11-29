package com.crazypug.cli.ffmpeg;


import cn.hutool.core.util.StrUtil;
import com.crazypug.cli.Command;
import com.crazypug.cli.ffmpeg.enums.CodeType;
import com.crazypug.cli.ffmpeg.model.FFmpegProgress;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FFmpegCommand extends Command {

    private static final Pattern FILTER_MORE_THAN_PATTERN = Pattern.compile(".*Last message repeated.*|.*More than.*|.*libx264.*|Opening.*|.*Past duration.*|.*hls,applehttp.*|.*http.*Opening.*");


    //frame=204873 fps=3413 q=23.0 size= 1168637kB time=01:53:56.86 bitrate=1400.3kbits/s speed= 114x
    // size=  375040kB time=00:45:50.58 bitrate=1117.0kbits/s speed=1.37e+03x
    //frame=    0 fps=0.0 q=0.0 size=       0kB time=00:00:00.34 bitrate=   1.1kbits/s dup=1 drop=0 speed=21.8x
    private static final  Pattern FRAME_PATTERN = Pattern.compile(".*size=([\\s\\S]*)time=(.*?)bitrate=([\\s\\S]*/s) .* speed=(.*?)x");
    //进度



    private long intervalSeconds = -1;

    private long lastProgressTime = -1;
    private File tempFileList;


    public FFmpegCommand() {
    }

    @Override
    public Stream<String> executeCommand(List<String> commands) {
        return super.executeCommand(commands)
                .filter(this::filterOutUselessLogs)
                .onClose(new Runnable() {
                    @Override
                    public void run() {
                        if (tempFileList != null) {
                            tempFileList.deleteOnExit();
                        }
                    }
                });
    }

    private List<String> getCommand(FFmpegCommandBuilder ffcb) {
        List<String> cmd;
        List<String> currentInputVideoPath = ffcb.getCurrentInputVideoPath();
        if (currentInputVideoPath.get(0).toUpperCase().endsWith("M3U8")) {
            cmd = new ArrayList<>(Arrays.asList("ffmpeg", "-allowed_extensions", "ALL"));
        } else {
            cmd = new ArrayList<>(Arrays.asList("ffmpeg"));
        }
        if (currentInputVideoPath.size() > 1) {//多文件合并
            tempFileList = new File(new File(currentInputVideoPath.get(0)).getParentFile(), UUID.randomUUID().toString().replace("-", "") + ".txt");
            if (tempFileList.exists()) {
                tempFileList.delete();
            }
            RandomAccessFile raf = null;
            try {
                tempFileList.createNewFile();
                raf = new RandomAccessFile(tempFileList, "rwd");
                StringBuffer content = new StringBuffer();
                for (int index = 0; index < currentInputVideoPath.size(); index++) {
                    content.append("file '");
                    content.append(new File(currentInputVideoPath.get(index)).getAbsolutePath());
                    content.append("'");
                    if (index != currentInputVideoPath.size() - 1) {
                        content.append("\r\n");
                    }
                }
                raf.seek(tempFileList.length());
                raf.write(content.toString().getBytes());
                cmd.add("-f");
                cmd.add("concat");
                cmd.add("-safe");
                cmd.add("0");
                cmd.add("-i");
                cmd.add(tempFileList.getPath());


            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            } finally {
                if (raf != null) {
                    try {
                        raf.close();
                    } catch (IOException e) {
                    }
                }
            }
        } else if (currentInputVideoPath.size() == 1) {

            cmd.add("-i");
            cmd.add(String.format("%s", currentInputVideoPath.get(0)));
            if (ffcb.getPackageFormat() != null) {
                cmd.add("-f");
                cmd.add(ffcb.getPackageFormat().getLibName());
            }
        }

        if (ffcb.getCodeType() == CodeType.VIDEO) {
            if (!StrUtil.isEmpty(ffcb.getWaterMark())) {

                cmd.add("-i");
                cmd.add(ffcb.getWaterMark());
                cmd.add("-filter_complex");
                cmd.add("overlay=10:10");
            }

            cmd.add("-movflags");
            cmd.add("+faststart");
            cmd.add("-strict");
            cmd.add("-2");

            if (ffcb.getVideoEncodingFormat() != null) {
                cmd.add("-vcodec");
                cmd.add(ffcb.getVideoEncodingFormat().getLibName());
            }

            if (ffcb.getBitrate() != null) {

                cmd.add("-b:v");
                cmd.add(ffcb.getBitrate() + "k");
            }

            if (ffcb.getWidth_aspect() != null && ffcb.getHight_aspect() != null) {

                cmd.add("-aspect");
                cmd.add(String.format("%d:%d", ffcb.getWidth_aspect(), ffcb.getHight_aspect()));
            }

            if (ffcb.getAudioEncodingFormat() != null) {
                cmd.add("-acodec");
                cmd.add(ffcb.getAudioEncodingFormat().getLibName());
            }

            if (ffcb.getWidth() != null && ffcb.getHight() != null) {
                cmd.add("-s");
                cmd.add(String.format("%dx%d", ffcb.getWidth(), ffcb.getHight()));
            }
            cmd.add("-preset");
            cmd.add("ultrafast");
        } else {

            if (ffcb.getBitrate() != null) {
                cmd.add("-b:v");
                cmd.add(ffcb.getBitrate() + "k");
            }
            if (ffcb.getAudioEncodingFormat() != null) {
                cmd.add("-acodec");
                cmd.add(ffcb.getAudioEncodingFormat().getLibName());
            }

        }

        if (ffcb.getMaxrate() != null) {
            cmd.add("-maxrate");
            cmd.add(ffcb.getMaxrate() + "k");

        }
        if (ffcb.getMinrate() != null) {
            cmd.add("-minrate");
            cmd.add(ffcb.getMinrate() + "k");
        }
        if (ffcb.getCrf() != null) {
            cmd.add("-crf");
            cmd.add(String.valueOf(ffcb.getCrf()));
        }

        cmd.add("-y");
        if (ffcb.getCurrentOutputVideoPath() != null) {
            cmd.add(ffcb.getCurrentOutputVideoPath());
        }
        return cmd;

    }


    public Stream<FFmpegProgress> executeAndGetProgress(FFmpegCommandBuilder commands) {
        return executeAndGetProgress(5, commands);
    }


    public Stream<FFmpegProgress> executeAndGetProgress(long intervalSeconds, FFmpegCommandBuilder commands) {
        this.intervalSeconds = intervalSeconds;
        return executeCommand(getCommand(commands))
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

    public static void main(String[] args) {

        //进度

        String s = "frame=47584 fps=826 q=22.0 size=  295680kB time=00:26:26.13 bitrate=1527.1kbits/s dup=1 drop=0 speed=27.5x    ";

        Matcher matcher = FRAME_PATTERN.matcher(s);
        matcher.find();


        String group = matcher.group();

        System.out.println(group);

    }
}
