package com.jj.childmodel;

import android.app.Application;

public class ChildApplication extends Application {
    public static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
