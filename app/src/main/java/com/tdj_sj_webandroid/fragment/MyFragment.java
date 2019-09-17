package com.tdj_sj_webandroid.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.tdj_sj_webandroid.AppAplication;
import com.tdj_sj_webandroid.MainTabActivity;
import com.tdj_sj_webandroid.MianActivity;
import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.SimpleWebView;
import com.tdj_sj_webandroid.WebViewActivity;
import com.tdj_sj_webandroid.base.BaseFrgment;
import com.tdj_sj_webandroid.mvp.presenter.IPresenter;
import com.tdj_sj_webandroid.utils.Constants;
import com.tdj_sj_webandroid.utils.GeneralUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFragment extends BaseFrgment {
    @BindView(R.id.tv_refresh)
    TextView tv_refresh;
  /*  @BindView(R.id.myProgressBar)
    ProgressBar myProgressBar;*/
    @BindView(R.id.wv_program)
    SimpleWebView wv_program;
    private WebSettings settings;
    private MainTabActivity activity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainTabActivity) context;
    }
    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        wv_program.addJavascriptInterface(new AndroidtoJs(), "android");//AndroidtoJS类对象映射到js的test对象
        initDetailsH5();
    }
    /**
     * 当界面重新展示时（fragment.show）,调用onrequest刷新界面
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        // TODO Auto-generated method stub
        super.onHiddenChanged(hidden);
        if (!hidden) {
            //重置数据
            initDetailsH5();
        }
    }
    /**
     * 初始化webView
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void initDetailsH5() {
        Map<String,String> map=new HashMap<>();
        map.put("token", GeneralUtils.getToken(getContext()));
            wv_program.loadUrl(Constants.URL+Constants.mine_do,map);
        LogUtils.d(Constants.URL+Constants.mine_do);
        wv_program.setWebViewClient(new SimpleWebView.SimpleWebViewClient() {
    /*        @Override
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
    protected void loadData() {

    }

    @Override
    protected IPresenter loadPresenter() {
        return null;
    }

    @Override
    protected int getContentId() {
        return R.layout.my_layout;
    }

    public class AndroidtoJs extends Object {
        @JavascriptInterface
        public void quitLogin()
        {
            GeneralUtils.removeToken(AppAplication.getAppContext());
            Intent intent = new Intent(getContext(), MianActivity.class);
           startActivity(intent);


        }
        @JavascriptInterface
        public void backHomePage()
        {
            activity.setTabSelection(0);


        }
        @JavascriptInterface
        public void changePassword()
        {
            GeneralUtils.removeToken(AppAplication.getAppContext());
            Intent intent = new Intent(getContext(), MianActivity.class);
            startActivity(intent);



        }
        @JavascriptInterface
        public void passwordChange(String url)
        {
            Intent intent=new Intent(getContext(), WebViewActivity.class);
            intent.putExtra("url", Constants.URL1+url);
            startActivity(intent);



        }
        @JavascriptInterface
        public void  startDepart(String url){
            LogUtils.d(url);
            Intent intent=new Intent(getContext(), WebViewActivity.class);
            intent.putExtra("url", Constants.URL1+url);
            startActivity(intent);


        }

    }
}
