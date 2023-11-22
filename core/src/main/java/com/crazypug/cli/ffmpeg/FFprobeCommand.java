package com.crazypug.cli.ffmpeg;

import com.crazypug.cli.Command;
import com.crazypug.cli.ffmpeg.model.FFprobeInfo;
import com.crazypug.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FFprobeCommand extends Command {


    private static final String BINARY_NAME = "ffprobe";

    private static final String[] BINARY_NAME_ARGS = {BINARY_NAME, "-show_format", "-show_streams", "-print_format", "json", "-loglevel", "error"};


    public FFprobeInfo getMediaInfo(String inputPath) {
        ArrayList<String> commands = new ArrayList<>(Arrays.asList(BINARY_NAME_ARGS));
        commands.add("-i");
        commands.add(inputPath);
        return getMediaInfo(commands);
    }

    public FFprobeInfo getMediaInfo(List<String> commands) {
        String json = executeCommand(commands).collect(Collectors.joining());
        return JsonUtil.readValue(json, FFprobeInfo.class);

    }


    @Override
    public String getBinary() {
        return BINARY_NAME;
    }
}
