package com.tdj_sj_webandroid.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    private static Toast toast;
    public static void showToast(Context context, String message) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        } else {
            toast.setText(message);
        }
        toast.show();
    }



}