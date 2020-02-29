package com.jj.childmodel.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jj.childmodel.ChildApplication;
import com.jj.childmodel.DreamActivity;
import com.jj.childmodel.bean.WhiteTimeBean;
import com.jj.childmodel.orm.DBManager;
import com.jj.childmodel.utils.NotificationUtil;
import com.jj.childmodel.utils.SPUtil;
import com.jj.childmodel.utils.TimeUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class BackgroundControlService extends Service {
    private Disposable disposable;

    @Override
    public void onCreate() {
        super.onCreate();
        startListener();
        NotificationUtil.makeNotication();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startListener(){
        if(disposable != null && !disposable.isDisposed()){
            return;
        }
        disposable = Observable.interval(1, TimeUnit.MINUTES)
                .map(new Function<Long, Boolean>() {
                    @Override
                    public Boolean apply(Long aLong) throws Exception {
                        if(!SPUtil.isEnable()){
                            return true;
                        }
                        if(checkInWhite()){
                            return true;
                        }
                        if(checkKeepTime()){
                            return true;
                        }
                        return false;
                    }
                })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if(aBoolean){
                            return;
                        }
                        DreamActivity.startDreamActivity();
                    }
                });
    }

    private boolean checkInWhite(){
        List<WhiteTimeBean> whiteTimeBeans = DBManager.getWhitelistBeans();
        if(whiteTimeBeans.isEmpty()){
            return false;
        }
        for(WhiteTimeBean whiteTimeBean : whiteTimeBeans){
            if(TimeUtil.isInTime(whiteTimeBean.startHour,whiteTimeBean.startMinute,whiteTimeBean.endHour,whiteTimeBean.endMinute)){
                return true;
            }
        }
        return false;
    }

    private boolean checkKeepTime(){
        return ChildApplication.keepedMinutes < SPUtil.getKeepTime();
    }
}
