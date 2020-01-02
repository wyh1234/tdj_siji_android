package com.tdj_sj_webandroid;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.tdj_sj_webandroid.adapter.ScannerHistoryAdapter;
import com.tdj_sj_webandroid.adapter.ScannerHistoryDetalisAdapter;
import com.tdj_sj_webandroid.base.BaseActivity;
import com.tdj_sj_webandroid.model.CustomApiResult;
import com.tdj_sj_webandroid.model.ScannerHistoryDetalis;
import com.tdj_sj_webandroid.mvp.presenter.ScannerHistoryDetalisPresenter;
import com.tdj_sj_webandroid.utils.ListUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScannerHistoryDetalisActivity extends BaseActivity<ScannerHistoryDetalisPresenter> implements OnLoadmoreListener {
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.rk_list)
    RecyclerView rk_list;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    public int pageNo = 1;
    private List<ScannerHistoryDetalis.ItemsBean> scannerHistoryList=new ArrayList<>();
    private ScannerHistoryDetalisAdapter scannerHistoryAdapter;
    @OnClick({R.id.btn_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }
    @Override
    protected ScannerHistoryDetalisPresenter loadPresenter() {
        return new ScannerHistoryDetalisPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        refreshLayout.setOnLoadmoreListener(this);
        ImmersionBar.with(this).statusBarDarkFont(true).keyboardEnable(true)
                .init();
        LinearLayoutManager layout = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        rk_list.setLayoutManager(layout);
        scannerHistoryAdapter = new ScannerHistoryDetalisAdapter(this, scannerHistoryList);
        rk_list.setAdapter(scannerHistoryAdapter);

        getScannerHistoryDetalis(1);
    }

    private void getScannerHistoryDetalis(int pn) {
        if (!ListUtils.isEmpty(scannerHistoryList)){
            if (pn==1){
                scannerHistoryList.clear();
                scannerHistoryAdapter.notifyDataSetChanged();
            }

        }
        mPresenter.getScannerHistoryDetalis(pn,getIntent().getStringExtra("staetTime"),
                getIntent().getStringExtra("endTime"),getIntent().getStringExtra("storeId"),getIntent().getStringExtra("storeName"),
                getIntent().getStringExtra("scannerHouse"),getIntent().getStringExtra("storePhone"));

    }
    public void  getScannerHistory_err(){
        if (refreshLayout.isEnableLoadmore()) {
            refreshLayout.finishLoadmore();
        }
    }
    public void getScannerHistory_Success(CustomApiResult<ScannerHistoryDetalis> response) {
        if (refreshLayout.isEnableLoadmore()) {
            refreshLayout.finishLoadmore();
        }
        if (response.getData()==null){
            return;
        }
        if (response.getErr() == 0) {

            if (response.getData().getItems().size() > 0) {
                scannerHistoryList.addAll(response.getData().getItems());
                scannerHistoryAdapter.notifyDataSetChanged();

            } else {
                if (response.getData().getPn()!=1) {
                    Toast.makeText(this, "数据加载完毕", Toast.LENGTH_LONG).show();
                }
            }

        } else {
            if (response.getData().getPn()!=1) {
                Toast.makeText(this, "数据加载完毕", Toast.LENGTH_LONG).show();
            }
        }

    }

    public Context getContext() {
        return this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.scanner_history_details_layout;
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        getScannerHistoryDetalis(++pageNo);
    }


}
