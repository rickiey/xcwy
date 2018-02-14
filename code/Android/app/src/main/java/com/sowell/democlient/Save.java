package com.sowell.democlient;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zxd on 2017/8/5.
 */

public class Save {

    public static boolean saveUserInfo(Context context, String number, String password) {
        SharedPreferences sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("mobile", number);
        edit.putString("password", password);
        edit.commit();
        return true;
    }

    public static Map<String, String> getUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String mobile = sp.getString("mobile", null);
        String password = sp.getString("password", null);
        Map<String, String> userMap = new HashMap<String, String>();
        userMap.put("mobile", mobile);
        userMap.put("password", password);
        return userMap;
    }

    public static boolean saveLognInfo(Context context, String number, String password) {
        SharedPreferences sp = context.getSharedPreferences("data2", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("mobile", number);
        edit.putString("password", password);
        edit.commit();
        return true;
    }

    public static Map<String, String> getLoginInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("data2", Context.MODE_PRIVATE);
        String mobile = sp.getString("mobile", null);
        String password = sp.getString("password", null);
        Map<String, String> userMap = new HashMap<String, String>();
        userMap.put("mobile", mobile);
        userMap.put("password", password);
        return userMap;
    }

}
