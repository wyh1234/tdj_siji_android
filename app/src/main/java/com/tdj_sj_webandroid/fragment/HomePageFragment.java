package com.tdj_sj_webandroid.fragment;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.apkfuns.logutils.LogUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tdj_sj_webandroid.MainTabActivity;

import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.StorageManagementActivity;
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

import java.io.IOException;

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
    private String SCANACTION = "com.tdj.server.scannerservice.broadcast";
    private final String ACTION_SCANNER_APP_SETTINGS = "com.android.scanner.service_settings";
    private final String TYPE_BARCODE_BROADCAST_ACTION = "action_barcode_broadcast";
    private final String TYPE_BOOT_START = "boot_start";
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
        //第二种方式，如果toolbar有颜色，要设置状态栏颜色
        rxPermissions = new RxPermissions(getActivity());
        LinearLayoutManager layout = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layout);

        rxPermissions.requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(new Consumer<Permission>() {
            @Override
            public void accept(Permission f) throws Exception {
                LogUtils.i(f.granted);
            }
        });


    }

    @Override
    protected void loadData() {
        mPresenter.get_menus();


//东大集
//        Intent intent = new Intent(ACTION_SCANNER_APP_SETTINGS).putExtra(TYPE_BOOT_START, true);
//        Intent intent = new Intent(ACTION_SCANNER_APP_SETTINGS).putExtra(TYPE_BARCODE_BROADCAST_ACTION, SCANACTION);
//        getActivity().sendBroadcast(intent);
//
//
//         scanReceiver =new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                if (intent.getAction().equals(SCANACTION)) {
//                    {
//
//                        mPresenter.get_scann(intent.getStringExtra("scannerdata"));
//                        LogUtils.i(intent.getStringExtra("scannerdata"));
//
//                    }
//                }
//            }
//        };

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
    public void get_scann_Success(int code) {
        if (code==0){
            Toast.makeText(getContext(),"code",Toast.LENGTH_LONG).show();
        }else {
            modeIndicater(activity.getApplicationContext());
        }

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
        }else if (getHomeInfo().getMenus().get(position).getMenuDesc().equals("smrk")){
            Intent intent = new Intent(getContext(), StorageManagementActivity.class);
            startActivity(intent);
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

    private void registerReceiver(){
//    新大陆
//    IntentFilter mFilter= new IntentFilter("nlscan.action.SCANNER_RESULT");
//        getActivity().registerReceiver(scanReceiver, mFilter);
        // 东大集
//        IntentFilter intentFilter=new IntentFilter(SCANACTION);
//        intentFilter.setPriority(Integer.MAX_VALUE);
//        getActivity().registerReceiver(scanReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
//  新大陆
//  unRegisterReceiver();
        // 东大集
//        unRegisterReceiver();
    }
    private void unRegisterReceiver()
    {
        // 东大集
//                try {
//         getActivity(). unregisterReceiver(scanReceiver);
//        } catch (Exception e) {
//        }

//新大陆
//        try {
//            getActivity(). unregisterReceiver(scanReceiver);
//        } catch (Exception e) {
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
//    新大陆
//    registerReceiver();
        // 东大集
//        registerReceiver();
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
}
