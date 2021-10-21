package com.tdj_sj_webandroid.utils;

import android.content.Intent;
import android.os.Bundle;

import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.WebViewActivity;

public class ToPlanDialog extends BaseDialogFragment{

    private OnToPlanClickListener mOnToPlanClickListener;
    @Override
    protected int attachLayoutRes() {
        return R.layout.to_plan_dialog;
    }

    @Override
    protected void initData() {

    }

    public void setOnToPlanClickListener(OnToPlanClickListener listener){
        this.mOnToPlanClickListener = listener;
    }

    @Override
    protected void initView() {
        mRootView.findViewById(R.id.iv_close).setOnClickListener( v -> finishSelf());
        mRootView.findViewById(R.id.tv_to_plan).setOnClickListener( v -> {
            Intent intent = new Intent(mActivity, WebViewActivity.class);
            intent.putExtra("url", Constants.URL + "task/deliveryPlan.do");
            mActivity.startActivity(intent);
            finishSelf();
        });
    }


    public static ToPlanDialog newInstance() {
        Bundle args = new Bundle();
        ToPlanDialog fragment = new ToPlanDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnToPlanClickListener{
        void onToPlanClick();
    }
}
