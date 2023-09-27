package com.crazypug.web.autoconfigure;


import com.crazypug.web.aop.MDCInterceptor;
import com.crazypug.web.exception.ExceptionHandlerAdvice;
import com.crazypug.web.aop.LogAspect;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AutoConfiguration()
public class CrazyPugConfiguration  implements WebMvcConfigurer {



    @Bean
    @ConditionalOnProperty(name = "crazypug.exception-handler.enable", havingValue = "true")
    public ExceptionHandlerAdvice exceptionHandler(){
        return new ExceptionHandlerAdvice();
    }


    @Bean
    @ConditionalOnProperty(name = "crazypug.request-log.enable", havingValue = "true")
    public LogAspect logaspect( ) {
        return new LogAspect();
    }




    @ConditionalOnProperty(name = "crazypug.mdc.enable", havingValue = "true")
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MDCInterceptor()).addPathPatterns("/**");
    }
}
