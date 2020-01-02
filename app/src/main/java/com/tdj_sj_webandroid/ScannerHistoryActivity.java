package com.tdj_sj_webandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.tdj_sj_webandroid.adapter.ScannerHistoryAdapter;
import com.tdj_sj_webandroid.adapter.StorageManagementAdapter;
import com.tdj_sj_webandroid.base.BaseActivity;
import com.tdj_sj_webandroid.model.CustomApiResult;
import com.tdj_sj_webandroid.model.ScannerHistory;
import com.tdj_sj_webandroid.mvp.presenter.IPresenter;
import com.tdj_sj_webandroid.mvp.presenter.ScannerHistoryPresenter;
import com.tdj_sj_webandroid.utils.ListUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScannerHistoryActivity extends BaseActivity<ScannerHistoryPresenter> implements OnLoadmoreListener, ScannerHistoryAdapter.OnItemClickListener {
    @BindView(R.id.tv_star)
    TextView tv_star;
    @BindView(R.id.tv_end)
    TextView tv_end;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.rk_list)
    RecyclerView rk_list;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    public int pageNo = 1;
    private String star_date,end_date;
    private boolean flag;
    private TimePickerView pvTime;
    private List<ScannerHistory.ItemsBean> scannerHistoryList=new ArrayList<>();
    private ScannerHistoryAdapter scannerHistoryAdapter;
    @OnClick({R.id.tv_star,R.id.tv_end,R.id.btn_back,R.id.btn})
        public void onClick(View view){
            switch (view.getId()){
                case R.id.btn_back:
                    finish();
                    break;
                case R.id.tv_star:
                    flag=true;
                    getDate();
                    break;
                case R.id.tv_end:
                    flag=false;
                    getDate();
                    break;
                case R.id.btn://
                    getScannerHistory(1);
                    break;
            }
    }
    public void getDate(){
        //昨天的11点到今天的凌晨三点
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (flag){
                    star_date=getTimes(date);
                    tv_star.setText(setTime(date));

                }else {
                    end_date=getTimes(date);
                    tv_end.setText(setTime(date));
                }

            }
        }) //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月","日", "时", "分", "")
                .isCenterLabel(true)
                .setContentTextSize(16)
                .setLineSpacingMultiplier(1.8f)
                .setDividerColor(Color.DKGRAY)
                .setDate(Calendar.getInstance())
                .setDecorView(null)
                .build();
        pvTime.show();
    }
    private void getScannerHistory(int pn) {
        if (!ListUtils.isEmpty(scannerHistoryList)){
            if (pn==1){
                scannerHistoryList.clear();
                scannerHistoryAdapter.notifyDataSetChanged();
            }

        }
        mPresenter.getScannerHistory(pn,star_date,end_date);

    }

    @Override
    protected ScannerHistoryPresenter loadPresenter() {
        return new ScannerHistoryPresenter();
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
        scannerHistoryAdapter = new ScannerHistoryAdapter(this, scannerHistoryList);
        rk_list.setAdapter(scannerHistoryAdapter);
        scannerHistoryAdapter.setOnItemClickListener(this);
        end_date=getTime(new Date(System.currentTimeMillis()))+"03:00:00";//接口上传开始时间
        star_date=getTime(new Date(System.currentTimeMillis()-60*24*60*1000))+"23:00:00";//接口上传结束时间
        tv_end.setText(getTime(new Date(System.currentTimeMillis()))+"03:00");
        tv_star.setText(getTime(new Date(System.currentTimeMillis()-60*24*60*1000))+"23:00");
        getScannerHistory(1);
    }

    public Context getContext() {
        return this;
    }

    public static String getTimes(Date date) {//
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
    public static String getTime(Date date) {//
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
        return format.format(date);
    }
    public static String setTime(Date date) {//
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.scanner_history_layout;
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        getScannerHistory(++pageNo);

    }
    public void  getScannerHistory_err(){
        if (refreshLayout.isEnableLoadmore()) {
            refreshLayout.finishLoadmore();
        }
    }
    public void getScannerHistory_Success(CustomApiResult<ScannerHistory> response) {
        if (refreshLayout.isEnableLoadmore()) {
            refreshLayout.finishLoadmore();
        }
        if (response.getData()==null){
            return;
        }
        if (response.getErr() == 0) {
            tv_total.setText("扫码数："+response.getData().getTotal());

            if (response.getData().getItems().size() > 0) {
                scannerHistoryList.addAll(response.getData().getItems());
                scannerHistoryAdapter.notifyDataSetChanged();

            } else {
                if (!response.getData().getPn().equals("1")) {
                    Toast.makeText(this, "数据加载完毕", Toast.LENGTH_LONG).show();
                }
            }

        } else {
            if (!response.getData().getPn().equals("1")) {
                Toast.makeText(this, "数据加载完毕", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent=new Intent(this,ScannerHistoryDetalisActivity.class);
        intent.putExtra("storeId",scannerHistoryList.get(position).getStoreId()+"");
        intent.putExtra("staetTime",star_date);
        intent.putExtra("endTime",end_date);
        intent.putExtra("storeName",scannerHistoryList.get(position).getStoreName());
        intent.putExtra("scannerHouse",scannerHistoryList.get(position).getScannerHouse());
        intent.putExtra("storePhone",scannerHistoryList.get(position).getStorePhone());
        startActivity(intent);
    }
}
