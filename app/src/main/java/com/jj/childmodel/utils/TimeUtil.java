package com.jj.childmodel.utils;

import android.text.TextUtils;

import java.util.Calendar;

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

    /**
     * 当前时间是否在白名单时段中
     */
    public static boolean isInTime(int startHour,int startMinute,int endHour,int endMinute){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return isAfterTime(hour,minute,startHour,startMinute)
                && isBeforeTime(hour,minute,endHour,endMinute);
    }

    /**
     * 在 tag 时间之前
     */
    public static boolean isBeforeTime(int hour,int minute,int tagHour,int tagMinute){
        if(hour > tagHour){
            return false;
        }
        if(hour == tagHour){
            return minute <= tagMinute;
        }
        else {
            return true;
        }
    }

    /**
     * 在 tag 时间之后
     */
    public static boolean isAfterTime(int hour,int minute,int tagHour,int tagMinute){
        if(hour < tagHour){
            return false;
        }
        if(hour == tagHour){
            return minute >= tagMinute;
        }
        else {
            return true;
        }
    }

    private static final StringBuilder timeStr = new StringBuilder();
    public static String formatTime(long time) {
        if (!TextUtils.isEmpty(timeStr.toString())) {
            timeStr.replace(0, timeStr.length(), "");
        }
        time /= 1000;
        if (time <= 0) {
            return "00:01";
        }
        long minute = time / 60;
        if (minute < 10) {
            timeStr.append("0");
        }
        timeStr.append(minute);
        timeStr.append(":");
        long second = time - (minute * 60);

        if (second < 10) {
            timeStr.append("0");
        }
        timeStr.append(second);
        return timeStr.toString();
    }
}
