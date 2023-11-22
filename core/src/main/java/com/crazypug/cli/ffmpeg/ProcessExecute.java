package com.crazypug.cli.ffmpeg;

import com.crazypug.cli.exception.UnexpectedlyQuitException;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
public class ProcessExecute {


    //这里
    public Stream<String> execute(List<String> command) {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(command);
        try {
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                    new ProcessIterator(builder),
                    Spliterator.ORDERED | Spliterator.IMMUTABLE), false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Stream<String> execute(String command) {
        return execute(command.split(" "));
    }

    public Stream<String> execute(String... command) {
        return execute(Arrays.asList(command));
    }

    @Slf4j
    public static class ProcessIterator implements Iterator<String> {
        private static boolean IS_LOG_TRACE = log.isTraceEnabled();


        ProcessBuilder processBuilder;
        BufferedReader errorReader;
        BufferedReader infoReader;
        Process process;
        private String line;

        private boolean isStart;


        LinkedHashMap<String, String> errorInfoStackList = new LinkedHashMap<String, String>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > 100;
            }
        };

        private void putErrorStack(String errorMessage) {
            if (errorMessage == null) return;
            errorInfoStackList.put(errorMessage, errorMessage);
        }

        public String getErrorMessage() {
            return errorInfoStackList.keySet().stream().collect(Collectors.joining("\n"));
        }

        public ProcessIterator(ProcessBuilder process) {
            this.processBuilder = process;
        }

        private void start() {

            try {
                if (isStart) return;

                process = processBuilder.start();
                isStart = true;
                errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                infoReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            } catch (IOException e) {
                throw new UnexpectedlyQuitException(e.getMessage() + getErrorMessage(), e, -1);
            }
        }

        private String read() {
            try {
                String line = errorReader.readLine();
                putErrorStack(line);
                if (line == null) {
                    line = infoReader.readLine();
                }

                if (IS_LOG_TRACE) {
                    log.trace(line);
                }
                return line;
            } catch (IOException e) {
                throw new RuntimeException(getErrorMessage(),e);
            }
        }

        public void finish() {
            try {
                errorReader.close();
                infoReader.close();
                int code = process.waitFor();
                if (code != 0) {
                    throw new UnexpectedlyQuitException("process exit code  "+process.exitValue()+"\n" +getErrorMessage(), process.exitValue());
                }
                process.destroy();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        @Override
        public boolean hasNext() {
            if (!isStart) {
                start();
                line = read();
            }

            return line != null;
        }

        @Override
        public String next() {

            String currentLine = this.line;

            this.line = read();

            if (this.line == null) {
                finish();
            }
            return currentLine;
        }


    }


}
