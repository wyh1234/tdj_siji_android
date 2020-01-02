package com.tdj_sj_webandroid.mvp.presenter;

import com.apkfuns.logutils.LogUtils;
import com.tdj_sj_webandroid.ScannerHistoryActivity;
import com.tdj_sj_webandroid.base.BasePresenter;
import com.tdj_sj_webandroid.http.GsonResponseHandler;
import com.tdj_sj_webandroid.http.HttpUtils;
import com.tdj_sj_webandroid.model.CustomApiResult;
import com.tdj_sj_webandroid.model.ScannerHistory;
import com.tdj_sj_webandroid.model.StorageManagement;
import com.tdj_sj_webandroid.mvp.model.Model;
import com.tdj_sj_webandroid.utils.Constants;
import com.zhouyou.http.exception.ApiException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScannerHistoryPresenter extends BasePresenter<Model, ScannerHistoryActivity> {
    @Override
    public Model loadModel() {
        return null;
    }
    public void getScannerHistory(int pn,String startTime,String endTime) {
        Map<String,String> map=new HashMap<>();
        map.put("ps","20");
        map.put("pn",String.valueOf(pn));
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        HttpUtils.onPost(getIView().getContext(), map, Constants.getSijiCount, new GsonResponseHandler<CustomApiResult<ScannerHistory>>() {
            @Override
            public void onError(ApiException e) {
                getIView(). getScannerHistory_err();
            }

            @Override
            public void onSuccess(CustomApiResult<ScannerHistory> response) {
                LogUtils.i(response);

                getIView().getScannerHistory_Success(response);


            }
        });




    }
}
