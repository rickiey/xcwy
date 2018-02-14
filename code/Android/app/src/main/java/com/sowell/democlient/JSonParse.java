package com.sowell.democlient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Administrator on 2017/7/29.
 */

//解析json对象

public class JSonParse {

    public static List<JsonInfo> getInfosFromJSON(String is)
            throws IOException {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<JsonInfo>>() {
        }.getType();
        List<JsonInfo> services = gson.fromJson(is, listType);
        return services;
    }
}
