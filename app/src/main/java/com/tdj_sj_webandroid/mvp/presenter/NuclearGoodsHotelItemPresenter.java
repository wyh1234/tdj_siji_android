package com.tdj_sj_webandroid.mvp.presenter;

import com.apkfuns.logutils.LogUtils;
import com.tdj_sj_webandroid.base.BasePresenter;
import com.tdj_sj_webandroid.fragment.NuclearGoodsHotelItemFragment;
import com.tdj_sj_webandroid.http.GsonResponseHandler;
import com.tdj_sj_webandroid.http.HttpUtils;
import com.tdj_sj_webandroid.model.CustomApiResult;
import com.tdj_sj_webandroid.model.GoodsInfo;
import com.tdj_sj_webandroid.model.NuclearGoodsHotel;
import com.tdj_sj_webandroid.mvp.model.Model;
import com.tdj_sj_webandroid.utils.Constants;
import com.zhouyou.http.exception.ApiException;

import java.util.HashMap;
import java.util.Map;

public class NuclearGoodsHotelItemPresenter extends BasePresenter<Model, NuclearGoodsHotelItemFragment> {
    @Override
    public Model loadModel() {
        return null;
    }

    public void checkGoods(String url) {
        Map<String,String> map=new HashMap<>();
        HttpUtils.onGet(getIView().getContext(), map, url, new GsonResponseHandler<CustomApiResult<GoodsInfo>>() {
            @Override
            public void onError(ApiException e) {
                getIView().checkGoods_onError(e);
            }

            @Override
            public void onSuccess(CustomApiResult<GoodsInfo> response) {
                LogUtils.i(response);
                getIView().checkGoods_Success(response);


            }
        });




    }
}
