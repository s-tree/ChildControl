package com.jj.childmodel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextClock;

public class DreamActivity extends AppCompatActivity {
    public static void startDreamActivity(){
        Intent intent = new Intent(ChildApplication.instance,DreamActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ChildApplication.instance.startActivity(intent);
    }

    private View blackView;
    private TextClock textClock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_activity_dream);

        blackView = findViewById(R.id.rootLayout);
        textClock = findViewById(R.id.textClock);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
