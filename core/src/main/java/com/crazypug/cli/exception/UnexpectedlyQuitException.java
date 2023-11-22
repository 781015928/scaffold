package com.crazypug.cli.exception;

public class UnexpectedlyQuitException extends RuntimeException {
    private int code;

    public UnexpectedlyQuitException(String message, int code) {
        super(message);
        this.code = code;
    }

    public UnexpectedlyQuitException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }
}
