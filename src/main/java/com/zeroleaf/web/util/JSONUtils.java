package com.zeroleaf.web.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by zeroleaf on 2015/5/6.
 */
public class JSONUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将一个对象转换为 对应的 JSON 表示.
     *
     * @param obj 对象.
     * @return 该对象对应的 JSON 表示.
     */
    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }
}
