package com.tdj_sj_webandroid;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.tdj_sj_webandroid.utils.GeneralUtils;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2018/5/4.
 */

public class SplashActivity extends AppCompatActivity {
    public Timer timer = new Timer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);//解决启动白频；
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                if (!GeneralUtils.isNullOrZeroLenght(GeneralUtils.getToken(SplashActivity.this))){
                    Intent intent = new Intent(SplashActivity.this, MainTabActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent=new Intent(SplashActivity.this,MianActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        };
        timer.schedule(task, 3 * 1000 + 200);
    }


}
