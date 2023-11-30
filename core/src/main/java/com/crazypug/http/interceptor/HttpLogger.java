package com.crazypug.http.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface HttpLogger {


    public HttpLogger default_logger = new HttpLogger() {

        Logger log = LoggerFactory.getLogger(HttpLogger.class);

        @Override
        public void log(String message) {
            log.info(message);
        }
    };


    void log(String message);
}
