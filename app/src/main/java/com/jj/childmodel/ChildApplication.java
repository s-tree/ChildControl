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
    public static boolean isForground = false;
    public LinkedList<Activity> activities;
    public LinkedList<Activity> foregroundActivities;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        activities = new LinkedList<>();
        foregroundActivities = new LinkedList<>();
        registerActivityLifecycleCallbacks(this);
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
        foregroundActivities.add(activity);
        keepedMinutes = 0;
        isForground = true;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        foregroundActivities.remove(activity);
        isForground = false;
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
