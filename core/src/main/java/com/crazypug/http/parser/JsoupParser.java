package com.crazypug.http.parser;

import okhttp3.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class JsoupParser<T> {


    private final Type actualTypeArgument;

    public JsoupParser() {
        actualTypeArgument = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Type getType() {
        return actualTypeArgument;
    }


    public abstract T parser(Document document);


}
