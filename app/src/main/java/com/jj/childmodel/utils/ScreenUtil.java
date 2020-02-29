package com.jj.childmodel.utils;

import com.jj.childmodel.ChildApplication;

public class ScreenUtil {

    public static int getScreenWidth(){
        return ChildApplication.instance.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(){
        return ChildApplication.instance.getResources().getDisplayMetrics().heightPixels;
    }
}
