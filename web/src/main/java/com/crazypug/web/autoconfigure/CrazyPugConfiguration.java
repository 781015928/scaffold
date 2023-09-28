package com.crazypug.web.autoconfigure;


import com.crazypug.web.aop.MDCInterceptor;
import com.crazypug.web.aop.MDCWebMvcConfigurer;
import com.crazypug.web.config.CrazyPugConfig;
import com.crazypug.web.exception.ExceptionHandlerAdvice;
import com.crazypug.web.aop.LogAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AutoConfiguration()
@ComponentScan("com.crazypug.web")
public class CrazyPugConfiguration  implements WebMvcConfigurer    {

    @Bean
    public CrazyPugConfig crazyPugConfig( ) {
        return new CrazyPugConfig();
    }

    @Bean
    @ConditionalOnProperty(name = "crazypug.exception-handler.enable", havingValue = "true")
    public ExceptionHandlerAdvice exceptionHandler(){
        return new ExceptionHandlerAdvice();
    }

    @ConditionalOnProperty(name = "crazypug.mdc.enable", havingValue = "true")
    @Bean
    public MDCWebMvcConfigurer mdcWebMvcConfigurer(MDCInterceptor mdcInterceptor) {
        return new MDCWebMvcConfigurer(mdcInterceptor);
    }

    @Bean
    @ConditionalOnProperty(name = "crazypug.mdc.enable", havingValue = "true")
    public MDCInterceptor mdcInterceptor(CrazyPugConfig crazyPugConfig) {
        return new MDCInterceptor(crazyPugConfig);
    }


    @Bean
    @ConditionalOnProperty(name = "crazypug.request-log.enable", havingValue = "true")
    public LogAspect logaspect( ) {
        return new LogAspect();
    }





}
