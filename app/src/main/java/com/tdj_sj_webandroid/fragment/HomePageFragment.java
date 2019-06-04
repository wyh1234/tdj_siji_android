package com.tdj_sj_webandroid.fragment;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.apkfuns.logutils.LogUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tdj_sj_webandroid.MainTabActivity;

import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.WebViewActivity;
import com.tdj_sj_webandroid.adapter.BaseRecyclerViewAdapter;
import com.tdj_sj_webandroid.adapter.HomePageFragmentAdapter;
import com.tdj_sj_webandroid.base.BaseFrgment;

import com.tdj_sj_webandroid.contract.TDJContract;
import com.tdj_sj_webandroid.model.HomeInfo;
import com.tdj_sj_webandroid.mvp.presenter.HomePageFragmentPresenter;
import com.tdj_sj_webandroid.utils.Constants;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

import static android.app.Activity.RESULT_OK;

public class HomePageFragment extends BaseFrgment<HomePageFragmentPresenter> implements TDJContract.HomePageFragmentView, BaseRecyclerViewAdapter.OnItemClickListener {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.list)
    RecyclerView list;
    private HomeInfo homeInfo;
    private RxPermissions rxPermissions;
    private static final int REQUEST_CODE_SCAN=0X0001;

    private MainTabActivity activity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainTabActivity) context;
    }
    public HomeInfo getHomeInfo() {
        return homeInfo;
    }

    public void setHomeInfo(HomeInfo homeInfo) {
        this.homeInfo = homeInfo;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        rxPermissions = new RxPermissions(getActivity());
        LinearLayoutManager layout = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layout);
    }

    @Override
    protected void loadData() {
        mPresenter.get_menus();

    }

    @Override
    protected HomePageFragmentPresenter loadPresenter() {
        return new HomePageFragmentPresenter();
    }

    @Override
    protected int getContentId() {
        return R.layout.homepage_layout;
    }


    @Override
    public void get_menus_Success(HomeInfo homeInfo) {
        setHomeInfo(homeInfo);
        tv_title.setText(homeInfo.getTitle());
        HomePageFragmentAdapter homePageFragmentAdapter= new HomePageFragmentAdapter(getContext(),homeInfo.getMenus());
        homePageFragmentAdapter.setOnItemClickListener(this);
        list.setAdapter(homePageFragmentAdapter);

    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, View v, int position) {
        if (getHomeInfo().getMenus().get(position).getMenuDesc()==null){
            Intent intent=new Intent(getContext(), WebViewActivity.class);

            intent.putExtra("url", Constants.URL1+getHomeInfo().getMenus().get(position).getMenuUrl());
            startActivity(intent);
    }else if  (getHomeInfo().getMenus().get(position).getMenuDesc().equals("sm")){
            rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean b) throws Exception {
                    Log.i("permission",b+"");
                    if (b) {
                        Intent intent = new Intent(getContext(), CaptureActivity.class);
                        startActivityForResult(intent, REQUEST_CODE_SCAN);

                    }
                }
            });
        }else if (getHomeInfo().getMenus().get(position).getMenuDesc().equals("ps")){
            activity.setTabSelection(1);
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
//                wv_program.loadUrl("javascript:getCameraResult('" + content + "')");
                Log.i("扫描结果为","扫描结果为：" + content);
                Intent intent=new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url", Constants.URL+Constants.retreat+content);
                LogUtils.d(Constants.URL+Constants.retreat+content);
                startActivity(intent);


            }
        }
    }
}
