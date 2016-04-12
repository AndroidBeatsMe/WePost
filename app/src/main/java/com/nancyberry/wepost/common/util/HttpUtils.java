package com.nancyberry.wepost.common.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by nan.zhang on 4/12/16.
 */
public class HttpUtils {

    private static Gson gson;
    private static Type type;

    static {
        gson = new Gson();
        type = new TypeToken<Map<String, String>>(){}.getType();
    }

    public static Map<String, String> pojoToMap(Object o) {
        return gson.fromJson(gson.toJson(o), type);
    }
}
