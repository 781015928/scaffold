package com.crazypug.cli.ffmpeg.enums;


import lombok.Getter;

/**
 * @author 陈泽功<br />
 * @ClassName: EncodingFormat <br/>
 * @date: 2020-08-12 09:50<br/>
 * @Description: <br/>
 */
@Getter
public enum EncodingFormat {
    H264("libx264", 1),//此类型有几十种 只列出通用的
    H265("libx265", 1),//此类型有几十种 只列出通用的

    AAC("aac", 2),//此类型有几十种 只列出通用的

    MP3("libmp3lame", 2),
    FLAC("flac", 2),//通用
    COPY("copy", 3),//通用

    // 封装格式。
    ;


    private String libName;

    private int fileType;

    EncodingFormat(String libName, int fileType) {
        this.libName = libName;
        this.fileType = fileType;

    }

    public static EncodingFormat getEncodingFormat(String name) {
        EncodingFormat encodingFormat = EncodingFormat.valueOf(name.toUpperCase());
        if (encodingFormat == null) {
            return COPY;
        }
        return encodingFormat;
    }

}
