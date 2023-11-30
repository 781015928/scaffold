package com.crazypug.http;

import com.crazypug.http.adapter.ExtendedConverterFactory;
import com.crazypug.http.adapter.ExtendedResponseCallAdapterFactory;
import com.crazypug.utils.JsonUtil;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Retrofit;

import java.util.List;
import java.util.Objects;

public class RetrofitTest {

    private Retrofit retrofit;
    private Api api;

    @Before
    public void init() {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.baidu.com/")
                .addCallAdapterFactory(ExtendedResponseCallAdapterFactory.create())
                .addConverterFactory(ExtendedConverterFactory.create(JsonUtil.getObjectMapper())).build();

        api = retrofit.create(Api.class);
    }

    @Test
    public void testBaidu() {


        List<String> title = api.home();

        System.out.println(title);
    }

    @Test
    public void selectTest() {


        Object title = api.selectTest();

        System.out.println(title);
    }

    @Test
    public void getDocument() {


        Document title = api.getDocument();

        System.out.println(title);


        System.out.println(title);

    }
}
