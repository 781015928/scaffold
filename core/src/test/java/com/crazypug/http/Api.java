package com.crazypug.http;

import com.crazypug.http.annotation.DocumentParser;
import com.crazypug.http.annotation.Select;
import org.jsoup.nodes.Document;
import retrofit2.http.GET;

import java.util.List;

public interface Api {


    @DocumentParser(BaiduHomeParser.class)
    @GET("/")
    List<String> home();



    @Select

    @GET("/")
    String selectTest();

    @GET("/")
    Document getDocument();

}
