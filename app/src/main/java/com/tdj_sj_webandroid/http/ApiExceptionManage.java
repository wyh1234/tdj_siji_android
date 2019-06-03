package com.tdj_sj_webandroid.http;

import android.content.Context;
import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.tdj_sj_webandroid.utils.ToastUtils;
import com.zhouyou.http.exception.ApiException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.io.NotSerializableException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * Created by wanyh on 2017/9/5.
 */

public class ApiExceptionManage extends Exception {
    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;

    public static void handleException(Throwable e, Context context) {
        ApiException ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ApiException(httpException, httpException.code());
            switch (httpException.code()) {
                case UNAUTHORIZED:


                    break;
                default:
                    ToastUtils.showToast(context, "未知错误");
                    break;
            }
        }else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof JsonSyntaxException
                || e instanceof JsonSerializer
                || e instanceof NotSerializableException
                || e instanceof ParseException) {
            ToastUtils.showToast(context, "解析错误");
        } else if (e instanceof ClassCastException) {
            ToastUtils.showToast(context, "类型转换错误");
        } else if (e instanceof ConnectException) {
            ToastUtils.showToast(context, "连接失败");
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ToastUtils.showToast(context, "证书验证失败");
        } else if (e instanceof ConnectTimeoutException) {
            ToastUtils.showToast(context, "连接超时");
        } else if (e instanceof java.net.SocketTimeoutException) {
            ToastUtils.showToast(context, "连接超时");
        } else if (e instanceof UnknownHostException) {
            ToastUtils.showToast(context, "无法解析该域名");
        } else if (e instanceof NullPointerException) {
        } else {
            ToastUtils.showToast(context, "未知错误");
        }
    }

}
