package com.tdj_sj_webandroid;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tdj_sj_webandroid.utils.Constants;
import com.tdj_sj_webandroid.utils.Density;
import com.tdj_sj_webandroid.utils.GeneralUtils;
import com.tencent.smtt.sdk.QbSdk;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.converter.SerializableDiskConverter;

public class AppAplication extends Application {
    private static Application app = null;
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        Density.setDensity(this);
        app = this;
        initX5();
        EasyHttp.init(this);
        //这里涉及到安全我把url去掉了，demo都是调试通的
        EasyHttp.getInstance()
                .debug("RxEasyHttp", true)
                .setReadTimeOut(60 * 1000)
                .setWriteTimeOut(60 * 1000)
                .setConnectTimeout(60 * 1000)
                .setRetryCount(3)//默认网络不好自动重试3次
                .setRetryDelay(500)//每次延时500ms重试
                .setRetryIncreaseDelay(500)//每次延时叠加500ms
                .setBaseUrl(Constants.URL).setCertificates()
                .setCacheDiskConverter(new SerializableDiskConverter())//默认缓存使用序列化转化
                .setCacheMaxSize(50 * 1024 * 1024)//设置缓存大小为50M
                .setCacheVersion(1);//缓存版本为1




    }
    /**
     * 获取Application的Context
     **/
    public static Context getAppContext() {
        if (app == null)
            return null;
        return app.getApplicationContext();
    }
    private void initX5() {
        QbSdk.setDownloadWithoutWifi(true);
        //x5内核初始化接口//搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.initX5Environment(getApplicationContext(),  new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }
            @Override
            public void onCoreInitFinished() {
            }
        });
    }
}
