package com.tdj_sj_webandroid.bluetooth.presenter;

import com.apkfuns.logutils.LogUtils;
import com.tdj_sj_webandroid.base.BasePresenter;
import com.tdj_sj_webandroid.bluetooth.fragment.LyGoodsHotelFragment;
import com.tdj_sj_webandroid.fragment.NuclearGoodsHotelFragment;
import com.tdj_sj_webandroid.http.GsonResponseHandler;
import com.tdj_sj_webandroid.http.HttpUtils;
import com.tdj_sj_webandroid.model.CustomApiResult;
import com.tdj_sj_webandroid.model.NuclearGoodsHotel;
import com.tdj_sj_webandroid.mvp.model.Model;
import com.tdj_sj_webandroid.utils.Constants;
import com.zhouyou.http.exception.ApiException;

import java.util.HashMap;
import java.util.Map;

public class LyGoodsHotelPresenter extends BasePresenter<Model, LyGoodsHotelFragment> {
    @Override
    public Model loadModel() {
        return null;
    }
    public void checkGoods(int type,String customerlinecode) {
        Map<String,String> map=new HashMap<>();
        HttpUtils.onGet(getIView().getContext(), map, Constants.checkGoods+type+"&customerlinecode="+customerlinecode, new GsonResponseHandler<CustomApiResult<NuclearGoodsHotel>>() {
            @Override
            public void onError(ApiException e) {
                getIView().checkGoods_onError(e);
            }

            @Override
            public void onSuccess(CustomApiResult<NuclearGoodsHotel> response) {
                LogUtils.i(response);
                getIView().checkGoods_Success(response);


            }
        });




    }
}
