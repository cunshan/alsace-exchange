package com.alsace.exchange.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * @author: wangwei
 * @Description:
 * @Date: Created in 10:23 上午 2020/8/18
 * @Modified by:
 */
public class JsonUtils {

    private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public JsonUtils() {}

    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    public static <T> T fromJson(String jsonStr, Class<T> clazz) {
        return GSON.fromJson(jsonStr, clazz);
    }

    public static <T> T fromJson(String jsonStr, Type type) {
        return GSON.fromJson(jsonStr, type);
    }
}
