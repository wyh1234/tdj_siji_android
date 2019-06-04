package com.tdj_sj_webandroid.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.tdj_sj_webandroid.MainTabActivity;
import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.SimpleWebView;
import com.tdj_sj_webandroid.WebViewActivity;
import com.tdj_sj_webandroid.base.BaseFrgment;
import com.tdj_sj_webandroid.mvp.presenter.IPresenter;
import com.tdj_sj_webandroid.utils.Constants;
import com.tdj_sj_webandroid.utils.GeneralUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PsFragment extends BaseFrgment {
    @BindView(R.id.tv_refresh)
    TextView tv_refresh;
    @BindView(R.id.myProgressBar)
    ProgressBar myProgressBar;
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
        wv_program.loadUrl(Constants.URL+Constants.task,map);
        LogUtils.d(Constants.URL+Constants.task);
        wv_program.setWebViewClient(new SimpleWebView.SimpleWebViewClient() {
            @Override
            public void onPageFinished(com.tencent.smtt.sdk.WebView webView, String url) {
                super.onPageFinished(webView, url);
                myProgressBar.setVisibility(View.GONE);
//                toolbarTitle.setText(webView.getTitle());//获取WebView 的标题，设置到toolbar中去
            }

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
        wv_program.setWebChromeClient(new SimpleWebView.SimpleWebChromeClient() {
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

        });
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
        return R.layout.activity_main;
    }
    public class AndroidtoJs extends Object {
        @JavascriptInterface
        public void backHomePage()
        {
            activity.setTabSelection(0);


        }
        @JavascriptInterface
        public void telephone(String phone)
        {
            LogUtils.d(phone);

            Intent intent_service = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + phone);
            intent_service.setData(data);
            startActivity(intent_service);
        }




        @JavascriptInterface
        public void mapNavi(String jsonObject)
        {
            LogUtils.d(jsonObject);

            try {
                JSONObject jsonObject1=new JSONObject(jsonObject);
                GeneralUtils.goToGaodeMap(getContext(),jsonObject1.getDouble("lat"),jsonObject1.getDouble("lng"),jsonObject1.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }




        }


    }
}
