package com.crazypug.web.config;


import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "crazypug")
public class CrazyPugConfig {


    public ExceptionHandlerConfig exceptionHandler = new ExceptionHandlerConfig();

    public MDCConfig mdc = new MDCConfig();

    public RequestLogConfig requestLog = new RequestLogConfig();


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ExceptionHandlerConfig {
        /**
         * 是否开启全局异常
         */
        private boolean enable = true;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class MDCConfig {
        /**
         * 是否开启MDC—ID生成
         */
        public boolean enable = true;

        /**
         * mdc ID 名称
         */
        public String traceIdName = "x-trace-id";


    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class RequestLogConfig {
        /**
         * 请求日志开关
         */
        private boolean enable = true;


    }
}
