package com.tdj_sj_webandroid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.lxj.matisse.CaptureMode;
import com.lxj.matisse.Matisse;
import com.lxj.matisse.MimeType;
import com.lxj.matisse.filter.Filter;
import com.tdj_sj_webandroid.base.BaseActivity;
import com.tdj_sj_webandroid.contract.TDJContract;
import com.tdj_sj_webandroid.model.BackHomePage;
import com.tdj_sj_webandroid.model.ConfirmPlan;
import com.tdj_sj_webandroid.model.LocationBean;
import com.tdj_sj_webandroid.model.Resume;
import com.tdj_sj_webandroid.mvp.presenter.WebViewToPresenter;
import com.tdj_sj_webandroid.utils.BitmapTools;
import com.tdj_sj_webandroid.utils.Constants;
import com.tdj_sj_webandroid.utils.GeneralUtils;
import com.tdj_sj_webandroid.utils.GifSizeFilter;
import com.tdj_sj_webandroid.utils.IMyLocation;
import com.tdj_sj_webandroid.utils.LocationUtils;
import com.tdj_sj_webandroid.utils.MyGlideEngine;
import com.tdj_sj_webandroid.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class WebViewToActivity extends BaseActivity<WebViewToPresenter> implements IMyLocation, TDJContract.WebViewView{
    @BindView(R.id.tv_refresh)
    TextView tv_refresh;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.title)
    RelativeLayout title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.tv_title)
    TextView tv_title;

   /* @BindView(R.id.myProgressBar)
    ProgressBar myProgressBar;*/
    @BindView(R.id.wv_program)
        SimpleWebView wv_program;
    private WebSettings settings;
    private static final int REQUEST_CODE_CHOOSE_GRIDE = 0X0002;
    private static final int REQUEST_CODE_CHOOSE = 0X0001;
    private int index;
    private String urls;

    @Override
    protected WebViewToPresenter loadPresenter() {
        return new WebViewToPresenter();
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        isFirstGetLocation = true;
        registerEventBus(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterEventBus(this);
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        ButterKnife.bind(this);
        wv_program.addJavascriptInterface(new AndroidtoJs(), "android");//AndroidtoJS类对象映射到js的test对象
        initDetailsH5();

        if (getIntent().getStringExtra("title")!=null){
            view.setVisibility(View.VISIBLE);
            tv_title.setText(getIntent().getStringExtra("title"));
            title.setVisibility(View.VISIBLE);
            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
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
            @Override
            public void onPageFinished(com.tencent.smtt.sdk.WebView webView, String url) {
                super.onPageFinished(webView, url);
                urls=url;
                LogUtils.e(url);//https://siji.51taodj.com/test-driver/driverScann/center.do
//                toolbarTitle.setText(webView.getTitle());//获取WebView 的标题，设置到toolbar中去
            }

            @Override
            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView webView, String url) {
                LogUtils.e(url+"2222222");

                if (url.contains("Activity/")) {

                } else if (url.contains("Share")) {

                } else {
                    webView.loadUrl(url);
                }
                return true;
            }

        });

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

    /*code 不同事件接受處理*/
    @Subscribe( threadMode = ThreadMode.MAIN)
    public void Resume(Resume resume) {
        Map<String,String> map=new HashMap<>();
        map.put("token",GeneralUtils.getToken(getApplication()));
        LogUtils.e(GeneralUtils.isNullOrZeroLenght(urls));
        if (!GeneralUtils.isNullOrZeroLenght(urls)){
            wv_program.loadUrl(urls,map);
        }
    }

    @Override
    public Context getContext() {
        return WebViewToActivity.this;
    }

    @Override
    public void uploadImage_Success(String url) {
        wv_program.loadUrl("javascript:getImageUrl('" + url + "')");

    }

    private boolean isFirstGetLocation = true;


    public class AndroidtoJs extends Object {

        @JavascriptInterface
        public void goback() {
            finish();
        }

        @JavascriptInterface
        public void backHomePage() {
            finish();
        }

        @JavascriptInterface
        public void check(String id,String customer_code,String type)
        {
            LogUtils.e(id);
            Intent intent;
            if (Build.MODEL.equals("NLS-MT90")){
                intent = new Intent(getContext(), NuclearGoodsActivity.class);
           }else {
                intent = new Intent(getContext(), DDJNuclearGoodsActivity.class);
            }
            intent.putExtra("customer_id",id);
            intent.putExtra("customer_code",customer_code);
            intent.putExtra("type",Integer.valueOf(type));
            startActivity(intent);

        }
        @JavascriptInterface
        public void telephone(String phone) {
            LogUtils.d(phone);
            Intent intent_service = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + phone);
            intent_service.setData(data);
            startActivity(intent_service);
        }

        @JavascriptInterface
        public void backHomePage(int index) {
//            MainTabActivity.lunchMainTabAc(WebViewActivity.this,index);
            EventBus.getDefault().post(new BackHomePage(index));
            finish();
        }

        @JavascriptInterface
        public void refreshPage() {
            finish();
            initDetailsH5();
        }

        @JavascriptInterface
        public void confirmPlan(int index) {
            EventBus.getDefault().post(new ConfirmPlan(index,true));
//            MainTabActivity.lunchMainTabAc(WebViewActivity.this,index);
            finish();
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
        public void getLocationBack() {
            try {
                if (isFirstGetLocation) {
                    LogUtils.i( "onMapEvent: 进方法了");
                    LocationUtils.getInstance().stopLocalService();
                    getPermissions(true,true);
                    isFirstGetLocation = false;
                }
            }catch (Exception e){
                e.printStackTrace();
                ToastUtils.showToast(WebViewToActivity.this,e.getMessage());
            }

        }

        @JavascriptInterface
        public void redirectPage(String url){
            WebViewToActivity.this.finish();
            LocationUtils.getInstance().stopLocalService();
            Intent intent=new Intent(getContext(), WebViewActivity.class);
            intent.putExtra("url", Constants.URL1+ url);
            startActivity(intent);
        }

        @JavascriptInterface
        public void takePhoto() {
            Matisse.from(WebViewToActivity.this)
//                    .jumpCapture()//直接跳拍摄，默认可以同时拍摄照片和视频
                    .jumpCapture(CaptureMode.Image)//只拍照片
                    //.jumpCapture(CaptureMode.Video)//只拍视频
                    .isCrop(false) //开启裁剪
                    .forResult(REQUEST_CODE_CHOOSE);
        }


        @JavascriptInterface
        public void uploadImage() {
            //从相册中选择图片 此处使用知乎开源库Matisse
            rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean b) throws Exception {
                    Log.i("permission",b+"");
                    if (b) {
                        Matisse.from(WebViewToActivity.this)
                                .choose(MimeType.ofImage())
                                .theme(R.style.Matisse_Dracula)
                                .countable(true)//true:选中后显示数字;false:选中后显示对号
                                .maxSelectable(1)
                                .capture(true)
//                                .captureStrategy(new CaptureStrategy(true, "com.tdj_sj_webandroid.fileProvider")) //是否拍照功能，并设置拍照后图片的保存路径; FILE_PATH = 你项目的包名.fileprovider,必须配置不然会抛异常
                                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                .originalEnable(true)
                                .maxOriginalSize(10)
                                .thumbnailScale(0.85f)
                                .imageEngine(new MyGlideEngine())
                                .forResult(REQUEST_CODE_CHOOSE);

                    }
                }
            });
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            //获取拍摄的图片路径，如果是录制视频则是视频的第一帧图片路径
            String captureImagePath = Matisse.obtainCaptureImageResult(data);
            if (captureImagePath != null && !captureImagePath.isEmpty()){
                mPresenter.uploadImage(BitmapTools.saveBitmap(BitmapTools.getimage(new File(captureImagePath).getPath()), new File(captureImagePath).getPath()));
            }else {
                //获取选择图片或者视频的结果路径，如果开启裁剪的话，获取的是原图的地址
                List<String> list = Matisse.obtainSelectPathResult(data);//文件形式路径
                mPresenter.uploadImage(BitmapTools.saveBitmap(BitmapTools.getimage(new File(list.get(0)).getPath()), new File(list.get(0)).getPath()));
            }

        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMapEvent(LocationBean locationBean){
        double latitude = locationBean.getLatitude();
        double longitude = locationBean.getLongitude();
        if (locationBean.isBack() && locationBean.isWbeView()) {
            wv_program.post(new Runnable() {
                @Override
                public void run() {
                    wv_program.loadUrl("javascript:getLocation(\"" + (longitude) + "\",\"" + (latitude) + "\")");
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wv_program != null){
            ViewParent parent = wv_program.getParent();
            if (parent != null){
                ((ViewGroup)parent).removeView(wv_program);
            }
            wv_program.stopLoading();
            //退出时调用此方法，移除绑定的服务，否则某些特定系统会报错mSearchWebView.getSettings().setJavaScriptEnabled(false);

            wv_program.clearHistory();

            wv_program.clearView();

            wv_program.removeAllViews();

            try{
                wv_program.destroy();

            }catch(Throwable ex) {

                ex.printStackTrace();

            }

        }

    }
}
