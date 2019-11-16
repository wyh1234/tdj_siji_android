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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.tdj_sj_webandroid.adapter.NuclearGoodsAdapter;
import com.tdj_sj_webandroid.adapter.StorageManagementAdapter;
import com.tdj_sj_webandroid.base.BaseActivity;
import com.tdj_sj_webandroid.model.CustomApiResult;
import com.tdj_sj_webandroid.model.Resume;
import com.tdj_sj_webandroid.model.StorageManagement;
import com.tdj_sj_webandroid.mvp.presenter.NuclearGoodsPresenter;
import com.tdj_sj_webandroid.utils.GeneralUtils;
import com.tdj_sj_webandroid.utils.PlayVoice;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NuclearGoodsActivity extends BaseActivity<NuclearGoodsPresenter> implements OnLoadmoreListener {
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.right_text)
    TextView right_text;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.rk_list)
    RecyclerView rk_list;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.tv_qx)
    TextView tv_qx;
    @BindView(R.id.tv_title1)
    TextView tv_title1;
    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.ll)
    LinearLayout ll;
    public int pageNo = 1;//翻页计数器
    private BroadcastReceiver scanReceiver;
    private List<StorageManagement> list = new ArrayList();
    private NuclearGoodsAdapter storageManagementAdapter;
    private int total = 0;
    private boolean b=false;
    @OnClick({R.id.tv_qx,R.id.right_text,R.id.btn_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_qx:
                b=true;
                tv_qx.setTextColor(getResources().getColor(R.color.text_));
                break;
            case R.id.right_text:
                if (TextUtils.isEmpty(search_edit.getText().toString())){
                    return;
                }
                if (b){
                    mPresenter.get_scann(search_edit.getText().toString(),"out",getIntent().getStringExtra("customer_id"),getIntent().getStringExtra("customer_code"));
                }else {
                    mPresenter.get_scann(search_edit.getText().toString(),"in",getIntent().getStringExtra("customer_id"),getIntent().getStringExtra("customer_code"));
                }
                break;
            case R.id.btn_back:
                EventBus.getDefault().post(new Resume());
                finish();
                break;
        }
    }
    @Override
    protected NuclearGoodsPresenter loadPresenter() {
        return new NuclearGoodsPresenter();
    }

    @Override
    protected void initData() {
        //新大陆
        Intent intent = new Intent("ACTION_BAR_SCANCFG");
        intent.putExtra("EXTRA_SCAN_MODE", 3);
        intent.putExtra("CODE_ID","EAN13");
        intent.putExtra("PROPERTY","TrsmtChkChar");
        intent.putExtra("VALUE","1");
        sendBroadcast(intent);
        scanReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String scanResult_1 = intent.getStringExtra("SCAN_BARCODE1");
                final String scanResult_2 = intent.getStringExtra("SCAN_BARCODE2");
                final int barcodeType = intent.getIntExtra("SCAN_BARCODE_TYPE", -1); // -1:unknown
                final String scanStatus = intent.getStringExtra("SCAN_STATE");
                LogUtils.i(scanStatus);
                LogUtils.i(scanResult_1);
                if ("ok".equals(scanStatus)) {
                    //成功
                    LogUtils.i(scanResult_1);
                    search_edit.setText("");
                    search_edit.setText(scanResult_1);
                    search_edit.setSelection(search_edit.getText().length());
                    LogUtils.i(b);
                    if (b){
                        mPresenter.get_scann(scanResult_1,"out",getIntent().getStringExtra("customer_id"),getIntent().getStringExtra("customer_code"));
                    }else {
                        mPresenter.get_scann(scanResult_1,"in",getIntent().getStringExtra("customer_id"),getIntent().getStringExtra("customer_code"));
                    }


                } else {
                    //失败如超时等
                    LogUtils.i("scanStatus" + scanStatus);
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
        storageManagementAdapter=new NuclearGoodsAdapter(this,list);
        rk_list.setAdapter(storageManagementAdapter);
        getData(1);
    }
    protected  void getData(int pn){
        mPresenter.get_scann(pn,getIntent().getStringExtra("customer_id"));

    }
    @Override
    protected int getLayoutId() {
        return R.layout.nuclear_goods_layout;
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        getData(++pageNo);
    }

    public Context getContext() {
        return this;
    }


    public void get_scann_onError(ApiException e) {
        if (b) {
            b = false;
            tv_qx.setTextColor(getResources().getColor(R.color.text_gonees));
        }
    }
    public void get_scann_Success(CustomApiResult<StorageManagement> result) {
        tv_qx.setTextColor(getResources().getColor(R.color.text_gonees));
        if (result.getErr()==0){
            if (b){
                b=false;
                if (list.size()>0){

                    Iterator<StorageManagement> iterator = list.iterator();
                    while (iterator.hasNext()) {
                        StorageManagement item = iterator.next();
                        if (item.getSku().equals(result.getData().getSku())) {
                            iterator.remove();
                        }
                    }
                    LogUtils.i(list);
                    storageManagementAdapter.notifyDataSetChanged();
                    LogUtils.i(total);
                    tv_num.setText("已核货："+(--total));


                }
                PlayVoice.playVoice(this,R.raw.quxiaoshichenggonggai);

            }else {
                PlayVoice.playVoice(this,R.raw.hehuochenggong);
                if (result.getErr()==0){
                    list.add(0,result.getData());
                    storageManagementAdapter.notifyDataSetChanged();
                    tv_num.setText("已核货："+(++total));

                }



            }
        }else {
            if (b){
                b=false;
            }
        }
        if (result.getErr()==1){
            PlayVoice.playVoice(this,R.raw.hehuoshibai);
        }
        if (result.getErr() == 8||result.getErr() == 2){
            PlayVoice.playVoice(this,R.raw.quxiaoshibaigai);
        }
        if (result.getErr() == 4){
            PlayVoice.playVoice(this,R.raw.saomashibaigai);
        }
        if (result.getErr() == 6) {
            PlayVoice.playVoice(this,R.raw.yijinghehuo);
        }
        tv_msg.setVisibility(View.GONE);
        if (result.getErr()==5){
            PlayVoice.playVoice(this,R.raw.chuanxian);
        }

        if (result.getErr()==13){
            PlayVoice.playVoice(this,R.raw.gaidingdanyiquxiao);
        }

        if (!GeneralUtils.isNullOrZeroLenght(result.getMsg())){
            tv_msg.setVisibility(View.VISIBLE);
            tv_msg.setText(result.getMsg());
        }
        if (list.size()>0){
            mStateView.showContent();
        }else {
            mStateView.showEmpty();
        }
    }

    public void get_scann__home_Success(CustomApiResult<List<StorageManagement>> response) {
        if (refreshLayout.isEnableLoadmore()) {
            refreshLayout.finishLoadmore();
        }
        tv_title1.setText(response.getRegionNo());
        if (response.getErr()==0){

            if (response.getData().size()>0&&response.getData()!=null){
                mStateView.showContent();
                list.addAll(response.getData());
                tv_num.setText("已核货："+list.size());
                storageManagementAdapter.notifyDataSetChanged();

            }else {
                if (pageNo!=1){
                    Toast.makeText(this,"数据加载完毕",Toast.LENGTH_LONG).show();
                }
                else {
                    mStateView.showEmpty();
                }
            }
            total=response.getTotal();
            tv_num.setText("已核货："+response.getTotal());

        }else {
            if (pageNo!=1){
                Toast.makeText(this,"数据加载完毕",Toast.LENGTH_LONG).show();
            }
        }


    }

    private void registerReceiver() {
        IntentFilter mFilter = new IntentFilter("nlscan.action.SCANNER_RESULT");
        registerReceiver(scanReceiver, mFilter);
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (GeneralUtils.isHideInput(view, ev)) {
                GeneralUtils. hideSoftInput(view.getWindowToken(),this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    public View getStateViewRoot() {
        return ll;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new Resume());
    }
}
