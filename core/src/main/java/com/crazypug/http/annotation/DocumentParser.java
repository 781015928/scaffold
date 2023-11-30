package com.crazypug.http.annotation;

import com.crazypug.http.parser.JsoupParser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DocumentParser {

    Class<? extends JsoupParser<?>> value();

}
