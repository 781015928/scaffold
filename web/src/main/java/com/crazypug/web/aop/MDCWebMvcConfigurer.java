package com.crazypug.web.aop;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;




@ConditionalOnProperty(name = "crazypug.mdc.enable", havingValue = "true")
@AllArgsConstructor
public class MDCWebMvcConfigurer implements WebMvcConfigurer {
   private MDCInterceptor mdcInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(mdcInterceptor).addPathPatterns("/**");
    }


}
