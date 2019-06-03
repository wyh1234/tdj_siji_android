package com.tdj_sj_webandroid.contract;

import com.tdj_sj_webandroid.model.HomeInfo;

import java.io.File;

public class TDJContract {
    public interface HomePageFragmentView{
        void get_menus_Success(HomeInfo homeInfo);

    }
    public interface  HomePageFragmentPresenter{
        void get_menus();
    }
    public interface WebViewView{
        void uploadImage_Success(String url);

    }
    public interface  WebViewPresenter{
        void uploadImage(File file);
    }
}
