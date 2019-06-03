package com.tdj_sj_webandroid.http;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.zhouyou.http.body.UIProgressResponseCallBack;
import com.zhouyou.http.subsciber.IProgressDialog;
import com.zhouyou.http.utils.HttpLog;

public class UpdaterIProgressDialog extends UIProgressResponseCallBack {
    private  Context context;
    public UpdaterIProgressDialog(Context context){
        this.context=context;
    }


    @Override
    public void onUIResponseProgress(long bytesRead, long contentLength, boolean done) {
        int progress = (int) (bytesRead * 100 / contentLength);
        HttpLog.i("progress=" + progress);
        ((ProgressDialog) mProgressDialog.getDialog()).setProgress(progress);
        if (done) {//完成
            ((ProgressDialog) mProgressDialog.getDialog()).setMessage("上传完整");
        }
    }

    private ProgressDialog dialog;
    private IProgressDialog mProgressDialog = new IProgressDialog() {
        @Override
        public Dialog getDialog() {
            if(dialog==null) {
                dialog = new ProgressDialog(context);
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置进度条的形式为圆形转动的进度条
                dialog.setMessage("正在上传...");
                // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
                dialog.setTitle("文件上传");
                dialog.setMax(100);
            }
            return dialog;
        }
    };
}
