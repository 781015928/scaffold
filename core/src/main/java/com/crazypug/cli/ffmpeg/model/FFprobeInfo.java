package com.crazypug.cli.ffmpeg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;


@NoArgsConstructor
@Data
public class FFprobeInfo {


    @JsonProperty("streams")
    private List<MediaInfo> streams;
    @JsonProperty("format")
    private Format format;


    @JsonIgnore
    public MediaInfo getVideoInfo() {
        return streams.stream().filter(it -> Objects.equals("video", it.codecType)).findFirst().orElse(null);
    }

    @JsonIgnore
    public MediaInfo getAudioInfo() {
        return streams.stream().filter(it -> Objects.equals("audio", it.codecType)).findFirst().orElse(null);
    }

    @NoArgsConstructor
    @Data
    public static class Format {
        @JsonProperty("filename")
        private String filename;
        @JsonProperty("nb_streams")
        private Integer nbStreams;
        @JsonProperty("nb_programs")
        private Integer nbPrograms;
        @JsonProperty("format_name")
        private String formatName;
        @JsonProperty("format_long_name")
        private String formatLongName;
        @JsonProperty("start_time")
        private String startTime;
        @JsonProperty("duration")
        private String duration;
        @JsonProperty("size")
        private String size;
        @JsonProperty("bit_rate")
        private String bitRate;
        @JsonProperty("probe_score")
        private Integer probeScore;
        @JsonProperty("tags")
        private Tags tags;

        @NoArgsConstructor
        @Data
        public static class Tags {
            @JsonProperty("major_brand")
            private String majorBrand;
            @JsonProperty("minor_version")
            private String minorVersion;
            @JsonProperty("compatible_brands")
            private String compatibleBrands;
            @JsonProperty("encoder")
            private String encoder;
        }
    }

    @NoArgsConstructor
    @Data
    public static class MediaInfo {
        @JsonProperty("index")
        private Integer index;
        @JsonProperty("codec_name")
        private String codecName;
        @JsonProperty("codec_long_name")
        private String codecLongName;
        @JsonProperty("profile")
        private String profile;
        @JsonProperty("codec_type")
        private String codecType;
        @JsonProperty("codec_tag_string")
        private String codecTagString;
        @JsonProperty("codec_tag")
        private String codecTag;
        @JsonProperty("width")
        private Integer width;
        @JsonProperty("height")
        private Integer height;
        @JsonProperty("coded_width")
        private Integer codedWidth;
        @JsonProperty("coded_height")
        private Integer codedHeight;
        @JsonProperty("closed_captions")
        private Integer closedCaptions;
        @JsonProperty("film_grain")
        private Integer filmGrain;
        @JsonProperty("has_b_frames")
        private Integer hasBFrames;
        @JsonProperty("pix_fmt")
        private String pixFmt;
        @JsonProperty("level")
        private Integer level;
        @JsonProperty("chroma_location")
        private String chromaLocation;
        @JsonProperty("field_order")
        private String fieldOrder;
        @JsonProperty("refs")
        private Integer refs;
        @JsonProperty("is_avc")
        private String isAvc;
        @JsonProperty("nal_length_size")
        private String nalLengthSize;
        @JsonProperty("id")
        private String id;
        @JsonProperty("r_frame_rate")
        private String rFrameRate;
        @JsonProperty("avg_frame_rate")
        private String avgFrameRate;
        @JsonProperty("time_base")
        private String timeBase;
        @JsonProperty("start_pts")
        private Integer startPts;
        @JsonProperty("start_time")
        private String startTime;
        @JsonProperty("duration_ts")
        private Integer durationTs;
        @JsonProperty("duration")
        private String duration;
        @JsonProperty("bit_rate")
        private String bitRate;
        @JsonProperty("bits_per_raw_sample")
        private String bitsPerRawSample;
        @JsonProperty("nb_frames")
        private String nbFrames;
        @JsonProperty("extradata_size")
        private Integer extradataSize;
        @JsonProperty("disposition")
        private Disposition disposition;
        @JsonProperty("tags")
        private Tags tags;
        @JsonProperty("sample_fmt")
        private String sampleFmt;
        @JsonProperty("sample_rate")
        private String sampleRate;
        @JsonProperty("channels")
        private Integer channels;
        @JsonProperty("channel_layout")
        private String channelLayout;
        @JsonProperty("bits_per_sample")
        private Integer bitsPerSample;
        @JsonProperty("initial_padding")
        private Integer initialPadding;

        @NoArgsConstructor
        @Data
        public static class Disposition {
            @JsonProperty("default")
            private Integer defaultX;
            @JsonProperty("dub")
            private Integer dub;
            @JsonProperty("original")
            private Integer original;
            @JsonProperty("comment")
            private Integer comment;
            @JsonProperty("lyrics")
            private Integer lyrics;
            @JsonProperty("karaoke")
            private Integer karaoke;
            @JsonProperty("forced")
            private Integer forced;
            @JsonProperty("hearing_impaired")
            private Integer hearingImpaired;
            @JsonProperty("visual_impaired")
            private Integer visualImpaired;
            @JsonProperty("clean_effects")
            private Integer cleanEffects;
            @JsonProperty("attached_pic")
            private Integer attachedPic;
            @JsonProperty("timed_thumbnails")
            private Integer timedThumbnails;
            @JsonProperty("non_diegetic")
            private Integer nonDiegetic;
            @JsonProperty("captions")
            private Integer captions;
            @JsonProperty("descriptions")
            private Integer descriptions;
            @JsonProperty("metadata")
            private Integer metadata;
            @JsonProperty("dependent")
            private Integer dependent;
            @JsonProperty("still_image")
            private Integer stillImage;
        }

        @NoArgsConstructor
        @Data
        public static class Tags {
            @JsonProperty("language")
            private String language;
            @JsonProperty("handler_name")
            private String handlerName;
            @JsonProperty("vendor_id")
            private String vendorId;
        }
    }
}
