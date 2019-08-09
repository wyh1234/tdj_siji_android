package com.tdj_sj_webandroid.contract;

import com.tdj_sj_webandroid.model.HomeInfo;

import java.io.File;

public class TDJContract {
    public interface HomePageFragmentView{
        void get_menus_Success(HomeInfo homeInfo);
//        void get_scann_Success(int err);

    }
    public interface  HomePageFragmentPresenter{
        void get_menus();
//        void get_scann(String code);
    }
    public interface WebViewView{
        void uploadImage_Success(String url);

    }
    public interface  WebViewPresenter{
        void uploadImage(File file);
    }
}
