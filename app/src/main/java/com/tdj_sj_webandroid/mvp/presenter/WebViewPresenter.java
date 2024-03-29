package com.tdj_sj_webandroid.mvp.presenter;

import com.apkfuns.logutils.LogUtils;
import com.tdj_sj_webandroid.WebViewActivity;
import com.tdj_sj_webandroid.base.BasePresenter;
import com.tdj_sj_webandroid.contract.TDJContract;
import com.tdj_sj_webandroid.http.HttpUtils;
import com.tdj_sj_webandroid.http.JsonResponseHandler;
import com.tdj_sj_webandroid.mvp.model.Model;
import com.tdj_sj_webandroid.utils.Constants;
import com.zhouyou.http.exception.ApiException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class WebViewPresenter extends BasePresenter<Model, WebViewActivity> implements TDJContract.WebViewPresenter {
    @Override
    public Model loadModel() {
        return null;
    }

    /**
     * 退换补打卡上传
     */
    @Override
    public void uploadImage(File file) {
        LogUtils.i(file);
        HttpUtils.onUploadOne(getIView().getContext(), file, Constants.uploadWithMark, new JsonResponseHandler() {
            @Override
            public void onError(ApiException e) {

            }

            @Override
            public void onSuccess(JSONObject response) {
                try {
                    if (response.getInt("err") == 200) {
                        if (response.getString("data") != null) {
                            getIView().uploadImage_Success(response.getString("data"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 拍照打卡上传
     */
    public void uploadImageSingle(File file) {
        LogUtils.i(file);
        HttpUtils.onUploadOne(getIView().getContext(), file, Constants.upload, new JsonResponseHandler() {
            @Override
            public void onError(ApiException e) {

            }

            @Override
            public void onSuccess(JSONObject response) {
                try {
                    if (response.getInt("err") == 200) {
                        if (response.getString("data") != null) {
                            getIView().uploadImage_Success(response.getString("data"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
