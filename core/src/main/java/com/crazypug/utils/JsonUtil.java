package com.crazypug.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.util.HashMap;

public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    }

    public static void setObjectMapper(ObjectMapper objectMapper) {
        JsonUtil.objectMapper = objectMapper;


    }

    @SneakyThrows
    public static String writeValueAsString(Object content) {
        return objectMapper.writeValueAsString(content);
    }

    @SneakyThrows
    public static String writePrettyString(Object content) {
        return objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(content);
    }

    @SneakyThrows
    public static String writeValueAsStringPart(Object key, Object value) {
        HashMap<Object, Object> data = new HashMap<>();
        data.put(key, value);
        return objectMapper.writeValueAsString(data);
    }


    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @SneakyThrows
    public static JsonNode readTree(String content) {
        return objectMapper.readTree(content);
    }

    @SneakyThrows
    public static <T> T readValue(String content, Class<T> valueType) {
        return objectMapper.readValue(content, valueType);
    }

    @SneakyThrows
    public static <T> T readValue(String content, TypeReference<T> typeReference) {
        return objectMapper.readValue(content, typeReference);
    }
}
