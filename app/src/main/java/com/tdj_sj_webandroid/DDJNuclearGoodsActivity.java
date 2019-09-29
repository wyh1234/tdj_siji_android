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
import com.tdj_sj_webandroid.base.BaseActivity;
import com.tdj_sj_webandroid.model.CustomApiResult;
import com.tdj_sj_webandroid.model.Resume;
import com.tdj_sj_webandroid.model.StorageManagement;
import com.tdj_sj_webandroid.mvp.presenter.DDJNuclearGoodsPresenter;
import com.tdj_sj_webandroid.mvp.presenter.NuclearGoodsPresenter;
import com.tdj_sj_webandroid.utils.GeneralUtils;
import com.tdj_sj_webandroid.utils.PlayVoice;
import com.tdj_sj_webandroid.utils.player.SoundPlayer;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DDJNuclearGoodsActivity extends BaseActivity<DDJNuclearGoodsPresenter> implements OnLoadmoreListener {
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
    private boolean b = false;

    private String SCANACTION = "com.android.server.scannerservice.broadcast";
    private final String ACTION_SCANNER_APP_SETTINGS = "com.android.scanner.service_settings";
    private final String TYPE_BARCODE_BROADCAST_ACTION = "action_barcode_broadcast";
    private final String TYPE_BOOT_START = "boot_start";
    private SoundPlayer soundUtils;
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
                    mPresenter.get_scann(search_edit.getText().toString(), "out", getIntent().getStringExtra("customer_id"),getIntent().getStringExtra("customer_code"));
                } else {
                    mPresenter.get_scann(search_edit.getText().toString(), "in", getIntent().getStringExtra("customer_id"),getIntent().getStringExtra("customer_code"));
                }
                break;
            case R.id.btn_back:
                EventBus.getDefault().post(new Resume());
                finish();
                break;
        }
    }

    @Override
    protected DDJNuclearGoodsPresenter loadPresenter() {
        return new DDJNuclearGoodsPresenter();
    }

    @Override
    protected void initData() {
        soundUtils = new SoundPlayer(this);
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
                            mPresenter.get_scann(intent.getStringExtra("scannerdata"),"out",getIntent().getStringExtra("customer_id"),getIntent().getStringExtra("customer_code"));
                        }else {
                            mPresenter.get_scann(intent.getStringExtra("scannerdata"),"in",getIntent().getStringExtra("customer_id"),getIntent().getStringExtra("customer_code"));
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
        storageManagementAdapter = new NuclearGoodsAdapter(this, list);
        rk_list.setAdapter(storageManagementAdapter);
        getData(1);
    }

    protected void getData(int pn) {
        mPresenter.get_scann(pn, getIntent().getStringExtra("customer_id"));

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
                    LogUtils.i(list);
                    storageManagementAdapter.notifyDataSetChanged();
                    LogUtils.i(total);
                    tv_num.setText("已核货：" + (--total));

                }
//                soundUtils.play(R.raw.quxiaoshichenggonggai, false);

                PlayVoice.playVoice(this,R.raw.quxiaoshichenggonggai);

            } else {
//                soundUtils.play(R.raw.hehuochenggong, false);
                PlayVoice.playVoice(this,R.raw.hehuochenggong);
                if (result.getErr() == 0) {
                    list.add(0, result.getData());
                    storageManagementAdapter.notifyDataSetChanged();
                    tv_num.setText("已核货：" + (++total));

                }


            }
        } else {
            if (b) {
                b = false;
            }
        }
        if (result.getErr() == 1) {
            soundUtils.play(R.raw.hehuoshibai, false);
            PlayVoice.playVoice(this,R.raw.hehuoshibai);
        }

        if (result.getErr() == 8 || result.getErr() == 2) {
            soundUtils.play(R.raw.quxiaoshibaigai, false);
            PlayVoice.playVoice(this,R.raw.quxiaoshibaigai);
        }
        if (result.getErr() == 4) {
//            soundUtils.play(R.raw.saomashibaigai, false);
            PlayVoice.playVoice(this,R.raw.saomashibaigai);

        }
        if (result.getErr() == 6) {
//            soundUtils.play(R.raw.yijinghehuo, false);
            PlayVoice.playVoice(this,R.raw.yijinghehuo);
        }
        tv_msg.setVisibility(View.GONE);
        if (result.getErr() == 5) {

//            soundUtils.play(R.raw.chuanxian, false);
            PlayVoice.playVoice(this,R.raw.chuanxian);
        }

        if (result.getErr()==13){
//            soundUtils.play(R.raw.gaidingdanyiquxiao, false);
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
        if (response.getErr() == 0) {

            if (response.getData().size()>0){
                list.addAll(response.getData());
                tv_num.setText("已核货：" + list.size());
                storageManagementAdapter.notifyDataSetChanged();
                mStateView.showContent();
            } else {
                if (pageNo != 1) {
                    Toast.makeText(this, "数据加载完毕", Toast.LENGTH_LONG).show();
                }else {
                    mStateView.showEmpty();
                }
            }
            total = response.getTotal();
            tv_num.setText("已核货：" + response.getTotal());

        } else {
            if (pageNo != 1) {
                Toast.makeText(this, "数据加载完毕", Toast.LENGTH_LONG).show();
            }
        }


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
    public View getStateViewRoot() {
        return ll;

    }
}
