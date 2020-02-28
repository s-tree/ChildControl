package com.jj.childmodel.utils;

import android.text.TextUtils;

public class TimeUtil {

    public static String getTimeString(int hour,int minute){
        return getDoubleTime(hour) + ":" + getDoubleTime(minute);
    }

    public static String getDoubleTime(int time){
        if(time < 10){
            return TextUtils.concat("0",String.valueOf(time)).toString();
        }
        return String.valueOf(time);
    }
}
