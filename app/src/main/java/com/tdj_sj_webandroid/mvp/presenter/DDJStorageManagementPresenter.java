package com.tdj_sj_webandroid.mvp.presenter;

import com.apkfuns.logutils.LogUtils;
import com.tdj_sj_webandroid.DDJStorageManagementActivity;
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
import java.util.List;
import java.util.Map;

public class DDJStorageManagementPresenter extends BasePresenter<Model, DDJStorageManagementActivity> {
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

    public void get_scann(int pn) {
        Map<String,String> map=new HashMap<>();
        map.put("ps","10");
        map.put("pn",String.valueOf(pn));
        HttpUtils.onGet(getIView().getContext(), map, Constants.home, new GsonResponseHandler<CustomApiResult<List<StorageManagement>>>() {
            @Override
            public void onError(ApiException e) {
            }

            @Override
            public void onSuccess(CustomApiResult<List<StorageManagement>> response) {
                LogUtils.i(response);
                getIView().get_scann__home_Success(response);


            }
        });




    }

}
