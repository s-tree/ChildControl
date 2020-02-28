package com.jj.childmodel.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.jj.childmodel.ChildApplication;

public class SPUtil {

    private static final SharedPreferences instance = ChildApplication.instance.getSharedPreferences("app", Context.MODE_PRIVATE);

    private static final String KEY_ENABLE = "KEY_ENABLE";

    private static final String KEY_KEEP_TIME = "KEY_KEEP_TIME";

    private static final String KEY_SLEEP_TIME = "KEY_SLEEP_TIME";

    public static void setEnable(boolean enable){
        instance.edit().putBoolean(KEY_ENABLE,enable).apply();
    }

    public static boolean isEnable(){
        return instance.getBoolean(KEY_ENABLE,false);
    }

    public static void setKeepTime(int time){
        instance.edit().putInt(KEY_KEEP_TIME,time).apply();
    }

    public static int getKeepTime(){
        return instance.getInt(KEY_KEEP_TIME,0);
    }

    public static void setSleepTime(int time){
        instance.edit().putInt(KEY_SLEEP_TIME,time).apply();
    }

    public static int getSleepTime(){
        return instance.getInt(KEY_SLEEP_TIME,0);
    }
}
