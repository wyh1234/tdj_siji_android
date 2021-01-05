package com.tdj_sj_webandroid.fragment;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tdj_sj_webandroid.DDJStorageManagementActivity;
import com.tdj_sj_webandroid.DIYScannerActivity;
import com.tdj_sj_webandroid.LyManagementActivity;
import com.tdj_sj_webandroid.MainTabActivity;

import com.tdj_sj_webandroid.ManualScannerActivity;
import com.tdj_sj_webandroid.NuclearGoodsHotelActivity;
import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.StorageManagementActivity;
import com.tdj_sj_webandroid.SunMiStorageManagementActivity;
import com.tdj_sj_webandroid.WebViewActivity;
import com.tdj_sj_webandroid.adapter.BaseRecyclerViewAdapter;
import com.tdj_sj_webandroid.adapter.HomePageFragmentAdapter;
import com.tdj_sj_webandroid.android.os.SystemProperties;
import com.tdj_sj_webandroid.base.BaseFrgment;

import com.tdj_sj_webandroid.bluetooth.MainActivity;
import com.tdj_sj_webandroid.contract.TDJContract;
import com.tdj_sj_webandroid.model.AppUpdate;
import com.tdj_sj_webandroid.model.HomeInfo;
import com.tdj_sj_webandroid.mvp.presenter.HomePageFragmentPresenter;
import com.tdj_sj_webandroid.utils.Constants;
import com.tdj_sj_webandroid.utils.appupdate.DownloadManager;
import com.tdj_sj_webandroid.utils.appupdate.OnDownloadListener;
import com.tdj_sj_webandroid.utils.appupdate.UpdateConfiguration;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

import static android.app.Activity.RESULT_OK;

public class HomePageFragment extends BaseFrgment<HomePageFragmentPresenter> implements TDJContract.HomePageFragmentView, BaseRecyclerViewAdapter.OnItemClickListener, OnDownloadListener {
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
        mPresenter.get_versions_store();


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
        }else if (getHomeInfo().getMenus().get(position).getMenuDesc().equals("smrk")){
            LogUtils.e(Build.MODEL);
            Intent intent;
//            intent = new Intent(getContext(), SunMiStorageManagementActivity.class);
            if (Build.MODEL.equals("NLS-MT90")){
                 intent = new Intent(getContext(), StorageManagementActivity.class);

            }/*else if (Build.MODEL.equals("L2-H")){
                intent = new Intent(getContext(), SunMiStorageManagementActivity.class);
            }*/else {
                intent = new Intent(getContext(), DDJStorageManagementActivity.class);
            }
            startActivity(intent);
        }else if (getHomeInfo().getMenus().get(position).getMenuDesc().equals("smhh")){
            Intent intent=new Intent(getContext(), NuclearGoodsHotelActivity.class);
            intent.putExtra("url", Constants.URL1+getHomeInfo().getMenus().get(position).getMenuUrl());
            startActivity(intent);
        }else if (getHomeInfo().getMenus().get(position).getMenuDesc().equals("zzsmrk")){
            if (!Build.MODEL.equals("NLS-MT90")){
                Intent  diyScanner_intent = new Intent(getContext(), DIYScannerActivity.class);
                startActivity(diyScanner_intent);
              }

        }else if (getHomeInfo().getMenus().get(position).getMenuDesc().equals("rgsmrk")){
            Intent intent;
            if (!Build.MODEL.equals("NLS-MT90")){
                Intent  manualScanner_intent = new Intent(getContext(), ManualScannerActivity.class);
                startActivity(manualScanner_intent);
            }
        }else if (getHomeInfo().getMenus().get(position).getMenuDesc().equals("jhcsmrk")){
            Intent intent;
            if (!Build.MODEL.equals("NLS-MT90")){
                Intent  manualScanner_intent = new Intent(getContext(), ManualScannerActivity.class);
                manualScanner_intent.putExtra("flag","sale");
                startActivity(manualScanner_intent);
            }
        }else if (getHomeInfo().getMenus().get(position).getMenuDesc().equals("jhczzsmrk")){
            Intent intent;
            if (!Build.MODEL.equals("NLS-MT90")){
                Intent  diyScanner_intent = new Intent(getContext(), DIYScannerActivity.class);
                diyScanner_intent.putExtra("flag","sale");
                startActivity(diyScanner_intent);
            }
        }else if(getHomeInfo().getMenus().get(position).getMenuDesc().equals("lysmrk")){
            Intent intent;
            intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        }else if(getHomeInfo().getMenus().get(position).getMenuDesc().equals("lysmhh")){
            Intent intent;
            intent = new Intent(getContext(), MainActivity.class);
            intent.putExtra("lyhh","lyhh");
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



    public void get_versions_Success(AppUpdate appUpdate) {
        LogUtils.i(appUpdate);
        if (appUpdate.getData() != null) {
                UpdateConfiguration configuration = new UpdateConfiguration()
                        //输出错误日志
                        .setEnableLog(true)
                        //设置自定义的下载
                        //.setHttpManager()
                        //下载完成自动跳动安装页面
                        .setJumpInstallPage(true)
                        //支持断点下载
                        .setBreakpointDownload(true)
                        //设置是否显示通知栏进度
                        .setShowNotification(true)
                        //设置强制更新
                        .setForcedUpgrade(true)
                        //设置下载过程的监听
                        .setOnDownloadListener(this);

                DownloadManager manager = DownloadManager.getInstance(getContext());
                manager.setApkName("taohaoyun.apk")
                        .setApkUrl(appUpdate.getData().getUrl())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setShowNewerToast(true)
                        .setConfiguration(configuration)
                        .setDownloadPath(Environment.getExternalStorageDirectory() + "/AppUpdate")
                        .setApkVersionCode(2)
                        .setApkVersionName(appUpdate.getData().getVersion())
                        .setApkSize("" + appUpdate.getData().getSize())
                        .setApkDescription(appUpdate.getData().getRemark())
                        .download();


            }

        }


    @Override
    public void start() {

    }

    @Override
    public void downloading(int max, int progress) {

    }

    @Override
    public void done(File apk) {

    }

    @Override
    public void error(Exception e) {

    }
}
