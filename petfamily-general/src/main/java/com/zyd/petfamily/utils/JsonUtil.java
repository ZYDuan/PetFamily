package com.zyd.petfamily.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @program: petfamily
 * @author: zyd
 * @description: json字符串跟对象的相互转换
 * @create: 2019-04-28 15:08
 */
public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 将json字符串转化为对象
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null)
            return null;
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toJson(Object object){
        if (object == null)
            return null;
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
