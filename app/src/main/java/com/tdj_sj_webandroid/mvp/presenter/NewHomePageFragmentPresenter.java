package com.tdj_sj_webandroid.mvp.presenter;

import android.os.Build;

import com.apkfuns.logutils.LogUtils;
import com.tdj_sj_webandroid.AppAplication;
import com.tdj_sj_webandroid.base.BasePresenter;
import com.tdj_sj_webandroid.contract.TDJContract;
import com.tdj_sj_webandroid.fragment.NewHomePageFragment;
import com.tdj_sj_webandroid.http.GsonResponseHandler;
import com.tdj_sj_webandroid.http.HttpUtils;
import com.tdj_sj_webandroid.model.AppUpdate;
import com.tdj_sj_webandroid.model.CustomApiResult;
import com.tdj_sj_webandroid.model.HomeInfo;
import com.tdj_sj_webandroid.mvp.model.Model;
import com.tdj_sj_webandroid.utils.Constants;
import com.tdj_sj_webandroid.utils.GeneralUtils;
import com.zhouyou.http.exception.ApiException;

import java.util.HashMap;

public class NewHomePageFragmentPresenter extends BasePresenter<Model, NewHomePageFragment> implements TDJContract.HomePageFragmentPresenter {
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
    public void get_versions_store() {

        HttpUtils.onGet(getIView().getContext(), new HashMap<String, String>(), Constants.version+ GeneralUtils.getAppVersionName(AppAplication.getAppContext())+"&type="+ Build.MODEL, new GsonResponseHandler<AppUpdate>() {

            @Override
            public void onError(ApiException e) {
                LogUtils.i(e.getMessage());
            }

            @Override
            public void onSuccess(AppUpdate appUpdate) {
                if (appUpdate.getErr()==0) {
                    getIView().get_versions_Success(appUpdate);
                }
//                String json = "{\"err\":0,\"msg\":\"成功\",\"data\":{\"id\":1,\"appName\":\"淘大运\",\"version\":\"3.5\",\"remark\":\"1.司机APP改版\",\"model\":\"CRUISE1\",\"nickname\":\"android\",\"createTime\":1565145451000,\"eifTime\":1564972655000,\"url\":\"https://tdj-res.oss-cn-hangzhou.aliyuncs.com/android/tdj-driver.apk\",\"type\":\"0\",\"size\":5.7}}";
//                AppUpdate update = new Gson().fromJson(json, AppUpdate.class);
//                getIView().get_versions_Success(update);
            }
        });
    }
}
