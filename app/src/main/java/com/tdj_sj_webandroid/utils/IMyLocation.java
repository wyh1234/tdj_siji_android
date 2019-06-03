package com.tdj_sj_webandroid.utils;

import android.content.Context;
import android.location.LocationManager;

/**
 * Created by zmj on 2018/3/29.
 */

public interface IMyLocation {

    //获取位置服务
    LocationManager getLocationManager();

    //获取当前上下文
    Context  getContext();

    //当反编码成功的时候
//    void onSuccessGeocoder(Response<MyLocationBean> response);
}
