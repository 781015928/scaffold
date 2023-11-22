package com.crazypug.cli.ffmpeg;

import cn.hutool.core.util.StrUtil;
import com.crazypug.cli.ffmpeg.enums.CodeType;
import com.crazypug.cli.ffmpeg.enums.EncodingFormat;
import com.crazypug.cli.ffmpeg.enums.PackageFormat;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FFmpegCommandBuilder {
    private Integer width;
    private Integer hight;
    private Integer width_aspect;
    private Integer hight_aspect;
    private String currentOutputVideoPath;
    private List<String> currentInputVideoPath;
    private EncodingFormat encodingFormat;
    private PackageFormat packageFormat;
    /**
     * 1 为视频 2为音频
     */
    private CodeType codeType = CodeType.VIDEO;
    private String waterMark;
    private Integer bitrate;
    private Integer maxrate;
    private Integer minrate;
    private Integer crf;

    private File tempFileList;

    public FFmpegCommandBuilder width(Integer width) {
        this.width = width;
        return this;
    }

    public FFmpegCommandBuilder hight(Integer hight) {
        this.hight = hight;
        return this;
    }

    public FFmpegCommandBuilder widthAspect(Integer width_aspect) {
        this.width_aspect = width_aspect;
        return this;
    }

    public FFmpegCommandBuilder hightAspect(Integer hight_aspect) {
        this.hight_aspect = hight_aspect;
        return this;
    }

    public FFmpegCommandBuilder outputVideoPath(String currentOutputVideoPath) {
        this.currentOutputVideoPath = currentOutputVideoPath;
        return this;
    }

    public FFmpegCommandBuilder inputVideoPath(List<String> currentInputVideoPath) {
        this.currentInputVideoPath = currentInputVideoPath;
        return this;
    }

    public FFmpegCommandBuilder inputVideoPath(String currentInputVideoPath) {
        return addInputVideoPath(currentInputVideoPath);
    }

    public FFmpegCommandBuilder addInputVideoPath(String currentInputVideoPath) {
        if (this.currentInputVideoPath == null) {
            this.currentInputVideoPath = new ArrayList<>();
        }
        this.currentInputVideoPath.add(currentInputVideoPath);

        return this;
    }

    public FFmpegCommandBuilder encodingFormat(EncodingFormat encodingFormat) {
        this.encodingFormat = encodingFormat;
        return this;
    }

    public FFmpegCommandBuilder packageFormat(PackageFormat packageFormat) {
        this.packageFormat = packageFormat;
        return this;
    }

    public FFmpegCommandBuilder codeType(CodeType codeType) {
        this.codeType = codeType;
        return this;
    }

    public FFmpegCommandBuilder waterMark(String waterMark) {
        this.waterMark = waterMark;
        return this;
    }

    public FFmpegCommandBuilder bitrate(Integer bitrate) {
        this.bitrate = bitrate;
        return this;
    }

    public FFmpegCommandBuilder maxrate(Integer maxrate) {
        this.maxrate = maxrate;
        return this;
    }

    public FFmpegCommandBuilder minrate(Integer minrate) {
        this.minrate = minrate;
        return this;
    }

    public FFmpegCommandBuilder crf(Integer crf) {
        this.crf = crf;
        return this;
    }

    public List<String> getCommand() {
        List<String> cmd;
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
            if (packageFormat != null) {
                cmd.add("-f");
                cmd.add(packageFormat.getLibName());
            }

        }

        cmd.add("-movflags");
        cmd.add("+faststart");
        cmd.add("-strict");
        cmd.add("-2");

        if (codeType == CodeType.VIDEO) {
            if (!StrUtil.isEmpty(waterMark)) {

                cmd.add("-i");
                cmd.add(waterMark);
                cmd.add("-filter_complex");
                cmd.add("overlay=10:10");
            }
            if (encodingFormat != null) {

                cmd.add("-vcodec");
                cmd.add(encodingFormat.getLibName());

            }

            if (bitrate != null) {

                cmd.add("-b:v");
                cmd.add(bitrate + "k");
            }

            if (width_aspect != null && hight_aspect != null) {

                cmd.add("-aspect");
                cmd.add(String.format("%d:%d", width_aspect, hight_aspect));
            }
            cmd.add("-acodec");
            cmd.add("copy");

            if (width != null && hight != null) {
                cmd.add("-s");
                cmd.add(String.format("%dx%d", width, hight));
            }
            cmd.add("-preset");
            cmd.add("ultrafast");
        } else {

            if (bitrate != null) {
                cmd.add("-b:v");
                cmd.add(bitrate + "k");
            }
            if (encodingFormat != null) {
                cmd.add("-acodec");
                cmd.add(encodingFormat.getLibName());
            }

        }

        if (maxrate != null) {
            cmd.add("-maxrate");
            cmd.add(maxrate + "k");

        }
        if (minrate != null) {
            cmd.add("-minrate");
            cmd.add(minrate + "k");
        }
        if (crf != null) {
            cmd.add("-crf");
            cmd.add(String.valueOf(crf));
        }

        cmd.add("-y");
        if (currentOutputVideoPath != null) {
            cmd.add(currentOutputVideoPath);
        }
        return cmd;

    }


    public void deleteTempFile() {
        if (tempFileList != null) {
            tempFileList.deleteOnExit();
        }
    }

}
