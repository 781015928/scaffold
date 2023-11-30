package com.crazypug.http;

import com.crazypug.http.parser.JsoupParser;
import org.jsoup.nodes.Document;

import java.util.List;

public class BaiduHomeParser  extends JsoupParser<List<String>> {
    @Override
    public List<String> parser(Document document) {


        List<String> strings = document.select("a")
                .eachAttr("href");



        return strings;
    }
}
