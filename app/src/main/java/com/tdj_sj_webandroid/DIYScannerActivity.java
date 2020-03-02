package com.tdj_sj_webandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.tdj_sj_webandroid.adapter.StorageManagementAdapter;
import com.tdj_sj_webandroid.base.BaseActivity;
import com.tdj_sj_webandroid.model.CustomApiResult;
import com.tdj_sj_webandroid.model.StorageManagement;
import com.tdj_sj_webandroid.mvp.presenter.DIYScannerPresenter;
import com.tdj_sj_webandroid.utils.GeneralUtils;
import com.tdj_sj_webandroid.utils.PlayVoice;
import com.tdj_sj_webandroid.utils.ToastUtils;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DIYScannerActivity extends BaseActivity<DIYScannerPresenter> implements OnLoadmoreListener {
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.right_text)
    TextView right_text;
    @BindView(R.id.rk_list)
    RecyclerView rk_list;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.tv_qx)
    TextView tv_qx;
    @BindView(R.id.tv_title1)
    TextView tv_title1;
    public int pageNo = 1;//翻页计数器
    private BroadcastReceiver scanReceiver;
    private boolean b = false;
    private List<StorageManagement> list = new ArrayList();
    private StorageManagementAdapter storageManagementAdapter;

    private String SCANACTION = "com.android.server.scannerservice.broadcast";
    private final String TYPE_BARCODE_BROADCAST_ACTION = "action_barcode_broadcast";


    @OnClick({R.id.tv_qx, R.id.right_text, R.id.btn_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_qx:
                b = true;
                tv_qx.setTextColor(getResources().getColor(R.color.text_));
                break;
            case R.id.right_text:
                if (TextUtils.isEmpty(search_edit.getText().toString())) {
                    return;
                }
                if (b) {
                    mPresenter.get_scann(search_edit.getText().toString(), "out",GeneralUtils.isNullOrZeroLenght(getIntent().getStringExtra("flag"))?"":getIntent().getStringExtra("flag"));
                } else {
                    mPresenter.get_scann(search_edit.getText().toString(), "in",GeneralUtils.isNullOrZeroLenght(getIntent().getStringExtra("flag"))?"":getIntent().getStringExtra("flag"));
                }
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }
    @Override
    protected DIYScannerPresenter loadPresenter() {
        return new DIYScannerPresenter();
    }

    @Override
    protected void initData() {
        //东大集
        Intent intent = new Intent("com.seuic.scanner.action.PARAM_SETTINGS").putExtra(TYPE_BARCODE_BROADCAST_ACTION, SCANACTION);
        intent.putExtra("number",0x10a);
        intent.putExtra("value",30);
        sendBroadcast(intent);


        scanReceiver =new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(SCANACTION)) {
                    {
                        //成功
                        LogUtils.i(intent.getStringExtra("scannerdata"));
                        search_edit.setText("");
                        search_edit.setText(intent.getStringExtra("scannerdata"));
                        search_edit.setSelection(search_edit.getText().length());
                        LogUtils.i(b);
                        if (b){
                            mPresenter.get_scann(intent.getStringExtra("scannerdata"),"out",GeneralUtils.isNullOrZeroLenght(getIntent().getStringExtra("flag"))?"":getIntent().getStringExtra("flag"));
                        }else {
                            mPresenter.get_scann(intent.getStringExtra("scannerdata"),"in",GeneralUtils.isNullOrZeroLenght(getIntent().getStringExtra("flag"))?"":getIntent().getStringExtra("flag"));
                        }

                    }
                }
            }
        };
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        refreshLayout.setOnLoadmoreListener(this);
        ImmersionBar.with(this).statusBarDarkFont(true).keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                .init();
        LinearLayoutManager layout = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        rk_list.setLayoutManager(layout);
        storageManagementAdapter = new StorageManagementAdapter(this, list);
        rk_list.setAdapter(storageManagementAdapter);
        getData(1);
    }
    protected void getData(int pn) {
        mPresenter.get_scann(pn,GeneralUtils.isNullOrZeroLenght(getIntent().getStringExtra("flag"))?"":getIntent().getStringExtra("flag"));

    }
    @Override
    protected int getLayoutId() {
        return R.layout.diy_scanner_layout;
    }
    private void registerReceiver() {
        IntentFilter intentFilter=new IntentFilter(SCANACTION);
        intentFilter.setPriority(Integer.MAX_VALUE);
        registerReceiver(scanReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unRegisterReceiver();
    }

    private void unRegisterReceiver() {
        try {
            unregisterReceiver(scanReceiver);
        } catch (Exception e) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver();
    }
    public void get_scann_Success(CustomApiResult<StorageManagement> result) {
        tv_qx.setTextColor(getResources().getColor(R.color.text_gonees));
        if (result.getErr() == 0) {
            if (b) {
                b = false;
                if (list.size() > 0) {
                    Iterator<StorageManagement> iterator = list.iterator();
                    while (iterator.hasNext()) {
                        StorageManagement item = iterator.next();
                        if (item.getSku().equals(result.getData().getSku())) {
                            iterator.remove();
                        }
                    }
                    storageManagementAdapter.notifyDataSetChanged();
                }
                PlayVoice.playVoice(this,R.raw.quxiaochenggong);

            } else {
                PlayVoice.playVoice(this,R.raw.rukuchenggong);
                if (result.getErr() == 0) {

                    list.add(0, result.getData());
                    storageManagementAdapter.notifyDataSetChanged();
                }


            }
        } else {
            if (b) {
                b = false;
            }
            ToastUtils.showToast(this,result.getMsg());
        }
        if (result.getErr() == 1) {
            PlayVoice.playVoice(this,R.raw.saomashibai);
        }
        if (result.getErr() == 2) {
            PlayVoice.playVoice(this,R.raw.saomachaoqu);
        }
        if (result.getErr() == 8){
            PlayVoice.playVoice(this,R.raw.quxiaoshibai);
        }
        if (result.getErr() == 9){
            PlayVoice.playVoice(this,R.raw.yijiruku);
        }

        if (result.getErr()==13){
            PlayVoice.playVoice(this,R.raw.gaidingdanyiquxiao);
        }
        if (result.getErr()==15){
            PlayVoice.playVoice(this,R.raw.feidangtain);
        }

    }
    public void get_scann__home_Success(CustomApiResult<List<StorageManagement>> response) {
        if (refreshLayout.isEnableLoadmore()) {
            refreshLayout.finishLoadmore();
        }
        tv_title1.setText(response.getRegionNo());
        if (response.getErr() == 0) {

            if (response.getData().size() > 0) {
                list.addAll(response.getData());
                storageManagementAdapter.notifyDataSetChanged();

            } else {
                if (response.getPn() != 1) {
                    Toast.makeText(this, "数据加载完毕", Toast.LENGTH_LONG).show();
                }
            }

        } else {
            if (response.getPn() != 1) {
                Toast.makeText(this, "数据加载完毕", Toast.LENGTH_LONG).show();
            }
        }


    }
    public void get_scann_onError(ApiException e) {
        if (b) {
            b = false;
            tv_qx.setTextColor(getResources().getColor(R.color.text_gonees));
        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (GeneralUtils.isHideInput(view, ev)) {
                GeneralUtils.hideSoftInput(view.getWindowToken(), this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        getData(++pageNo);
    }

    public Context getContext() {
        return this;
    }
}
