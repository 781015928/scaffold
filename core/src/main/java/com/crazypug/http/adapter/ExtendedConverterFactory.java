package com.crazypug.http.adapter;

import cn.hutool.core.util.URLUtil;
import com.crazypug.http.annotation.DocumentParser;
import com.crazypug.http.annotation.Select;
import com.crazypug.http.exception.DocumentParserException;
import com.crazypug.http.parser.JsoupParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.http.Query;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
public class ExtendedConverterFactory extends Converter.Factory {
    /**
     * Create an instance using a default {@link ObjectMapper} instance for conversion.
     */
    public static ExtendedConverterFactory create() {
        return create(new ObjectMapper());
    }

    /**
     * Create an instance using {@code mapper} for conversion.
     */
    public static ExtendedConverterFactory create(ObjectMapper mapper) {
        return new ExtendedConverterFactory(mapper);
    }

    private final ObjectMapper mapper;

    private ExtendedConverterFactory(ObjectMapper mapper) {
        if (mapper == null) throw new NullPointerException("mapper == null");
        this.mapper = mapper;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {

        if (StringResponseBodyConverter.isStringConverter(type, annotations)) {
            return new StringResponseBodyConverter();
        }
        if (DocumentResponseBodyConverter.isDocumentType(type, annotations)) {

            return new DocumentResponseBodyConverter();
        }
        if (QueryObjectRequestConverter.isQueryObjectConverter(type, annotations)) {
            Query query = QueryObjectRequestConverter.getQuery(annotations);
            return new QueryObjectRequestConverter<>(mapper, query);
        }
        if (DocumentSelectResponseBodyConverter.isDocumentSelectConverter(type, annotations)) {
            Select select = DocumentSelectResponseBodyConverter.getSelect(annotations);
            return new DocumentSelectResponseBodyConverter(select);
        }
        if (DocumentParserResponseBodyConverter.isDocumentParserConverter(type, annotations)) {
            DocumentParser documentParser = DocumentParserResponseBodyConverter.getDocumentParser(annotations);
            return new DocumentParserResponseBodyConverter(documentParser, type);
        }

        JavaType javaType = mapper.getTypeFactory().constructType(type);
        ObjectReader reader = mapper.reader(javaType);
        return new JacksonResponseBodyConverter<>(reader);
    }


    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        JavaType javaType = mapper.getTypeFactory().constructType(type);
        ObjectWriter writer = mapper.writerWithType(javaType);
        return new JacksonRequestBodyConverter<>(writer);
    }

    static class QueryObjectRequestConverter<T> implements Converter<T, String> {
        Query query;
        private final ObjectMapper mapper;

        public static boolean isQueryObjectConverter(Type type, Annotation[] annotations) {
            if (isPrimitive(type)) {
                return false;
            }
            Query query = Arrays.stream(annotations)
                    .filter(it -> it instanceof Query)
                    .map(it -> (Query) it)
                    .findFirst().orElse(null);
            return query != null;
        }

        public static boolean isPrimitive(Type clazz) {
            return clazz instanceof Class && ((Class) clazz).isPrimitive() || (clazz == Boolean.class || clazz == Byte.class || clazz == Character.class || clazz == Short.class ||
                    clazz == Integer.class || clazz == Long.class || clazz == Float.class || clazz == Double.class) || clazz == String.class;
        }


        public static Query getQuery(Annotation[] annotations) {
            Query query = Arrays.stream(annotations)
                    .filter(it -> it instanceof Query)
                    .map(it -> (Query) it)
                    .findFirst().orElse(null);
            return query;
        }

        QueryObjectRequestConverter(ObjectMapper mapper, Query query) {
            this.mapper = mapper;
            this.query = query;
        }

        @Override
        public String convert(T value) throws IOException {
            String json = mapper.writeValueAsString(value);
            return query.encoded() ? URLUtil.encodeQuery(json) : json;
        }
    }

    static class JacksonRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

        private final ObjectWriter adapter;

        JacksonRequestBodyConverter(ObjectWriter adapter) {
            this.adapter = adapter;
        }

        @Override
        public RequestBody convert(T value) throws IOException {
            byte[] bytes = adapter.writeValueAsBytes(value);
            return RequestBody.create(MEDIA_TYPE, bytes);
        }
    }


    static class JacksonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final ObjectReader adapter;

        JacksonResponseBodyConverter(ObjectReader adapter) {
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            String data = null;
            try {

                data = value.string();
                return adapter.readValue(data);
            } catch (Exception ex) {
                log.error("json 解析异常\n" + data, ex);
                throw new IOException("json 解析异常\n" + data + ex);

            } finally {
                value.close();
            }
        }
    }

    static class DocumentResponseBodyConverter implements Converter<ResponseBody, Document> {

        DocumentResponseBodyConverter() {
        }

        public static boolean isDocumentType(Type type, Annotation[] annotations) {
            return type == Document.class;
        }

        @Override
        public Document convert(ResponseBody value) throws IOException {
            try {
                return Jsoup.parse(value.string());
            } finally {
                value.close();
            }
        }
    }

    static class DocumentParserResponseBodyConverter implements Converter<ResponseBody, Object> {


        private final JsoupParser<?> jsoupParser;

        DocumentParserResponseBodyConverter(DocumentParser documentParser, Type type) {
            Class<? extends JsoupParser<?>> jsoupParserClass = documentParser.value();

            try {
                jsoupParser = jsoupParserClass.newInstance();

                Type parserType = jsoupParser.getType();

                if (!Objects.equals(parserType, type)) {
                    throw new RuntimeException(String.format("JsoupParser class %s and methoud return type %s not matching", parserType.getTypeName(), type.getTypeName()));
                }
            } catch (InstantiationException e) {
                throw new RuntimeException("JsoupParser Instantiation error", e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }




        }

        public static boolean isDocumentParserConverter(Type type, Annotation[] annotations) {
            DocumentParser query = Arrays.stream(annotations)
                    .filter(it -> it instanceof DocumentParser)
                    .map(it -> (DocumentParser) it)
                    .findFirst().orElse(null);
            return query != null;
        }

        public static DocumentParser getDocumentParser(Annotation[] annotations) {
            DocumentParser query = Arrays.stream(annotations)
                    .filter(it -> it instanceof DocumentParser)
                    .map(it -> (DocumentParser) it)
                    .findFirst().orElse(null);
            return query;
        }

        @Override
        public Object convert(ResponseBody value) throws IOException {
            try {
                Document document = Jsoup.parse(value.string());
                try {
                    return jsoupParser.parser(document);
                } catch (Exception ex) {
                    throw new DocumentParserException(ex.getMessage(), ex, document);
                }
            } finally {
                value.close();
            }
        }
    }

    static class DocumentSelectResponseBodyConverter implements Converter<ResponseBody, Object> {
        private Select select;

        public static boolean isDocumentSelectConverter(Type type, Annotation[] annotations) {
            if (isPrimitive(type)) {
                return false;
            }
            Select query = Arrays.stream(annotations)
                    .filter(it -> it instanceof Select)
                    .map(it -> (Select) it)
                    .findFirst().orElse(null);
            return query != null;
        }

        public static Select getSelect(Annotation[] annotations) {
            Select query = Arrays.stream(annotations)
                    .filter(it -> it instanceof Select)
                    .map(it -> (Select) it)
                    .findFirst().orElse(null);
            return query;
        }

        public static boolean isPrimitive(Type clazz) {
            return clazz instanceof Class && ((Class) clazz).isPrimitive() || (clazz == Boolean.class || clazz == Byte.class || clazz == Character.class || clazz == Short.class ||
                    clazz == Integer.class || clazz == Long.class || clazz == Float.class || clazz == Double.class) || clazz == String.class;
        }


        DocumentSelectResponseBodyConverter(Select select) {
            this.select = select;
        }

        @Override
        public Object convert(ResponseBody responseBody) throws IOException {

            Document document = Jsoup.parse(responseBody.string());
            Select.SelectMethod method = select.method();
            String value = select.value();
            switch (method) {
                case ALL:
                    return document.select(value);
                case VAL:
                    return document.select(value).val();
                case HTML:
                    return document.select(value).outerHtml();
                default:
                    return document.select(value);
            }


        }
    }

    static class StringResponseBodyConverter implements Converter<ResponseBody, String> {

        StringResponseBodyConverter() {
        }

        public static boolean isStringConverter(Type type, Annotation[] annotations) {
            return type == String.class;
        }

        @Override
        public String convert(ResponseBody value) throws IOException {
            try {
                return value.string();
            } finally {
                value.close();
            }
        }
    }

}
