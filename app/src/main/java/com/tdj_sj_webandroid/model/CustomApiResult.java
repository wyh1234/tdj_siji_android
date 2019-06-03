package com.tdj_sj_webandroid.model;

import com.zhouyou.http.model.ApiResult;

public class CustomApiResult <T>{
    private int err;
    private String msg;
    private T data;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isOk() {
        return err==200;
    }
}
