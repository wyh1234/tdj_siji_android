package com.tdj_sj_webandroid.mvp.presenter;

import com.apkfuns.logutils.LogUtils;
import com.tdj_sj_webandroid.base.BasePresenter;
import com.tdj_sj_webandroid.contract.TDJContract;
import com.tdj_sj_webandroid.fragment.HomePageFragment;
import com.tdj_sj_webandroid.http.GsonResponseHandler;
import com.tdj_sj_webandroid.http.HttpUtils;
import com.tdj_sj_webandroid.model.CustomApiResult;
import com.tdj_sj_webandroid.model.HomeInfo;
import com.tdj_sj_webandroid.mvp.model.Model;
import com.tdj_sj_webandroid.utils.Constants;
import com.zhouyou.http.exception.ApiException;

import java.util.HashMap;

public class HomePageFragmentPresenter extends BasePresenter<Model, HomePageFragment> implements TDJContract.HomePageFragmentPresenter {
    @Override
    public Model loadModel() {
        return null;
    }

    @Override
    public void get_menus() {
        HttpUtils.onPost(getIView().getContext(), new HashMap<String, String>(), Constants.menus, new GsonResponseHandler<CustomApiResult<HomeInfo>>() {
            @Override
            public void onError(ApiException e) {

            }

            @Override
            public void onSuccess(CustomApiResult<HomeInfo> response) {
                if (response.isOk()){
                    getIView().get_menus_Success(response.getData());

                }

            }
        });

    }
    @Override
    public void get_scann(String code) {
        HttpUtils.onGet(getIView().getContext(), new HashMap<String, String>(), Constants.scann+code, new GsonResponseHandler<CustomApiResult>() {
            @Override
            public void onError(ApiException e) {

            }

            @Override
            public void onSuccess(CustomApiResult response) {
                    LogUtils.i(response);
                    getIView().get_scann_Success(response.getErr());


            }
        });

    }
}
