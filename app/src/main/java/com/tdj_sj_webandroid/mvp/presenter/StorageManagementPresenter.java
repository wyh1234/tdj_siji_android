package com.tdj_sj_webandroid.mvp.presenter;

import com.apkfuns.logutils.LogUtils;
import com.tdj_sj_webandroid.StorageManagementActivity;
import com.tdj_sj_webandroid.base.BasePresenter;
import com.tdj_sj_webandroid.http.GsonResponseHandler;
import com.tdj_sj_webandroid.http.HttpUtils;
import com.tdj_sj_webandroid.model.CustomApiResult;
import com.tdj_sj_webandroid.model.StorageManagement;
import com.tdj_sj_webandroid.mvp.model.Model;
import com.tdj_sj_webandroid.utils.Constants;
import com.zhouyou.http.exception.ApiException;

import java.util.HashMap;
import java.util.Map;

public class StorageManagementPresenter extends BasePresenter<Model, StorageManagementActivity> {
    @Override
    public Model loadModel() {
        return null;
    }
    public void get_scann(String code,String type) {
        Map<String,String> map=new HashMap<>();
        map.put("code",code);
        map.put("type",type);
        HttpUtils.onGet(getIView().getContext(), map, Constants.scann, new GsonResponseHandler<CustomApiResult<StorageManagement>>() {
            @Override
            public void onError(ApiException e) {
                getIView().get_scann_onError(e);
            }

            @Override
            public void onSuccess(CustomApiResult<StorageManagement> response) {
                LogUtils.i(response);
                getIView().get_scann_Success(response);


            }
        });

    }
}
