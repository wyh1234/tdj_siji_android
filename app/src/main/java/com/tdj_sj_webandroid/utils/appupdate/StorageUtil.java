package com.tdj_sj_webandroid.utils.appupdate;

import android.content.Context;
import android.os.Environment;

import com.tdj_sj_webandroid.AppAplication;
import com.tdj_sj_webandroid.R;

import java.io.File;


public class StorageUtil {

    public static String getCacheDirPath(Context context) {
        String path = "";
        if (context != null) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
                    && context.getExternalCacheDir() != null) {
                path = context.getExternalCacheDir().getPath();
            } else {
                path = context.getCacheDir().getPath();
            }
        } else {
            path = Environment.getExternalStoragePublicDirectory(context.getPackageName()).getPath() + "/cache";
        }
        return path;
    }

    public static String getImgCachePath(Context context) {
        return getCacheDirPath(context) + "/img/";
    }

    public static String PICC_CACHE_PATH = "/TSJ";
    /**
     * 获取picc缓存路径
     *
     * @return
     */
    public static String getPicCachePath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //sd卡可用
            //sd卡根目录
            return Environment.getExternalStorageDirectory() + PICC_CACHE_PATH;
        } else {
            //当前sd卡不可用
            //获取手机本身存储根目录
            return Environment.getExternalStoragePublicDirectory("") + PICC_CACHE_PATH;
        }
    }
    /**
     * 获取图片缓存路径
     *
     * @return
     */
    public static String getDiskCacheImagePath() {
        return isFileExistAndCreated(getPicCachePath() + File.separator +
                AppAplication.getAppContext().getString(R.string.app_name));
    }

    /**
     * 判断文件夹是否存在  不存在会创建
     *
     * @param path
     */
    private static String isFileExistAndCreated(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }
}
