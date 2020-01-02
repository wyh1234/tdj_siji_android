package com.tdj_sj_webandroid.mvp.presenter;

import com.apkfuns.logutils.LogUtils;
import com.tdj_sj_webandroid.ScannerHistoryDetalisActivity;
import com.tdj_sj_webandroid.base.BasePresenter;
import com.tdj_sj_webandroid.http.GsonResponseHandler;
import com.tdj_sj_webandroid.http.HttpUtils;
import com.tdj_sj_webandroid.model.CustomApiResult;
import com.tdj_sj_webandroid.model.ScannerHistory;
import com.tdj_sj_webandroid.model.ScannerHistoryDetalis;
import com.tdj_sj_webandroid.mvp.model.Model;
import com.tdj_sj_webandroid.utils.Constants;
import com.zhouyou.http.exception.ApiException;

import java.util.HashMap;
import java.util.Map;

public class ScannerHistoryDetalisPresenter extends BasePresenter<Model, ScannerHistoryDetalisActivity> {
    @Override
    public Model loadModel() {
        return null;
    }

    public void getScannerHistoryDetalis(int pn, String staetTime, String endTime, String storeId,String storeName,String scannerHouse,String storePhone) {
        Map<String,String> map=new HashMap<>();
        map.put("ps","20");
        map.put("pn",String.valueOf(pn));
        map.put("startTime",staetTime);
        map.put("endTime",endTime);
        map.put("storeId",storeId);
        map.put("storeName",storeName);
        map.put("scannerHouse",scannerHouse);
        map.put("storePhone",storePhone);
        HttpUtils.onPost(getIView().getContext(), map, Constants.getSijiCountByStore, new GsonResponseHandler<CustomApiResult<ScannerHistoryDetalis>>() {
            @Override
            public void onError(ApiException e) {
                getIView(). getScannerHistory_err();
            }

            @Override
            public void onSuccess(CustomApiResult<ScannerHistoryDetalis> response) {
                LogUtils.i(response);

                getIView().getScannerHistory_Success(response);


            }
        });

    }
}
