package com.tdj_sj_webandroid.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Constants {
//    public final static String URL="https://siji.51taodj.com/tdj-driver/";
//    public final static String URL1="https://siji.51taodj.com/tdj-driver";
//    public final static String URL="https://siji.51taodj.com/yushangxian-driver/";
//    public final static String URL1="https://siji.51taodj.com/yushangxian-driver/";
    public final static String URL="https://siji.51taodj.com/test-driver/";
    public final static String URL1="https://siji.51taodj.com/test-driver";
//    public final static String URL="http://47.111.135.109:8060/test-driver/";
//    public final static String URL1="http://47.111.135.109:8060/test-driver";
//    public final static String URL="http://47.111.135.109:8060/yushangxian-driver/";
//    public final static String URL1="http://47.111.135.109:8060/yushangxian-driver/";

//public final static String URL="http://192.168.10.239:8089/";
//    public final static String URL1="http://192.168.10.239:8089";
//public final static String URL="https://siji.51taodj.com/
// test-driver/";
//    public final static String URL1="https://siji.51taodj.com/test-driver";
    public final static String upload="home/imgUpload.do";
    public final static String menus="user/menus.do";
    public final static String login="user/login.do";
    public final static String mine_do="mine.do";
    public final static String retreat="home/retreat.do?orderNo=";
    public final static String task="task/task.do";
    public final static String scann="order/scanner.do?";
    public final static String home="order/home.do";
    public final static String stock_list="order/inStockList.do?";
    public final static String check_list="driverScann/checkList.do";
    public final static String version="version/check.do?version=";
    public final static String addUserLocation="api/addUserLocation.do";
    public final static String driverScann="driverScann/scann.do?";
    public final static String driverScann_list="driverScann/list.do?";
    public final static String checkGoods="checkGoods/center.do?type=";
    public final static String problem="checkGoods/problem.do?type=";
    public final static String statis="checkGoods/statis.do?type=";
    public final static String diy="diy/scanner.do?";
    public final static String diy_home="diy/home.do?";
    public final static String getSijiCount="storeDiyFee/getSijiCount.do?";
    public final static String getSijiCountByStore="storeDiyFee/getSijiCountByStore.do?";


    public static boolean chackEdtextNoEmpty(Context context,EditText editText,String str){
        String edstr = editText.getText().toString().trim();
        if (edstr.length()==0) {
            ToastMessage(context, str);
            return false;
        }else {
            return true;
        }
    }
    public static int chackEdtextArea(Context context, EditText editText, int lessNumber, int moreNumber, String str){
        String edstr = editText.getText().toString().trim();
        int valueOf = Integer.valueOf(edstr);
        if (valueOf<lessNumber||valueOf>moreNumber) {
            ToastMessage(context, str);
            return -1;
        }else {
            return valueOf;
        }
    }
    public static AlertDialog.Builder Dialog(Context context, String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(message);
        return builder;
    }
    public static void ToastMessage(Context context,String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static AlertDialog.Builder DialogView(Context context, View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder.setView(view);
    }
    public static String byteASCIIstr(byte[] by){
        String str="";
        for (int i = 0; i < by.length; i++) {
            char b=(char)by[i];
            str+=b;
        }
        return str;
    }

}
