package com.tdj_sj_webandroid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.LocationManager;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.apkfuns.logutils.LogUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tdj_sj_webandroid.base.BaseActivity;
import com.tdj_sj_webandroid.contract.TDJContract;
import com.tdj_sj_webandroid.mvp.presenter.WebViewPresenter;
import com.tdj_sj_webandroid.utils.BitmapTools;
import com.tdj_sj_webandroid.utils.GeneralUtils;
import com.tdj_sj_webandroid.utils.GifSizeFilter;
import com.tdj_sj_webandroid.utils.IMyLocation;
import com.tdj_sj_webandroid.utils.MyGlideEngine;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class WebViewActivity extends BaseActivity<WebViewPresenter> implements IMyLocation, TDJContract.WebViewView {
    @BindView(R.id.tv_refresh)
    TextView tv_refresh;
   /* @BindView(R.id.myProgressBar)
    ProgressBar myProgressBar;*/
    @BindView(R.id.wv_program)
        SimpleWebView wv_program;
    private WebSettings settings;
    private static final int REQUEST_CODE_CHOOSE_GRIDE = 0X0002;
    private int index;

    @Override
    protected WebViewPresenter loadPresenter() {
        return new WebViewPresenter();
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        ButterKnife.bind(this);

        wv_program.addJavascriptInterface(new AndroidtoJs(), "android");//AndroidtoJS类对象映射到js的test对象
        initDetailsH5();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 初始化webView
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void initDetailsH5() {
        Map<String,String> map=new HashMap<>();
        map.put("token",GeneralUtils.getToken(getApplication()));
        if (getIntent().getStringExtra("url")!=null){
            wv_program.loadUrl(getIntent().getStringExtra("url"),map);
        }

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
        return WebViewActivity.this;
    }

    @Override
    public void uploadImage_Success(String url) {
        wv_program.loadUrl("javascript:getImageUrl('" + url + "')");

    }


    public class AndroidtoJs extends Object {
        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
            //定位



/*        @JavascriptInterface
        public void getPermission(){
                @Override
                    rxPermissions.requestEach(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION).subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) throws Exception {
                            index++;
                            LogUtils.d(index);
                            if (index==2){
                                index=0;
                                if (permission.granted) {
                                    LogUtils.d(permission.granted);
                                    wv_program.loadUrl("javascript:getPermission('" + true+ "')");
                                }else if (permission.shouldShowRequestPermissionRationale) {
                                    // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框 Log.d(TAG, permission.name + " is denied. More info should be provided.");
                                    Toast.makeText(getContext(),"必须开启定位权限才能完成打卡操作",Toast.LENGTH_LONG).show();
                                } else {
                                    // 用户拒绝了该权限，并且选中『不再询问』 Log.d(TAG, permission.name + " is denied.");
                                    Toast.makeText(getContext(),"必须开启定位权限才能完成打卡操作",Toast.LENGTH_LONG).show();

                                }
                            }

                        }
                    });
                }

        }*/


        @JavascriptInterface
        public void goback()
        {
            finish();

        }
        @JavascriptInterface
        public void backHomePage()
        {
            finish();


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
        @JavascriptInterface
        public void uploadImage() {
            //从相册中选择图片 此处使用知乎开源库Matisse
            rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean b) throws Exception {
                    Log.i("permission",b+"");
                    if (b) {
                        Matisse.from(WebViewActivity.this)
                                .choose(MimeType.ofImage())
                                .theme(R.style.Matisse_Dracula)
                                .countable(true)//true:选中后显示数字;false:选中后显示对号
                                .maxSelectable(1)
                                .capture(true)
                                .captureStrategy(new CaptureStrategy(true, "com.tdj_sj_webandroid")) //是否拍照功能，并设置拍照后图片的保存路径; FILE_PATH = 你项目的包名.fileprovider,必须配置不然会抛异常
                                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                .originalEnable(true)
                                .maxOriginalSize(10)
                                .thumbnailScale(0.85f)
                                .imageEngine(new MyGlideEngine())
                                .forResult(REQUEST_CODE_CHOOSE_GRIDE);

                    }
                }
            });




        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE_GRIDE && resultCode == RESULT_OK) {//storage/emulated/0/Pictures/JPEG_20181011_155709.jpg
            Log.e("OnActivityResult ", String.valueOf(Matisse.obtainPathResult(data).get(0)));
                mPresenter.uploadImage(BitmapTools.saveBitmap(BitmapTools.getimage(new File(Matisse.obtainPathResult(data).get(0)).getPath()), new File(Matisse.obtainPathResult(data).get(0)).getPath()));
        }
    }
    }
