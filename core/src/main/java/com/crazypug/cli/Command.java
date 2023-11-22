package com.crazypug.cli;


import com.crazypug.cli.ffmpeg.ProcessExecute;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public abstract class Command {

    private static final String VERSION_ARGS = "-version";


    ProcessExecute processExecute;

    public Command() {
        this.processExecute = new ProcessExecute();
    }


    public Stream<String> executeCommand(List<String> args) {
        return processExecute.execute(args);
    }

    public String executeVersionCommand() {
        return executeCommand(Arrays.asList(getBinary(), getVersion())).collect(Collectors.joining("\n"));
    }

    protected String getVersion() {
        return VERSION_ARGS;
    }


    public abstract String getBinary();

    public void checkEnv() {
        String version = getVersion();
        log.info(version);
    }


    //UnexpectedlyQuitException


}
