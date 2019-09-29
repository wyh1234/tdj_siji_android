package com.tdj_sj_webandroid.base;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.amap.api.location.AMapLocationListener;
import com.apkfuns.logutils.LogUtils;
import com.github.nukc.stateview.StateView;
import com.google.android.gms.maps.model.LatLng;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tdj_sj_webandroid.AppAplication;
import com.tdj_sj_webandroid.DDJStorageManagementActivity;
import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.StorageManagementActivity;
import com.tdj_sj_webandroid.http.GsonResponseHandler;
import com.tdj_sj_webandroid.http.HttpUtils;
import com.tdj_sj_webandroid.model.AppUpdate;
import com.tdj_sj_webandroid.model.CustomApiResult;
import com.tdj_sj_webandroid.model.LocationBean;
import com.tdj_sj_webandroid.mvp.presenter.IPresenter;
import com.tdj_sj_webandroid.mvp.view.IView;
import com.tdj_sj_webandroid.utils.Constants;
import com.tdj_sj_webandroid.utils.Density;
import com.tdj_sj_webandroid.utils.GeneralUtils;
import com.tdj_sj_webandroid.utils.LocationUtils;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;


/**
 * Created by wanyh on 2017/9/11.
 */
/*0356
* */
public abstract class BaseActivity<P extends IPresenter> extends FragmentActivity implements IView {
    protected View view;
    protected P mPresenter;
     public RxPermissions rxPermissions;
     private boolean aBoolean;
    protected StateView mStateView;//用于显示加载中、网络异常，空布局、内容布局
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());
        Density.setDefault(this);//屏幕适配
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
      /*  if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }*/
        //初始化p层
//            mImmersionBar=ImmersionBar.with(this);
//            LogUtils.i(ImmersionBar.getStatusBarHeight(this));
        //沉浸式状态栏


        mPresenter = loadPresenter();
        //绑定v层
        initCommonData();
        //初始化空件
        initView();
        //初始化监听
        initListener();
        //加载网络（或者本地）数据
        initData();
//        Log.i("父类","父类方法");

        rxPermissions=new RxPermissions(this);
        mStateView = StateView.inject(getStateViewRoot());
        if (mStateView != null){
            mStateView.setEmptyResource(R.layout.page_empty);
        }
        if (!aBoolean){
            getPermissions();
        }

    }
    /**StateView的根布局，默认是整个界面，如果需要变换可以重写此方法*/
    public View getStateViewRoot() {
        return view;
    }
    public void getPermissions(){
        rxPermissions.request( Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean b) throws Exception {
                aBoolean=b;
                if (b){
                    LocationUtils.getInstance().startLocalService();
                }

            }
        });

    }

    protected abstract P loadPresenter();
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }


    private void initCommonData() {


        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void initView();

    protected abstract int getLayoutId();


    /**
     * @return 显示的内容
     */
    public View getView() {
        view = View.inflate(this, getLayoutId(), null);
        return view;
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mPresenter != null){
            mPresenter.detachView();
        }
        LocationUtils.getInstance().stopLocalService();
        unregisterEventBus(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.i("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.i("onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.i("onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.i("onStop");


    }



    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.i("onStart");
        registerEventBus(this);
    }

    /*code 不同事件接受處理*/
    @Subscribe( threadMode = ThreadMode.MAIN)
    public void eventCode(LocationBean locationBean) {
        LogUtils.i(locationBean);
/*        LogUtils.i(this instanceof StorageManagementActivity);
        LogUtils.i(this instanceof DDJStorageManagementActivity);
        if (this instanceof StorageManagementActivity||this instanceof DDJStorageManagementActivity){
            return;
        }*/
        if (GeneralUtils.isNullOrZeroLenght(GeneralUtils.getToken(AppAplication.getAppContext())) ){
            return;
        }
        Map<String, String> map=new HashMap<>();
        map.put("lat",String.valueOf(locationBean.getLatitude()));
        map.put("lng",String.valueOf(locationBean.getLongitude()));
        map.put("address",locationBean.getAddress());
        map.put("version",GeneralUtils.getAppVersionName(AppAplication.getAppContext()));

        HttpUtils.onPost1(this, map, Constants.addUserLocation, new GsonResponseHandler<CustomApiResult>() {
            @Override
            public void onError(ApiException e) {

            }

            @Override
            public void onSuccess(CustomApiResult customApiResult) {
                LogUtils.i(customApiResult);


            }
        });


    }


    public boolean isEventBusRegisted(Object subscribe) {
        return EventBus.getDefault().isRegistered(subscribe);
    }
    public void registerEventBus(Object subscribe) {
        if (!isEventBusRegisted(subscribe)) {
            EventBus.getDefault().register(subscribe);
        }
    }
    public void unregisterEventBus(Object subscribe) {
        if (isEventBusRegisted(subscribe)) {
            EventBus.getDefault().unregister(subscribe);
        }
    }
}
