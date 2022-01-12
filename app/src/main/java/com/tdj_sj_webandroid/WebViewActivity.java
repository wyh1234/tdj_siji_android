package com.tdj_sj_webandroid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
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
import com.tdj_sj_webandroid.model.Resume;
import com.tdj_sj_webandroid.model.event.RefreshBackEvent;
import com.tdj_sj_webandroid.mvp.presenter.WebViewPresenter;
import com.tdj_sj_webandroid.utils.BitmapTools;
import com.tdj_sj_webandroid.utils.Constants;
import com.tdj_sj_webandroid.utils.GeneralUtils;
import com.tdj_sj_webandroid.utils.GifSizeFilter;
import com.tdj_sj_webandroid.utils.IMyLocation;
import com.tdj_sj_webandroid.utils.ImageWaterMarkUtil;
import com.tdj_sj_webandroid.utils.JumpPermissionManagement;
import com.tdj_sj_webandroid.utils.MyGlideEngine;
import com.tdj_sj_webandroid.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class WebViewActivity extends BaseActivity<WebViewPresenter> implements IMyLocation, TDJContract.WebViewView/*, TakePhoto.TakeResultListener, InvokeListener */ {
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

    @BindView(R.id.wv_program)
    SimpleWebView wv_program;
    private WebSettings settings;
    private static final int REQUEST_CODE_CHOOSE_GRIDE = 0X0002;
    private static final int REQUEST_CODE_CHOOSE = 0X0001;
    private int index;
    private String urls;
    //时间格式
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    //拍照
    private static final int REQUEST_IMAGE_CAPTURE = 10;

    private String currentPhotoPath;

    /**
     * 是否有定位权限 0无1有
     */
    private int isLocationPermission = 0;

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

        if (getIntent().getStringExtra("title") != null) {
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

        Map<String, String> map = new HashMap<>();
        map.put("token", GeneralUtils.getToken(getApplication()));
        if (getIntent().getStringExtra("url") != null) {
            wv_program.loadUrl(getIntent().getStringExtra("url"), map);
        }
        wv_program.setWebViewClient(new SimpleWebView.SimpleWebViewClient() {
            @Override
            public void onPageFinished(com.tencent.smtt.sdk.WebView webView, String url) {
                super.onPageFinished(webView, url);
                urls = url;
                LogUtils.e(url);//https://siji.51taodj.com/test-driver/driverScann/center.do
//                toolbarTitle.setText(webView.getTitle());//获取WebView 的标题，设置到toolbar中去
            }

            @Override
            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView webView, String url) {
                LogUtils.e(url + "2222222");

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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Resume(Resume resume) {
        Map<String, String> map = new HashMap<>();
        map.put("token", GeneralUtils.getToken(getApplication()));
        LogUtils.e(GeneralUtils.isNullOrZeroLenght(urls));
        if (!GeneralUtils.isNullOrZeroLenght(urls)) {
            wv_program.loadUrl(urls, map);
        }
    }

    @Override
    public Context getContext() {
        return WebViewActivity.this;
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
        public void check(String id, String customer_code, String type) {
            LogUtils.e(id);
            Intent intent;
            if (Build.MODEL.equals("NLS-MT90")) {
                intent = new Intent(getContext(), NuclearGoodsActivity.class);
            } else {
                intent = new Intent(getContext(), DDJNuclearGoodsActivity.class);
            }
            intent.putExtra("customer_id", id);
            intent.putExtra("customer_code", customer_code);
            intent.putExtra("type", Integer.valueOf(type));
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
            EventBus.getDefault().post(new ConfirmPlan(index, true));
//          MainTabActivity.lunchMainTabAc(WebViewActivity.this,index);
            finish();
        }

        @JavascriptInterface
        public void mapNavi(String jsonObject) {
            LogUtils.d(jsonObject);

            try {
                JSONObject jsonObject1 = new JSONObject(jsonObject);
                GeneralUtils.goToGaodeMap(getContext(), jsonObject1.getDouble("lat"), jsonObject1.getDouble("lng"), jsonObject1.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

//        @JavascriptInterface
//        public void getLocationBack() {
//            try {
//                if (isFirstGetLocation) {
//                    LogUtils.i( "onMapEvent: 进方法了");
//                    LocationUtils.getInstance().stopLocalService();
//                    getPermissions(true,true);
//                    isFirstGetLocation = false;
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//                ToastUtils.showToast(WebViewActivity.this,e.getMessage());
//            }
//
//        }

        /**
         * 上传 此时经纬度和是否有定位权限
         */
        @JavascriptInterface
        public String getLocation() {
            getPermissions(false, true);
            if (aBoolean) {
                isLocationPermission = 1;
            } else {
                isLocationPermission = 0;
                initPermissionSettingDialog();
            }
            Log.d("upupload", "" + isLocationPermission);
            return Constants.latitude + "|" + Constants.longtitude + "|" + isLocationPermission;
        }

        /**
         * 权限设置弹窗
         */
        private void initPermissionSettingDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(WebViewActivity.this);
            View view = LayoutInflater.from(WebViewActivity.this).inflate(R.layout.permission_detail_dialog, null);
            builder.setView(view);
            builder.create();
            Window windows = builder.create().getWindow();
            //圆角
            windows.setBackgroundDrawableResource(R.drawable.bg_circle);
            Dialog dialog = builder.show();
            view.findViewById(R.id.close_id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            view.findViewById(R.id.confirm_id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    try {
                        JumpPermissionManagement.GoToSetting(WebViewActivity.this);
                    } catch (Exception e) {
                    }
                }
            });
        }

        @JavascriptInterface
        public void redirectPage(String url) {
            WebViewActivity.this.finish();
            Intent intent = new Intent(getContext(), WebViewToActivity.class);
            intent.putExtra("url", Constants.URL1 + url);
            startActivity(intent);
        }

        @JavascriptInterface
        public void takePhoto() {
            //只拍照打卡
            TakePictureMethodSingle();
        }

        @JavascriptInterface
        public void uploadImage() {
            TakePictureMethod();
        }

        /**
         * 打卡界面返回我的页面刷新
         */
        @JavascriptInterface
        public void gobackRefresh() {
            EventBus.getDefault().post(new RefreshBackEvent(1));
            finish();
        }

    }

    public void TakePictureMethod() {
        //又拍照又相册选图片
        //从相册中选择图片 此处使用知乎开源库Matisse
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean b) throws Exception {
                Log.i("permission", b + "右派优选");
                if (b) {
                    Matisse.from(WebViewActivity.this)
                            .choose(MimeType.ofImage())
                            .theme(R.style.Matisse_Dracula)
                            .countable(true)//true:选中后显示数字;false:选中后显示对号
                            .maxSelectable(1)
                            .capture(true)//开启拍摄按钮
//                                .captureStrategy(new CaptureStrategy(true, "com.tdj_sj_webandroid.fileProvider")) //是否拍照功能，并设置拍照后图片的保存路径; FILE_PATH = 你项目的包名.fileprovider,必须配置不然会抛异常
                            .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .originalEnable(true)
                            .maxOriginalSize(70)//原图大小
                            .thumbnailScale(0.85f)
                            .imageEngine(new MyGlideEngine())
                            .forResult(REQUEST_CODE_CHOOSE);
                }
            }
        });
    }

    public void TakePictureMethodSingle() {
        //判断相机是否可用,只开启拍照。若想又拍照又相册选图片请开启上面的代码
        final boolean deviceHasCameraFlag = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
        if (deviceHasCameraFlag) {
            rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean b) throws Exception {
                    Log.i("permission", b + "只排");
                    if (b) {
                        Matisse.from(WebViewActivity.this)
//                    .jumpCapture()//直接跳拍摄，默认可以同时拍摄照片和视频
                                .jumpCapture(CaptureMode.Image)//只拍照片
                                //.jumpCapture(CaptureMode.Video)//只拍视频
                                .isCrop(false) //开启裁剪
                                .forResult(REQUEST_IMAGE_CAPTURE);
                    } else {
                        ToastUtils.showToast(WebViewActivity.this, "没有相机使用权限");
                    }
                }
            });
        } else {
            ToastUtils.showToast(WebViewActivity.this, "相机不可使用");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //知乎开源库Matisse 又拍照,又相册选择,暂时未使用
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            try {
                createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //获取拍摄的图片路径，如果是录制视频则是视频的第一帧图片路径
            String captureImagePath = Matisse.obtainCaptureImageResult(data);
            if (captureImagePath != null && !captureImagePath.isEmpty()) {
                BitmapTools.ReturnObject object = BitmapTools.getImageTwo(new File(captureImagePath).getPath());
                mPresenter.uploadImage(BitmapTools.saveBitmap(object.bitmap, currentPhotoPath));
            } else {
                //获取选择图片或者视频的结果路径，如果开启裁剪的话，获取的是原图的地址
                List<String> list = Matisse.obtainSelectPathResult(data);//文件形式路径
                File file = new File(new File(list.get(0)).getPath());
                long length = file.length() / 1024;
                // Log.d("aazzzz", "原图大小  " + length + "kb");
                BitmapTools.ReturnObject object = BitmapTools.getImageTwo(new File(list.get(0)).getPath());
                long length2 = BitmapTools.saveBitmap(object.bitmap, currentPhotoPath).length() / 1024;
                //Log.d("aazzzz", "压缩后大小  " + length2 + "kb");
                mPresenter.uploadImage(BitmapTools.saveBitmap(object.bitmap, currentPhotoPath));
            }
        }

        //只拍照打卡,调用系统相机
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //获取拍摄的图片路径，如果是录制视频则是视频的第一帧图片路径
            String captureImagePath = Matisse.obtainCaptureImageResult(data);
            if (captureImagePath != null && !captureImagePath.isEmpty()) {
                BitmapTools.ReturnObject object = BitmapTools.getImageTwo(new File(captureImagePath).getPath());
                mPresenter.uploadImageSingle(BitmapTools.saveBitmap(object.bitmap, currentPhotoPath));
            }
        }

    }

    /**
     * 退补换 货增加时间戳水印,合并两个bitMap(已改为接口添水印,可删除)
     */
    public Bitmap addImageWatermark(BitmapTools.ReturnObject sourceObject) {
        Date curDate = new Date(System.currentTimeMillis());
        String strTime = formatter.format(curDate);
        View view = LayoutInflater.from(this).inflate(R.layout.date_water_mark_view, null, false);
        TextView textView = view.findViewById(R.id.tvTime);
        textView.setText(strTime);
        //textView转bitMap
        textView.setDrawingCacheEnabled(true);
        int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthSpec, heightSpec);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
        //+50补偿sourceObject.bitmap.getWidth()的宽度无法填充满
        textView.layout(0, 0, sourceObject.bitmap.getWidth() + 50, textView.getMeasuredHeight());
        Bitmap tvBitMap = Bitmap.createBitmap(textView.getDrawingCache());
        tvBitMap.getWidth();
        Bitmap resultBit = ImageWaterMarkUtil.createWaterMaskCenterBottom(sourceObject.bitmap, tvBitMap);
        //ImageView viewSHow = (ImageView) findViewById(R.id.vvv);
        //viewSHow.setImageBitmap(resultBit);
        //释放资源
        textView.destroyDrawingCache();
        return resultBit;
    }

    /**
     * 图片压缩后的保存文件夹,防止压缩后的图片替换了原图
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TDJ_JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wv_program != null) {
            ViewParent parent = wv_program.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(wv_program);
            }
            wv_program.stopLoading();
            //退出时调用此方法，移除绑定的服务，否则某些特定系统会报错mSearchWebView.getSettings().setJavaScriptEnabled(false);

            wv_program.clearHistory();

            wv_program.clearView();

            wv_program.removeAllViews();

            try {
                wv_program.destroy();

            } catch (Throwable ex) {

                ex.printStackTrace();

            }

        }

    }
}
