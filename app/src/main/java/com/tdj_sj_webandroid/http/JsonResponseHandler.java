package com.tdj_sj_webandroid.http;

import org.json.JSONObject;

/**
 * json类型的回调接口
 * Created by tsy on 16/8/15.
 */
public abstract class JsonResponseHandler implements IResponse {

    public abstract void onSuccess( JSONObject response);


}
