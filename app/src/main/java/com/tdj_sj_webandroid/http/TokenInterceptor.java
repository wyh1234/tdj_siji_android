package com.tdj_sj_webandroid.http;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.tdj_sj_webandroid.AppAplication;
import com.tdj_sj_webandroid.MianActivity;
import com.tdj_sj_webandroid.model.CustomApiResult;
import com.tdj_sj_webandroid.utils.GeneralUtils;
import com.tdj_sj_webandroid.utils.ToastUtils;
import com.zhouyou.http.interceptor.BaseExpiredInterceptor;
import com.zhouyou.http.model.ApiResult;
import com.zhouyou.http.utils.HttpLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 处理接口签名异常
 */

public class TokenInterceptor extends BaseExpiredInterceptor {
    private Context context;
    private CustomApiResult apiResult;
    private Response res;

    public TokenInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public boolean isResponseExpired(Response response, String bodyString) {
        res=response;
        apiResult = new Gson().fromJson(bodyString, CustomApiResult.class);
        if (apiResult != null) {
            int code = apiResult.getErr();
            if (code!= 200) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Response responseExpired(Chain chain, String bodyString) {
            switch (apiResult.getErr()) {
                case 10000:
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            Looper.prepare();
                            try {
                                ToastUtils.showToast(context, apiResult.getMsg());
                            }catch (Exception e) {
                            }
                            Looper.loop();
                        }
                    }.start();
                    refreshToken();
                    break;
            }
        return res;
    }
    /**
     * 处理HeT网络请求出现的业务错误
     *
     * @return
     * @throws IOException
     */
    private Response processError(Chain chain, Request request, String key, String data) throws IOException {
        // create a new request and modify it accordingly using the new token
        String method = request.method();
        FormBody oldBody = (FormBody) request.body();
        if (oldBody == null) {
            if (method.equalsIgnoreCase("GET")) {
                oldBody = getRequestParams(request.url().query());
            } else {
                return chain.proceed(request);
            }
        }
        FormBody.Builder newBody = new FormBody.Builder();
        for (int i = 0; i < oldBody.size(); i++) {
            String name = oldBody.encodedName(i);
            String value = oldBody.encodedValue(i);
            if (!TextUtils.isEmpty(name)) {
                if (name.equals(key)) {
                    value = data;
                }
                if (name.equals("msg")) {
                    String path = request.url().pathSegments().get(0);
                    String ret = apiResult.getMsg();
                    if (!TextUtils.isEmpty(ret)) {
                        value = ret;
                    }
                }
            }
            newBody.add(name, value);
        }

        Request newRequest;
        if (method.equalsIgnoreCase("GET")) {
            String url = packageParams(newBody.build());
            HttpLog.i("uuok.GET.Error.newUrl:" + url);
            HttpUrl newHrrpIrl = request.url().newBuilder().query(url).build();
            newRequest = request.newBuilder().url(newHrrpIrl).get().build();
        } else {
            newRequest = request.newBuilder().post(newBody.build()).build();
        }
        // retry the request
//        originalResponse.body().close();
        return chain.proceed(newRequest);
    }
    /**
     * 封装参数
     */
    private String packageParams(FormBody oldBody) {
        List<String> namesAndValues = new ArrayList<>();
        for (int i = 0; i < oldBody.size(); i++) {
            String name = oldBody.encodedName(i);
            String value = oldBody.encodedValue(i);
            if (!TextUtils.isEmpty(name)) {
                namesAndValues.add(name);
                namesAndValues.add(value);
            }
        }
        StringBuilder sb = new StringBuilder();
        namesAndValuesToQueryString(sb, namesAndValues);
        return sb.toString();
    }
    /**
     * 将GET请求的参数封装成FormBody
     */
    private FormBody getRequestParams(String params) {
        if (params == null)
            return null;
        String[] strArr = params.split("&");
        if (strArr == null) {
            return null;
        }

        TreeMap<String, String> map = new TreeMap<>();
        FormBody.Builder fBulder = new FormBody.Builder();
        for (String s : strArr) {
            String[] sArr = s.split("=");
            if (sArr.length < 2)
                continue;
            map.put(sArr[0], sArr[1]);
            fBulder.add(sArr[0], sArr[1]);
        }
        FormBody formBody = fBulder.build();
        return formBody;
    }
    /**
     * 合并GET参数
     */
    private void namesAndValuesToQueryString(StringBuilder out, List<String> namesAndValues) {
        for (int i = 0, size = namesAndValues.size(); i < size; i += 2) {
            String name = namesAndValues.get(i);
            String value = namesAndValues.get(i + 1);
            if (i > 0) out.append('&');
            out.append(name);
            if (value != null) {
                out.append('=');
                out.append(value);
            }
        }
    }
    private void refreshToken() {
        GeneralUtils.removeToken(AppAplication.getAppContext());
        Intent intent = new Intent(context, MianActivity.class);
        context.startActivity(intent);
    }
}
