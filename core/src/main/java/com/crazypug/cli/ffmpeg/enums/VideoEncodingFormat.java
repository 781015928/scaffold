package com.crazypug.cli.ffmpeg.enums;


import lombok.Getter;

/**
 * @author 陈泽功<br />
 * @ClassName: EncodingFormat <br/>
 * @date: 2020-08-12 09:50<br/>
 * @Description: <br/>
 */
@Getter
public enum VideoEncodingFormat {
    H264("libx264", 1),//此类型有几十种 只列出通用的
    H265("libx265", 1),//此类型有几十种 只列出通用的
    H264_VIDEO_TOOL_BOX("h264_videotoolbox", 1),//此类型有几十种 只列出通用的
    OPEN_H264("libopenh264", 1),//此类型有几十种 只列出通用的
    FLV("flv", 1),//此类型有几十种 只列出通用的
    AVI("libaom-av1", 1),//此类型有几十种 只列出通用的

    COPY("copy", 3),//通用

    // 封装格式。
    ;


    private String libName;

    private int fileType;

    VideoEncodingFormat(String libName, int fileType) {
        this.libName = libName;
        this.fileType = fileType;

    }

    public static VideoEncodingFormat getEncodingFormat(String name) {
        VideoEncodingFormat encodingFormat = VideoEncodingFormat.valueOf(name.toUpperCase());
        if (encodingFormat == null) {
            return COPY;
        }
        return encodingFormat;
    }

}
