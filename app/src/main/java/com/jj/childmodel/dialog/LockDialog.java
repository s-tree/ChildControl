package com.jj.childmodel.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextClock;

import com.jj.childmodel.ChildApplication;
import com.jj.childmodel.R;
import com.jj.childmodel.utils.ScreenUtil;

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

    private View blackView;
    private TextClock textClock;

    public LockDialog(@NonNull Context context) {
        super(context);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = ScreenUtil.getScreenWidth();
        params.height = ScreenUtil.getScreenHeight();

        setContentView(R.layout.layout_activity_dream);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        }else{
            getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }

        setCancelable(false);
        setCanceledOnTouchOutside(false);

        blackView = findViewById(R.id.rootLayout);
        textClock = findViewById(R.id.textClock);
        textClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChildApplication.keepedMinutes = 0;
                dismiss();
            }
        });

        showAni();
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
}
