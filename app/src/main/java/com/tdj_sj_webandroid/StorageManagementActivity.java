package com.tdj_sj_webandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.tdj_sj_webandroid.adapter.BaseRecyclerViewAdapter;
import com.tdj_sj_webandroid.adapter.StorageManagementAdapter;
import com.tdj_sj_webandroid.base.BaseActivity;
import com.tdj_sj_webandroid.model.CustomApiResult;
import com.tdj_sj_webandroid.model.StorageManagement;
import com.tdj_sj_webandroid.mvp.presenter.IPresenter;
import com.tdj_sj_webandroid.mvp.presenter.StorageManagementPresenter;
import com.tdj_sj_webandroid.utils.Constants;
import com.zhouyou.http.exception.ApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StorageManagementActivity extends BaseActivity<StorageManagementPresenter> implements BaseRecyclerViewAdapter.OnItemClickListener {
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
    @BindView(R.id.tv_cancle)
    TextView tv_cancle;
    private BroadcastReceiver scanReceiver;
    private boolean b=false;
    private List<StorageManagement> list=new ArrayList();
    private StorageManagementAdapter storageManagementAdapter;

    @OnClick({R.id.tv_cancle,R.id.right_text,R.id.btn_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_cancle:
                b=true;
                tv_cancle.setTextColor(getResources().getColor(R.color.text_));
                break;
            case R.id.right_text:
                if (TextUtils.isEmpty(search_edit.getText().toString())){
                    return;
                }
                mPresenter.get_scann(search_edit.getText().toString(),"in");
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }


    @Override
    protected StorageManagementPresenter loadPresenter() {
        return new StorageManagementPresenter();
    }

    @Override
    protected void initData() {
        //新大陆
        Intent intent = new Intent("ACTION_BARCODE_CFG");
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
                if ("ok".equals(scanStatus)) {
                    //成功
                    LogUtils.i(scanResult_1);
                        search_edit.setText("");
                        search_edit.setText(scanResult_1);
                    search_edit.setSelection(search_edit.getText().length());
                    LogUtils.i(b);
                    if (b){
                        mPresenter.get_scann(scanResult_1,"out");
                    }else {
                        mPresenter.get_scann(scanResult_1,"in");
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
        ImmersionBar.with(this).statusBarDarkFont(true).keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                .init();
        LinearLayoutManager layout = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        rk_list.setLayoutManager(layout);
        storageManagementAdapter=new StorageManagementAdapter(this,list);
        rk_list.setAdapter(storageManagementAdapter);
        storageManagementAdapter.setOnItemClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.storage_managemet_layout;
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
    /**

     * 监听系统静音模式

     * @param mContext

     */

    private void modeIndicater(Context mContext){

        AudioManager am = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);

        final int ringerMode = am.getRingerMode();

        switch (ringerMode) {

            case AudioManager.RINGER_MODE_NORMAL://普通模式

                playFromRawFile(mContext);

                break;

            case AudioManager.RINGER_MODE_VIBRATE://静音模式

                break;

            case AudioManager.RINGER_MODE_SILENT://震动模式

                break;

        }

    }

    /**

     * 提示音

     * @param mContext

     */

    private void playFromRawFile(Context mContext) {

        try {

            MediaPlayer player = new MediaPlayer();

            AssetFileDescriptor file = mContext.getResources().openRawResourceFd(R.raw.aa);

            try {

                player.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());

                file.close();

                if (!player.isPlaying()){

                    player.prepare();

                    player.start();

                }

            } catch (IOException e) {

                player = null;

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    public void get_scann_Success(CustomApiResult<StorageManagement> result) {
        LogUtils.i(b);
        if (b){
            b=false;
            tv_cancle.setTextColor(getResources().getColor(R.color.text_gonees));
                if (list.size()>0){
                    for (int i=0;i<list.size();i++){
                        if (list.get(i).getSku().equals(result.getData().getSku())){
                            list.remove(i);
                            LogUtils.i(i);

                            break;
                        }

                    }
                    storageManagementAdapter.notifyDataSetChanged();



                }


        }else {
            if (result.getErr()==0){
                list.add(result.getData());
                storageManagementAdapter.notifyDataSetChanged();


            }else {
                modeIndicater(this);
            }

        }
        tv_num.setText("已入库："+list.size());

    }

    public Context getContext() {
        return this;
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, View v, int position) {
        Intent intent=new Intent(getContext(), WebViewActivity.class);

        intent.putExtra("url", Constants.URL+"order/info.do?code="+list.get(position).getSku());
        startActivity(intent);
    }

    public void get_scann_onError(ApiException e) {
        if (b) {
            b = false;
            tv_cancle.setTextColor(getResources().getColor(R.color.text_gonees));
        }
    }
}
