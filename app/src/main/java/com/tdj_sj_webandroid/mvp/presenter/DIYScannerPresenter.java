package com.tdj_sj_webandroid.mvp.presenter;

import com.apkfuns.logutils.LogUtils;
import com.tdj_sj_webandroid.DIYScannerActivity;
import com.tdj_sj_webandroid.base.BasePresenter;
import com.tdj_sj_webandroid.http.GsonResponseHandler;
import com.tdj_sj_webandroid.http.HttpUtils;
import com.tdj_sj_webandroid.model.CustomApiResult;
import com.tdj_sj_webandroid.model.StorageManagement;
import com.tdj_sj_webandroid.mvp.model.Model;
import com.tdj_sj_webandroid.utils.Constants;
import com.tdj_sj_webandroid.utils.GeneralUtils;
import com.zhouyou.http.exception.ApiException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DIYScannerPresenter extends BasePresenter<Model, DIYScannerActivity> {
    @Override
    public Model loadModel() {
        return null;
    }

    public void get_scann(String code,String type,String sale) {
        Map<String,String> map=new HashMap<>();
        map.put("code",code);
        map.put("type",type);
        if (!GeneralUtils.isNullOrZeroLenght(sale)){
            map.put("flag",sale);
        }
        HttpUtils.onGet(getIView().getContext(), map, Constants.diy, new GsonResponseHandler<CustomApiResult<StorageManagement>>() {
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

    public void get_scann(int pn,String sale) {
        Map<String,String> map=new HashMap<>();
        map.put("ps","10");
        map.put("pn",String.valueOf(pn));
        if (!GeneralUtils.isNullOrZeroLenght(sale)){
            map.put("flag",sale);
        }
        HttpUtils.onGet(getIView().getContext(), map, Constants.diy_home, new GsonResponseHandler<CustomApiResult<List<StorageManagement>>>() {
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
