package com.crazypug.http.exception;

import java.util.HashMap;
import java.util.Map;

public class HttpException extends RuntimeException {
    private int statusCode;
    private String body;

    public HttpException(int statusCode, String message, String body, Map<String, String> headers) {
        super(message);
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
    }

    Map<String, String> headers;

    /**
     * Constructor for the HTTPException
     *
     * @param statusCode <code>int</code> for the HTTP status code
     **/

    public HttpException(int statusCode, String message) {
        this(statusCode, message, null, new HashMap<>());

    }

    public HttpException(int statusCode, String message, String body) {
        this(statusCode, message, body, new HashMap<>());

    }

    /**
     * Gets the HTTP status code.
     *
     * @return HTTP status code
     **/
    public int getStatusCode() {
        return statusCode;
    }


    @Override
    public String getMessage() {
        return String.format("{code=%d,message=%s,body=%s}", statusCode, super.getMessage(), body);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
