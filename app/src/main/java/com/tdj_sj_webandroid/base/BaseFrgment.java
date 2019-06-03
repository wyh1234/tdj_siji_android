package com.tdj_sj_webandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdj_sj_webandroid.mvp.presenter.IPresenter;
import com.tdj_sj_webandroid.mvp.view.IView;


/**
 * Created by wanyh on 2017/9/11.
 */

public abstract class BaseFrgment<P extends IPresenter> extends Fragment implements IView {
    protected View view;
    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.mPresenter == null){
            //创建P层
            this.mPresenter = loadPresenter();
        }

        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(getContentId(),container,false);
        initView(view);
        loadData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //加载网络（或者本地）数据
//        loadData();

    }

  /*  @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            LogUtils.i("不可见");
//            System.out.println("不可见");

        } else {
            LogUtils.i("当前可见");
//            System.out.println("当前可见");
        }
    }*/



        @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mPresenter!=null){
            mPresenter.detachView();
        }

    }

    protected abstract void initView( View view);
//    protected abstract void otherViewClick(View view);
    protected abstract void loadData();
    protected abstract P loadPresenter();
    protected abstract int getContentId();
}

