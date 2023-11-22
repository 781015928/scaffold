package com.crazypug.cli.ffmpeg.enums;


import lombok.Getter;

/**
 * @author 陈泽功<br />
 * @ClassName: 封装格式 <br/>
 * @date: 2020-08-12 10:00<br/>
 * @Description: ffmpeg -formats
 */
@Getter
public enum PackageFormat {
    AVI("avi", 1),
    F4V("f4v", 1),
    FLV("flv", 1),
    M4V("m4v", 1),
    MOV("mov", 1),
    MP4("mp4", 1),
    AAC("aac", 2),
    FLAC("flac", 2),
    MP3("mp3", 2);
    private String libName;
    private int fileType;

    PackageFormat(String libName, int fileType) {
        this.libName = libName;
        this.fileType = fileType;
    }

    public static PackageFormat getPackageFormat(String name) {
        return PackageFormat.valueOf(name.toUpperCase());
    }
}

