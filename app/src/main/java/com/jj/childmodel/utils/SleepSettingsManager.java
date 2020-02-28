package com.jj.childmodel.utils;

public class SleepSettingsManager {
    private static SettingChangedObserver observer;

    public static void setObserver(SettingChangedObserver observer) {
        SleepSettingsManager.observer = observer;
    }

    public static void postEnableChanged(boolean isEnabled){
        if(observer == null){
            return;
        }
        observer.onEnableChanged(isEnabled);
    }

    public static void postKeepTimeChanged(int keepTime){
        if(observer == null){
            return;
        }
        observer.onKeepTimeChanged(keepTime);
    }

    public static void postSleepTimeChanged(int sleepTime){
        if(observer == null){
            return;
        }
        observer.onSleepTimeChanged(sleepTime);
    }

    public interface SettingChangedObserver{

        void onEnableChanged(boolean isEnabled);

        void onKeepTimeChanged(int keepTime);

        void onSleepTimeChanged(int sleepTime);

    }
}
