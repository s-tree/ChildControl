package com.jj.childmodel.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextClock;
import android.widget.TextView;

import com.jj.childmodel.ChildApplication;
import com.jj.childmodel.R;
import com.jj.childmodel.utils.SPUtil;
import com.jj.childmodel.utils.ScreenUtil;
import com.jj.childmodel.utils.TimeUtil;

public class LockDialog extends Dialog {
    private static LockDialog instance;
    public static void showDialog(){
        if(instance == null){
            instance = new LockDialog(ChildApplication.instance);
        }
        if(instance.isShowing()){
            return;
        }
        instance.show();
    }

    private View blackView,bgView;
    private TextClock textClock;
    private TextView time;

    public LockDialog(@NonNull Context context) {
        super(context);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View v = LayoutInflater.from(ChildApplication.instance).inflate(R.layout.layout_activity_dream,null);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
        layoutParams.width = ScreenUtil.getScreenWidth();
        layoutParams.height = ScreenUtil.getScreenHeight();
        setContentView(v,layoutParams);
        setTitle("");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        }else{
            getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }

        setCancelable(false);
        setCanceledOnTouchOutside(false);

        blackView = findViewById(R.id.rootLayout);
        bgView = findViewById(R.id.bgView);
        time = findViewById(R.id.time);
        textClock = findViewById(R.id.textClock);
        textClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChildApplication.keepedMinutes = 0;
                dismiss();
            }
        });

    }

    @Override
    public void show() {
        super.show();
        showAni();
        MyCusTimer myCusTimer = new MyCusTimer(SPUtil.getSleepTime() * 60 * 1000,1000);
        myCusTimer.start();
    }

    private void showAni(){
        AlphaAnimation animation = new AlphaAnimation(0f,1f);
        animation.setDuration(2000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.w("test_bug","onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.w("test_bug","onAnimationEnd");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        blackView.startAnimation(animation);
    }

    class MyCusTimer extends CountDownTimer {

        private MyCusTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            time.setText(TimeUtil.formatTime(millisUntilFinished));
        }

        @Override
        public void onFinish() {
            this.cancel();
            dismiss();
        }
    }
}
