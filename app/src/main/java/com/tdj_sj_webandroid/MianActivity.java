package com.tdj_sj_webandroid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tdj_sj_webandroid.base.BaseActivity;
import com.tdj_sj_webandroid.mvp.presenter.IPresenter;
import com.tdj_sj_webandroid.utils.Constants;
import com.tdj_sj_webandroid.utils.GeneralUtils;
import com.tdj_sj_webandroid.utils.IMyLocation;
import com.tdj_sj_webandroid.utils.MyLocationManager;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class MianActivity extends BaseActivity implements IMyLocation {
    @BindView(R.id.tv_refresh)
    TextView tv_refresh;
  /*  @BindView(R.id.myProgressBar)
    ProgressBar myProgressBar;*/
    @BindView(R.id.wv_program)
    SimpleWebView wv_program;
    private WebSettings settings;
    private MyLocationManager manager;

    @Override
    protected IPresenter loadPresenter() {
        return null;
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);


        ImmersionBar.with(this).statusBarDarkFont(true) .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                .init();
//        rxPermissions = new RxPermissions(this);

        manager = new MyLocationManager(this);

        wv_program.addJavascriptInterface(new AndroidtoJs(), "android");//AndroidtoJS类对象映射
        initDetailsH5();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mains;
    }

    /**
     * 初始化webView
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void initDetailsH5() {
        wv_program.loadUrl(Constants.URL+Constants.login);
        wv_program.setWebViewClient(new SimpleWebView.SimpleWebViewClient() {
/*            @Override
            public void onPageFinished(com.tencent.smtt.sdk.WebView webView, String url) {
                super.onPageFinished(webView, url);
                myProgressBar.setVisibility(View.GONE);
//                toolbarTitle.setText(webView.getTitle());//获取WebView 的标题，设置到toolbar中去
            }*/

            @Override
            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView webView, String url) {
                if (url.contains("Activity/")) {

                } else if (url.contains("Share")) {

                } else {
                    webView.loadUrl(url);
                }
                return true;
            }

        });
/*        wv_program.setWebChromeClient(new SimpleWebView.SimpleWebChromeClient() {
            @Override
            public void onProgressChanged(com.tencent.smtt.sdk.WebView webView, int newProgress) {
                if (newProgress == 100) {
                    myProgressBar.setVisibility(View.GONE);
                } else {
                    if (View.GONE == myProgressBar.getVisibility()) {
                        myProgressBar.setVisibility(View.VISIBLE);
                    }
                    myProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(webView, newProgress);
            }

        });*/
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (wv_program.canGoBack())
                wv_program.goBack();
            else
                finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public LocationManager getLocationManager() {
        return (LocationManager) (getSystemService(LOCATION_SERVICE));
    }

    @Override
    public Context getContext() {
        return MianActivity.this;
    }

    public class AndroidtoJs extends Object {

        @JavascriptInterface
        public void toIndex(String Token){
            LogUtils.d(Token);
            try {
                JSONObject jsonObject=new JSONObject(Token);
                if (jsonObject.getInt("err")==200){
                    GeneralUtils.setToken(getApplication(),jsonObject.getString("data"));
                 Intent intent = new Intent(MianActivity.this, MainTabActivity.class);
                startActivity(intent);
                 finish();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }



        }

        @JavascriptInterface
        public void quitLogin()
        {
            GeneralUtils.removeToken(AppAplication.getAppContext());
            wv_program.goBack();

        }
    }

}
