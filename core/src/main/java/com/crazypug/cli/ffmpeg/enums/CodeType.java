package com.crazypug.cli.ffmpeg.enums;

public enum CodeType {

    VIDEO() {
        @Override
        public String toString() {
            return "video";
        }
    }, AUDIO {
        @Override
        public String toString() {
            return "audio";
        }


        ;
    }
}
