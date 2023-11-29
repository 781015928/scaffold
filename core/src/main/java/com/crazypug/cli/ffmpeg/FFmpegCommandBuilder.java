package com.crazypug.cli.ffmpeg;

import com.crazypug.cli.ffmpeg.enums.AudioEncodingFormat;
import com.crazypug.cli.ffmpeg.enums.CodeType;
import com.crazypug.cli.ffmpeg.enums.VideoEncodingFormat;
import com.crazypug.cli.ffmpeg.enums.PackageFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FFmpegCommandBuilder {
    private Integer width;
    private Integer hight;
    private Integer width_aspect;
    private Integer hight_aspect;
    private String currentOutputVideoPath;
    private List<String> currentInputVideoPath;
    private VideoEncodingFormat videoEncodingFormat;
    private AudioEncodingFormat audioEncodingFormat;
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

    public FFmpegCommandBuilder audioEncodingFormat(AudioEncodingFormat encodingFormat) {
        this.audioEncodingFormat = encodingFormat;
        return this;
    }

    public FFmpegCommandBuilder videoEncodingFormat(VideoEncodingFormat encodingFormat) {
        this.videoEncodingFormat = encodingFormat;
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



    public void deleteTempFile() {
        if (tempFileList != null) {
            tempFileList.deleteOnExit();
        }
    }

}
