package com.tdj_sj_webandroid.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.lxj.matisse.CaptureMode;
import com.lxj.matisse.Matisse;
import com.lxj.matisse.MimeType;
import com.lxj.matisse.filter.Filter;
import com.tdj_sj_webandroid.AppAplication;
import com.tdj_sj_webandroid.MainTabActivity;
import com.tdj_sj_webandroid.MianActivity;
import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.SimpleWebView;
import com.tdj_sj_webandroid.WebViewActivity;
import com.tdj_sj_webandroid.base.BaseFrgment;
import com.tdj_sj_webandroid.http.HttpUtils;
import com.tdj_sj_webandroid.http.JsonResponseHandler;
import com.tdj_sj_webandroid.model.LocationBean;
import com.tdj_sj_webandroid.mvp.presenter.IPresenter;
import com.tdj_sj_webandroid.utils.BitmapTools;
import com.tdj_sj_webandroid.utils.Constants;
import com.tdj_sj_webandroid.utils.GeneralUtils;
import com.tdj_sj_webandroid.utils.GifSizeFilter;
import com.tdj_sj_webandroid.utils.LocationUtils;
import com.tdj_sj_webandroid.utils.MyGlideEngine;
import com.tdj_sj_webandroid.utils.ToastUtils;
import com.zhouyou.http.exception.ApiException;

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

import static android.app.Activity.RESULT_OK;

public class MapFragment extends BaseFrgment {

    @BindView(R.id.tv_refresh)
    TextView tv_refresh;
    @BindView(R.id.wv_program)
    SimpleWebView wv_program;
    private MainTabActivity activity;
    private static final int REQUEST_CODE_CHOOSE = 0X0001;

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

    @Override
    protected void loadData() {

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

        isFirstGetLocation = true;
    }

    /**
     * 初始化webView
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void initDetailsH5() {
        Map<String,String> map=new HashMap<>();
        map.put("token", GeneralUtils.getToken(getContext()));
        wv_program.loadUrl(Constants.URL+Constants.map_do,map);
        LogUtils.d(Constants.URL+Constants.map_do);
        wv_program.setWebViewClient(new SimpleWebView.SimpleWebViewClient() {
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
    }

    private boolean isFirstGetLocation = true;


    @Override
    protected IPresenter loadPresenter() {
        return null;
    }

    @Override
    protected int getContentId() {
        return R.layout.map_fragment;
    }

    public class AndroidtoJs extends Object {
        @JavascriptInterface
        public void quitLogin() {
            GeneralUtils.removeToken(AppAplication.getAppContext());
            Intent intent = new Intent(getContext(), MianActivity.class);
            startActivity(intent);
        }

        @JavascriptInterface
        public void backHomePage() {
            activity.setTabSelection(0);
        }

        @JavascriptInterface
        public void redirectPage(String url){
            Intent intent=new Intent(getContext(), WebViewActivity.class);
            intent.putExtra("url", Constants.URL1+ url);
            startActivity(intent);
        }

        @JavascriptInterface
        public void takePhoto() {
            Matisse.from(MapFragment.this)
//                    .jumpCapture()//直接跳拍摄，默认可以同时拍摄照片和视频
                    .jumpCapture(CaptureMode.Image)//只拍照片
                    //.jumpCapture(CaptureMode.Video)//只拍视频
                    .isCrop(false) //开启裁剪
                    .forResult(REQUEST_CODE_CHOOSE);
        }

        @JavascriptInterface
        public String getLocation() {
            LogUtils.d("getLocation");
            activity.getPermissions(false,false);
            return Constants.latitude + "|" + Constants.longtitude;
        }

        @JavascriptInterface
        public void getLocationBack() {
            try {
                if (isFirstGetLocation) {
                    LocationUtils.getInstance().stopLocalService();
                    activity.getPermissions(true,false);
                    isFirstGetLocation = false;
                }

            }catch (Exception e){
                e.printStackTrace();
                ToastUtils.showToast(activity,e.getMessage());
            }

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
            activity.rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean b) throws Exception {
                    Log.i("permission",b+"");
                    if (b) {
                        Matisse.from(MapFragment.this)
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            //获取拍摄的图片路径，如果是录制视频则是视频的第一帧图片路径
            String captureImagePath = Matisse.obtainCaptureImageResult(data);
            if (captureImagePath != null && !captureImagePath.isEmpty()){
                uploadImage(BitmapTools.saveBitmap(BitmapTools.getimage(new File(captureImagePath).getPath()), new File(captureImagePath).getPath()));
            }else {
                //获取选择图片或者视频的结果路径，如果开启裁剪的话，获取的是原图的地址
                List<String> list = Matisse.obtainSelectPathResult(data);//文件形式路径
                uploadImage(BitmapTools.saveBitmap(BitmapTools.getimage(new File(list.get(0)).getPath()), new File(list.get(0)).getPath()));
            }

        }
    }

    public void uploadImage(File file) {
        LogUtils.i(file);
        HttpUtils.onUploadOne(activity, file, Constants.upload, new JsonResponseHandler() {
            @Override
            public void onError(ApiException e) {

            }

            @Override
            public void onSuccess(JSONObject response) {
                try {
                    if (response.getInt("err") == 200) {
                        if (response.getString("data") != null) {
                            uploadImage_Success(response.getString("data"));

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void uploadImage_Success(String url) {
        wv_program.loadUrl("javascript:getImageUrl('" + url + "')");

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMapEvent(LocationBean locationBean){
        double latitude = locationBean.getLatitude();
        double longitude = locationBean.getLongitude();
        if (locationBean.isBack() && !locationBean.isWbeView()) {
            wv_program.post(new Runnable() {
                @Override
                public void run() {
                    wv_program.loadUrl("javascript:getLocation(\"" + (longitude) + "\",\"" + (latitude) + "\")");
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        registerEventBus(MapFragment.this);
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterEventBus(MapFragment.this);
    }

}

