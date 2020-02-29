package com.jj.childmodel.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.jj.childmodel.ChildApplication;
import com.jj.childmodel.R;

public class NotificationUtil {

    private static final int notificationId = 0x11;

    public static void makeNotication(){
        NotificationCompat.Builder compatBuilder = new NotificationCompat.Builder(ChildApplication.instance);

        compatBuilder.setChannelId(ChildApplication.instance.getPackageName());
        compatBuilder.setContentTitle("ChildModel is running");
        compatBuilder.setLargeIcon(BitmapFactory.decodeResource(ChildApplication.instance.getResources(), R.mipmap.ic_launcher));
        compatBuilder.setSmallIcon(R.mipmap.ic_launcher);
        compatBuilder.setOngoing(true);

        Notification notification = compatBuilder.build();
        NotificationManager manager = (NotificationManager) ChildApplication.instance.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notificationId,notification);
    }

    public static void cancelNnotification(){
        NotificationManager manager = (NotificationManager) ChildApplication.instance.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notificationId);
    }
}
