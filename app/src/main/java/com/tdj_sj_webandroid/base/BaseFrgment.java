package com.tdj_sj_webandroid.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apkfuns.logutils.LogUtils;
import com.github.nukc.stateview.StateView;
import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.mvp.presenter.IPresenter;
import com.tdj_sj_webandroid.mvp.view.IView;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by wanyh on 2017/9/11.
 */

public abstract class BaseFrgment<P extends IPresenter>  extends LazyLoadFragment implements IView {
    protected View view;
    protected P mPresenter;
    protected Activity mActivity;
    protected StateView mStateView;//用于显示加载中、网络异常，空布局、内容布局
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
        mStateView = StateView.inject(getStateViewRoot());
        if (mStateView != null){
//                mStateView.setLoadingResource(R.layout.page_loading);
            mStateView.setEmptyResource(R.layout.page_empty1);
        }
        loadData();
        return view;
    }
    /**StateView的根布局，默认是整个界面，如果需要变换可以重写此方法*/
    public View getStateViewRoot() {
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
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    protected abstract void initView( View view);
//    protected abstract void otherViewClick(View view);
    protected abstract void loadData();
    protected abstract P loadPresenter();
    protected abstract int getContentId();
    public boolean isEventBusRegisted(Object subscribe) {
        return EventBus.getDefault().isRegistered(subscribe);
    }
    public void registerEventBus(Object subscribe) {
        if (!isEventBusRegisted(subscribe)) {
            EventBus.getDefault().register(subscribe);
            LogUtils.e(subscribe+"");
        }
    }
    public void unregisterEventBus(Object subscribe) {
        if (isEventBusRegisted(subscribe)) {
            EventBus.getDefault().unregister(subscribe);
        }
    }
}

