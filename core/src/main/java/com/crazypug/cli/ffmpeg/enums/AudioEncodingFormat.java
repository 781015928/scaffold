package com.crazypug.cli.ffmpeg.enums;


import lombok.Getter;

/**
 * @author 陈泽功<br />
 * @ClassName: EncodingFormat <br/>
 * @date: 2020-08-12 09:50<br/>
 * @Description: <br/>
 */
@Getter
public enum AudioEncodingFormat {
    AAC("aac", 2),//此类型有几十种 只列出通用的
    ALAC("alac", 2),//此类型有几十种 只列出通用的
    MP3("libmp3lame", 2),
    FLAC("flac", 2),//通用
    COPY("copy", 3),//通用

    // 封装格式。
    ;


    private String libName;

    private int fileType;

    AudioEncodingFormat(String libName, int fileType) {
        this.libName = libName;
        this.fileType = fileType;

    }

    public static AudioEncodingFormat getEncodingFormat(String name) {
        AudioEncodingFormat encodingFormat = AudioEncodingFormat.valueOf(name.toUpperCase());
        if (encodingFormat == null) {
            return COPY;
        }
        return encodingFormat;
    }

}
