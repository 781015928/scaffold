package com.crazypug.http.adapter;

import com.crazypug.http.exception.HttpException;
import com.crazypug.http.response.HeaderResponse;
import lombok.SneakyThrows;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ExtendedResponseCallAdapterFactory extends CallAdapter.Factory {
    public static CallAdapter.Factory create() {
        return new ExtendedResponseCallAdapterFactory();
    }

    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (returnType.getTypeName().startsWith(HeaderResponse.class.getName())) {
            return new BaseResponseCallAdapter<>(returnType);
        }
        return new DefaultCallAdapter(returnType);
    }

//    private static class JsoupSelectCallAdapter<E> implements CallAdapter {
//        private Select select;
//
//        private Type returnType;
//
//        public JsoupSelectCallAdapter(Select select, Type returnType) {
//            this.select = select;
//            this.returnType = returnType;
//            if (select != null) {
//                if (returnType instanceof ParameterizedType) {
//
//                    Type ownerType = ((ParameterizedType) returnType).getOwnerType();
//                    if (ownerType != select.method().getType()) {
//                        throw new RuntimeException("@Select  method or return type error  return type is " + ownerType.getTypeName() + " @Select Type is " + select.method().getType());
//                    }
//                } else {
//                    if (returnType != select.method().getType()) {
//                        throw new RuntimeException("@Select  method or return type error  return type is " + returnType.getTypeName() + " @Select Type is " + select.method().getType());
//                    }
//                }
//
//
//            }
//        }
//
//        @Override
//        public Type responseType() {
//            return returnType;
//
//        }
//
//        @SneakyThrows
//        @Override
//        public Object adapt(Call call) {
//            String baseUrl = call.request().url().toString();
//            String body = (String) call.execute().body();
//
//            Document document = Jsoup.parse(body, baseUrl);
//            if (select != null) {
//                Select.SelectMethod method = select.method();
//                String value = select.value();
//
//                switch (method) {
//                    case ALL:
//                        return document.select(value);
//                    case VAL:
//                        return document.select(value).val();
//                    case HTML:
//                        return document.select(value).outerHtml();
//                }
//
//
//            }
//
//            return document;
//
//        }
//
//
//    }

    private static class DefaultCallAdapter<E> implements CallAdapter {
        Type returnType;

        public DefaultCallAdapter(Type returnType) {
            this.returnType = returnType;
        }

        @Override
        public Type responseType() {
            return returnType;
        }


        @SneakyThrows
        @Override
        public Object adapt(Call call) {
            Response response = call.execute();
            if (response.isSuccessful()) {
                Object body = response.body();
                return body;
            }
            throw new HttpException(response.code(), response.message(), response.errorBody().string());
        }
    }


    private static class BaseResponseCallAdapter<E> implements CallAdapter<HeaderResponse> {
        Type returnType;

        public BaseResponseCallAdapter(Type returnType) {
            this.returnType = ((ParameterizedType) returnType).getActualTypeArguments()[0];
        }

        @Override
        public Type responseType() {
            return returnType;
        }

        @SneakyThrows
        @Override
        public <R> HeaderResponse<R> adapt(Call<R> call) {
            Response<R> response = call.execute();
            if (response.isSuccessful()) {

                Headers headers = response.headers();
                Map<String, String> headersMap = new HashMap<>();
                for (String name : headers.names()) {
                    headersMap.put(name, headers.get(name));
                }
                R body = response.body();
                HeaderResponse<R> headerResponse = new HeaderResponse<R>();
                headerResponse.setHeader(headersMap);
                headerResponse.setBody(body);
                return headerResponse;
            }
            throw new HttpException(response.code(), response.message(), response.errorBody().string());
        }
    }

}
