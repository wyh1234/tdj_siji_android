package com.tdj_sj_webandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
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
import com.tdj_sj_webandroid.adapter.BaseRecyclerViewAdapter;
import com.tdj_sj_webandroid.adapter.StorageManagementAdapter;
import com.tdj_sj_webandroid.base.BaseActivity;
import com.tdj_sj_webandroid.model.CustomApiResult;
import com.tdj_sj_webandroid.model.StorageManagement;
import com.tdj_sj_webandroid.mvp.presenter.DDJStorageManagementPresenter;
import com.tdj_sj_webandroid.mvp.presenter.SunMiStorageManagementPresenter;
import com.tdj_sj_webandroid.utils.Constants;
import com.tdj_sj_webandroid.utils.GeneralUtils;
import com.tdj_sj_webandroid.utils.player.SoundPlayer;
import com.zhouyou.http.exception.ApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SunMiStorageManagementActivity extends BaseActivity<SunMiStorageManagementPresenter> implements BaseRecyclerViewAdapter.OnItemClickListener, OnLoadmoreListener {
    public int pageNo = 1;//翻页计数器
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
    private boolean b = false;
    private List<StorageManagement> list = new ArrayList();
    private StorageManagementAdapter storageManagementAdapter;
    private int total = 0;

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
                    mPresenter.get_scann(search_edit.getText().toString(), "out");
                } else {
                    mPresenter.get_scann(search_edit.getText().toString(), "in");
                }
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }


    @Override
    protected SunMiStorageManagementPresenter loadPresenter() {
        return new SunMiStorageManagementPresenter();
    }

    protected void getData(int pn) {
        mPresenter.get_scann(pn);

    }

    @Override
    protected void initData() {
        soundUtils = new SoundPlayer(this);
        //东大集
        Intent intent = new Intent("com.summi.scan");
        intent.setPackage("com.sunmi.sunmiqrcodescanner");
        startActivityForResult(intent, 1);
    /*    Intent intent = new Intent("com.seuic.scanner.action.PARAM_SETTINGS").putExtra(TYPE_BARCODE_BROADCAST_ACTION, SCANACTION);
        intent.putExtra("number", 0x10a);
        intent.putExtra("value", 30);
        sendBroadcast(intent);*/


/*
        scanReceiver = new BroadcastReceiver() {
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
                        if (b) {
                            mPresenter.get_scann(intent.getStringExtra("scannerdata"), "out");
                        } else {
                            mPresenter.get_scann(intent.getStringExtra("scannerdata"), "in");
                        }

                    }
                }
            }
        };
*/


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
        storageManagementAdapter.setOnItemClickListener(this);
        getData(1);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.storage_managemet_layout;
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
                    LogUtils.i(total);
                    tv_num.setText("已入库：" + (--total));
//                    soundUtils.playSound(1,SoundPlayer.SINGLE_PLAY);
                    soundUtils.play(R.raw.quxiaochenggong, false);
//                    modeIndicater(this,1);

                }


            } else {
//            soundUtils.playSound(0,SoundPlayer.SINGLE_PLAY);
                soundUtils.play(R.raw.rukuchenggong, false);
                if (result.getErr() == 0) {

                    list.add(0, result.getData());
                    storageManagementAdapter.notifyDataSetChanged();
                    tv_num.setText("已入库：" + (++total));

                }


            }
        } else {
            if (b) {
                b = false;
            }
        }
        if (result.getErr() == 1) {
//            soundUtils.playSound(3,SoundPlayer.SINGLE_PLAY);
            soundUtils.play(R.raw.saomashibai, false);
//            modeIndicater(this,3);
        }
        if (result.getErr() == 2) {
//            soundUtils.playSound(2,SoundPlayer.SINGLE_PLAY);
            soundUtils.play(R.raw.saomachaoqu, false);
//            modeIndicater(this,4);
        }
        if (result.getErr() == 8){
            soundUtils.play(R.raw.quxiaoshibai, false);
        }
        if (result.getErr() == 9){
            soundUtils.play(R.raw.yijiruku, false);
        }


    }

    public Context getContext() {
        return this;
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, View v, int position) {
        Intent intent = new Intent(getContext(), WebViewActivity.class);

        intent.putExtra("url", Constants.URL + "order/info.do?code=" + list.get(position).getSku());
        startActivity(intent);
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

    public void get_scann__home_Success(CustomApiResult<List<StorageManagement>> response) {
        if (refreshLayout.isEnableLoadmore()) {
            refreshLayout.finishLoadmore();
        }
        tv_title1.setText(response.getRegionNo());
        if (response.getErr() == 0) {

            if (response.getData().size() > 0) {
                list.addAll(response.getData());
                tv_num.setText("已入库：" + list.size());
                storageManagementAdapter.notifyDataSetChanged();

            } else {
                if (response.getPn() != 1) {
                    Toast.makeText(this, "数据加载完毕", Toast.LENGTH_LONG).show();
                }
            }
            total = response.getTotal();
            tv_num.setText("已入库：" + response.getTotal());

        } else {
            if (response.getPn() != 1) {
                Toast.makeText(this, "数据加载完毕", Toast.LENGTH_LONG).show();
            }
        }


    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        getData(++pageNo);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && data != null) {
            Bundle bundle = data.getExtras();
            LogUtils.i(bundle.getSerializable("data"));
            ArrayList result = (ArrayList) bundle .getSerializable("data");

            Iterator it = result.iterator();
            while (it.hasNext()) {
                HashMap hashMap = (HashMap) it.next();
               LogUtils.i( hashMap.get("TYPE"));//这个是扫码的类型
                LogUtils.i( hashMap.get("VALUE"));//这个是扫码的类型
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
