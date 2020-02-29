package com.jj.childmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import com.jj.childmodel.service.BackgroundControlService;

import java.util.LinkedList;

public class ChildApplication extends Application implements Application.ActivityLifecycleCallbacks{
    public static Application instance;
    public static long keepedMinutes = 0;
    public LinkedList<Activity> activities;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        activities = new LinkedList<>();
        startService(new Intent(this, BackgroundControlService.class));
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activities.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activities.remove(activity);
    }
}
