package com.tdj_sj_webandroid.http;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.zhouyou.http.subsciber.IProgressDialog;

public class MyIProgressDialog implements IProgressDialog {
    private Context context;
    private String msg;

    public MyIProgressDialog(Context context){
        this.context=context;

    }
    private MyIProgressDialog(Context context,String msg){
        this.context=context;
        this.msg=msg;

    }
    @Override
    public Dialog getDialog() {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        if (msg!=null){
            dialog.setMessage(msg+"...");
        }else {
            dialog.setMessage("加载中...");
        }

        return dialog;
    }
}
