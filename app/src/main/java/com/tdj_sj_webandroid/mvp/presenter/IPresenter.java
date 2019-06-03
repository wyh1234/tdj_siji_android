package com.tdj_sj_webandroid.mvp.presenter;
import com.tdj_sj_webandroid.mvp.view.IView;

/**
 * Created by wanyh on 2017/9/11.
 */

public interface IPresenter<V extends IView> {
    /**
     * @param view 绑定
     */
    void attachView(V view);


    /**
     * 防止内存的泄漏,清楚presenter与activity之间的绑定
     */
    void detachView();

    /**
     *
     * @return 获取View
     */
    IView getIView();


}
