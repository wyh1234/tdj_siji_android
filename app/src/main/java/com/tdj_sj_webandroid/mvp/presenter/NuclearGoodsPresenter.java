package com.tdj_sj_webandroid.mvp.presenter;

import com.apkfuns.logutils.LogUtils;
import com.tdj_sj_webandroid.NuclearGoodsActivity;
import com.tdj_sj_webandroid.base.BasePresenter;
import com.tdj_sj_webandroid.http.GsonResponseHandler;
import com.tdj_sj_webandroid.http.HttpUtils;
import com.tdj_sj_webandroid.model.CheckListBean;
import com.tdj_sj_webandroid.model.CustomApiResult;
import com.tdj_sj_webandroid.model.StorageManage;
import com.tdj_sj_webandroid.model.StorageManagement;
import com.tdj_sj_webandroid.mvp.model.Model;
import com.tdj_sj_webandroid.utils.Constants;
import com.zhouyou.http.exception.ApiException;

import java.util.HashMap;
import java.util.Map;

public class NuclearGoodsPresenter extends BasePresenter<Model, NuclearGoodsActivity> {
    @Override
    public Model loadModel() {
        return null;
    }
    public void get_scann(String code,String type,String customer,String customer_code) {
        Map<String,String> map=new HashMap<>();
        map.put("code",code);
        map.put("type",type);
        map.put("customer_id",customer);
        map.put("customer_code",customer_code);

        HttpUtils.onGet(getIView().getContext(), map, Constants.driverScann, new GsonResponseHandler<CustomApiResult<StorageManagement>>() {
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

    public void get_scann(int pn,String customer_id) {
        Map<String,String> map=new HashMap<>();
        map.put("ps","10");
        map.put("pn",String.valueOf(pn));
        map.put("customer_id",customer_id+"");
        HttpUtils.onGet(getIView().getContext(), map, Constants.driverScann_list, new GsonResponseHandler<CustomApiResult<StorageManage>>() {
            @Override
            public void onError(ApiException e) {
            }

            @Override
            public void onSuccess(CustomApiResult<StorageManage> response) {
                LogUtils.i(response);
                getIView().get_scann__home_Success(response);


            }
        });
    }

    public void getStockList(String code,String type) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("type",type);
        HttpUtils.onGet(getIView().getContext(), map, Constants.check_list, new GsonResponseHandler<CustomApiResult<CheckListBean>>() {
            @Override
            public void onError(ApiException e) {
            }

            @Override
            public void onSuccess(CustomApiResult<CheckListBean> response) {
                LogUtils.i(response);
                getIView().getStockListSuccess(response);
            }
        });
    }
}
