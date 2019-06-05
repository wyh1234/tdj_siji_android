package com.tdj_sj_webandroid.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

import com.tdj_sj_webandroid.mvp.presenter.IPresenter;
import com.tdj_sj_webandroid.mvp.view.IView;
import com.tdj_sj_webandroid.utils.Density;


/**
 * Created by wanyh on 2017/9/11.
 */
/*0356
* */
public abstract class BaseActivity<P extends IPresenter> extends FragmentActivity implements
        IView {
    protected View view;
    protected P mPresenter;
//    protected   ImmersionBar mImmersionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());
        Density.setDefault(this);//屏幕适配
      /*  if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }*/
        //初始化p层
//            mImmersionBar=ImmersionBar.with(this);
//            LogUtils.i(ImmersionBar.getStatusBarHeight(this));
        //沉浸式状态栏


        mPresenter = loadPresenter();
        //绑定v层
        initCommonData();
        //初始化空件
        initView();
        //初始化监听
        initListener();
        //加载网络（或者本地）数据
        initData();
//        Log.i("父类","父类方法");
    }

    protected abstract P loadPresenter();
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }


    private void initCommonData() {


        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void initView();

    protected abstract int getLayoutId();

//    protected abstract void otherViewClick(View view);

    /**
     * @return 显示的内容
     */
    public View getView() {
        view = View.inflate(this, getLayoutId(), null);
        return view;
    }

/*    *//**
     * 点击的事件的统一的处理
     *
     * @param view
     *//*
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                otherViewClick(view);
                break;
        }

    }*/




    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mPresenter != null){
            mPresenter.detachView();
        }
  /*      if (mImmersionBar!=null){
            mImmersionBar.destroy();
        }*/
    }

}
