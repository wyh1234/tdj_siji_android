package com.tdj_sj_webandroid.http;


import com.zhouyou.http.exception.ApiException;

public interface IResponse {
    void onError(ApiException e);

}
