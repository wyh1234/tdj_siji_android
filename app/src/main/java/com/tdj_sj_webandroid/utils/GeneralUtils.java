package com.tdj_sj_webandroid.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


public class GeneralUtils {
    public static ProgressDialog progressDialog;
    public static final String TOKEN = "tdj";
    private static DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    /**
     * 判断字符串是否为null或者0长度，字符串在判断长度时，先去除前后的空格,空或者0长度返回true,否则返回false
     *
     * @param str 被判断的字符串
     * @return boolean
     */
    public static boolean isNullOrZeroLenght(String str) {
        return (null == str || "".equals(str.trim())) ? true : false;
    }
    public static int getStatusBarHeight(Context context) {

        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
 /*   public static SnsPlatform createSnsPlatform(String platformName) {
        String mShowWord = platformName;
        String mIcon = "";
        String mGrayIcon = "";
        String mKeyword = platformName;
        if (Wechat.Name.equals(platformName)) {
            mIcon = "jiguang_socialize_wechat";
            mGrayIcon = "jiguang_socialize_wechat";
            mShowWord = "jiguang_socialize_text_weixin_key";
        } else if (WechatMoments.Name.equals(platformName)) {
            mIcon = "jiguang_socialize_wxcircle";
            mGrayIcon = "jiguang_socialize_wxcircle";
            mShowWord = "jiguang_socialize_text_weixin_circle_key";

        } else if (WechatFavorite.Name.equals(platformName)) {
            mIcon = "jiguang_socialize_wxfavorite";
            mGrayIcon = "jiguang_socialize_wxfavorite";
            mShowWord = "jiguang_socialize_text_weixin_favorite_key";
        }
        return ShareBoard.createSnsPlatform(mShowWord, mKeyword, mIcon, mGrayIcon, 0);
    }*/
    /**
     * 取消等待进度条
     */
    public static void cancelProgress() {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }
    public static long get_time_difference(String starttime, int day) {
        long from = 0;
        long to = 0;
        long m1 = 0;
        try {
            from = df1.parse(starttime).getTime();
            to = System.currentTimeMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (from - to > 0) {
            long m = (from - to);
            m1 = day * 60 * 60 * 24 - m / 1000;
        } else {
            long m = (to - from);
            m1 = day * 60 * 60 * 24 - m / 1000;
        }
        return m1;

    }

    public static void setInput(Activity activity) {//强制收起键盘
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(),
                    0);
         /*   boolean isOpen = imm.isActive();
            if (isOpen) {
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }*/
        }

    }


    public static void isNetworkAvailables(Context context) {
        if (!isNetworkAvailable(context)) {
            ToastUtils.showToast(context, "网络访问失败，请检查网络是否开启");
            return;
        }
    }
    /**
     * 判断网络是否连接
     *
     * @param inContext
     * @return
     */
    public static boolean isNetworkAvailable(Context inContext) {
        ConnectivityManager connectivity = (ConnectivityManager) inContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            // 获取所有网络连接信息
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {// 逐一查找状态为已连接的网络
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
        }
        return versionName;
    }

    /***
     * 读取token
     *
     * @param context
     * @return
     */
    public static String getToken(Context context) {
        SharedPreferences settings = context
                .getSharedPreferences(TOKEN, 0);
        String decryptToken = "";
        if (!"".equals(settings.getString("token", ""))) {
            decryptToken = settings.getString("token", "");
            Log.i("getToken", "" + decryptToken);
        }
        return decryptToken;
    }

    public static void setToken(Context context, String token) {
        SharedPreferences settings = context
                .getSharedPreferences(TOKEN, 0);
        SharedPreferences.Editor editor = settings.edit();
        Log.i("setToken", "" + token);
        editor.putString("token", token);
        editor.commit();
    }
    // 清除Token；
    public static void removeToken(Context context) {
        SharedPreferences settings = context
                .getSharedPreferences(TOKEN, 0);
        settings.edit().remove("token").commit();
    }



    /**
     * 跳转高德地图
     */
    public static void goToGaodeMap(Context mContext, double mLat, double mLng, String mAddressStr) {
        if (!isInstalled("com.autonavi.minimap",mContext)) {
            Toast.makeText(mContext, "请先安装高德地图客户端",Toast.LENGTH_LONG).show();
            return;
        }
        LatLng endPoint = BD2GCJ(new LatLng(mLat, mLng));//坐标转换
        StringBuffer stringBuffer = new StringBuffer("androidamap://navi?sourceApplication=").append("amap");
        stringBuffer.append("&lat=").append(endPoint.latitude)
                .append("&lon=").append(endPoint.longitude).append("&keywords=" + mAddressStr)
                .append("&dev=").append(0)
                .append("&style=").append(2);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        mContext.startActivity(intent);
    }

    /**
     * 检测程序是否安装
     *
     * @param packageName
     * @return
     */
    private static boolean isInstalled(String packageName,Context mContext) {
        PackageManager manager = mContext.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> installedPackages = manager.getInstalledPackages(0);
        if (installedPackages != null) {
            for (PackageInfo info : installedPackages) {
                if (info.packageName.equals(packageName))
                    return true;
            }
        }
        return false;
    }
    /**
     * BD-09 坐标转换成 GCJ-02 坐标
     */
    public static LatLng BD2GCJ(LatLng bd) {
        double x = bd.longitude - 0.0065, y = bd.latitude - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI);

        double lng = z * Math.cos(theta);//lng
        double lat = z * Math.sin(theta);//lat
        return new LatLng(lat, lng);
    }
}