package com.tdj_sj_webandroid.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tdj_sj_webandroid.DDJStorageManagementActivity;
import com.tdj_sj_webandroid.DIYScannerActivity;
import com.tdj_sj_webandroid.MainTabActivity;
import com.tdj_sj_webandroid.ManualScannerActivity;
import com.tdj_sj_webandroid.NuclearGoodsHotelActivity;
import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.StorageManagementActivity;
import com.tdj_sj_webandroid.WebViewActivity;
import com.tdj_sj_webandroid.adapter.BannerHolderView;
import com.tdj_sj_webandroid.adapter.NewHomePageFragmentAdapter;
import com.tdj_sj_webandroid.base.BaseFrgment;
import com.tdj_sj_webandroid.bluetooth.MainActivity;
import com.tdj_sj_webandroid.contract.TDJContract;
import com.tdj_sj_webandroid.model.AppUpdate;
import com.tdj_sj_webandroid.model.ConfirmPlan;
import com.tdj_sj_webandroid.model.HomeInfo;
import com.tdj_sj_webandroid.mvp.presenter.NewHomePageFragmentPresenter;
import com.tdj_sj_webandroid.utils.Constants;
import com.tdj_sj_webandroid.utils.GeneralUtils;
import com.tdj_sj_webandroid.utils.LocationUtils;
import com.tdj_sj_webandroid.utils.ToPlanDialog;
import com.tdj_sj_webandroid.utils.appupdate.DownloadManager;
import com.tdj_sj_webandroid.utils.appupdate.OnDownloadListener;
import com.tdj_sj_webandroid.utils.appupdate.UpdateConfiguration;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

import static android.app.Activity.RESULT_OK;

public class NewHomePageFragment extends BaseFrgment<NewHomePageFragmentPresenter> implements
        TDJContract.HomePageFragmentView, OnDownloadListener,NewHomePageFragmentAdapter.OnItemClickListener, OnItemClickListener,SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_recyclerView)
    RecyclerView rv_recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private HomeInfo homeInfo;
    private RxPermissions rxPermissions;
    private static final int REQUEST_CODE_SCAN=0X0001;
    private MainTabActivity activity;
    private Map<Integer, List<HomeInfo.MenusBean>> map = new TreeMap<Integer,  List<HomeInfo.MenusBean>>(
            new Comparator<Integer>() {
                public int compare(Integer obj1, Integer obj2) {
                    return obj1.compareTo(obj2);
                }
            });;
    private Map<String, List<HomeInfo.MenusBean>> maps = new TreeMap<String,  List<HomeInfo.MenusBean>>();
    private   NewHomePageFragmentAdapter homePageFragmentAdapter;
    private List<String> titleList=new ArrayList<>();
    private View headView;
    private ConvenientBanner banner;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainTabActivity) context;
        registerEventBus(this);
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
        swipeRefreshLayout.setColorSchemeResources(R.color.bbl_ff0000);
        swipeRefreshLayout.setOnRefreshListener(this);
        //第二种方式，如果toolbar有颜色，要设置状态栏颜色
        rxPermissions = new RxPermissions(getActivity());
        LinearLayoutManager layout = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        rv_recyclerView.setLayoutManager(layout);

         homePageFragmentAdapter= new NewHomePageFragmentAdapter(getContext(), maps, titleList) ;
        rv_recyclerView.setAdapter(homePageFragmentAdapter);
        homePageFragmentAdapter.setOnItemClickListener(this);

        headView=LayoutInflater.from(getContext()).inflate(R.layout.home_head_layout, rv_recyclerView, false);
        banner=headView.findViewById(R.id.banner);
        banner.setOnItemClickListener(this);
        homePageFragmentAdapter.addHeaderView(headView);


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
    protected NewHomePageFragmentPresenter loadPresenter() {
        return new NewHomePageFragmentPresenter();
    }

    @Override
    protected int getContentId() {
        return R.layout.new_activity_tab_main;
    }



    @Override
    public void get_menus_Success(HomeInfo homeInfo) {
        stop();
        setHomeInfo(homeInfo);
        titleList.clear();
        map.clear();
        maps.clear();
        ((TextView)headView.findViewById(R.id.tv_title)).setText(homeInfo.getTitle());
        for (HomeInfo.MenusBean menusBean:homeInfo.getMenus()){
            if (map.containsKey(menusBean.getHomeGroup())){
                List<HomeInfo.MenusBean> lists=  map.get(menusBean.getHomeGroup());
                lists.add(menusBean);
                map.put(menusBean.getHomeGroup(),lists);
            }else {
                List<HomeInfo.MenusBean> list=new ArrayList<>();
                list.add(menusBean);
                map.put(menusBean.getHomeGroup(),list);
            }
        }


        for (Map.Entry<Integer, List<HomeInfo.MenusBean>> entry : map.entrySet()) {
                  titleList.add(entry.getValue().get(0).getHomeGroupName());
                  maps.put(entry.getValue().get(0).getHomeGroupName(),entry.getValue());//排序过后的maps

        }
        homePageFragmentAdapter.notifyDataSetChanged();

        //初始化商品图片轮播
        banner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new BannerHolderView();
            }
        },  homeInfo.getNoticeList());
        banner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        banner.startTurning(5000);

    }

    @Override
    public void onItemClick(View v, HomeInfo.MenusBean menusBean) {
        LogUtils.e(""+menusBean);
        if (menusBean.getMenuDesc()==null){
                Intent intent=new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url", Constants.URL1+menusBean.getMenuUrl());
                startActivity(intent);
        }else if (menusBean.getMenuDesc().equals("hhzc")){

                if (getHomeInfo().getPhase() == 0) {
                    ToPlanDialog toPlanDialog = ToPlanDialog.newInstance();
                    if (!toPlanDialog.isAdded()) {
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .add(toPlanDialog, "toPlanDialog")
                                .commitAllowingStateLoss();
                    }
                } else {
                    Intent intent = new Intent(getContext(), WebViewActivity.class);
                    intent.putExtra("url", Constants.URL1 + menusBean.getMenuUrl());
                    startActivity(intent);
                }

        } else if  (menusBean.getMenuDesc().equals("sm")){
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
        }else if (menusBean.getMenuDesc().equals("ps")){
            activity.setTabSelection(1);
        }else if (menusBean.getMenuDesc().equals("smrk")){
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
        }else if (menusBean.getMenuDesc().equals("smhh")){
            Intent intent=new Intent(getContext(), NuclearGoodsHotelActivity.class);
            intent.putExtra("url", Constants.URL1+menusBean.getMenuUrl());
            startActivity(intent);
        }else if (menusBean.getMenuDesc().equals("zzsmrk")){
            if (!Build.MODEL.equals("NLS-MT90")){
                Intent  diyScanner_intent = new Intent(getContext(), DIYScannerActivity.class);
                startActivity(diyScanner_intent);
            }

        }else if (menusBean.getMenuDesc().equals("rgsmrk")){
            Intent intent;
            if (!Build.MODEL.equals("NLS-MT90")){
                Intent  manualScanner_intent = new Intent(getContext(), ManualScannerActivity.class);
                startActivity(manualScanner_intent);
            }
        }else if (menusBean.getMenuDesc().equals("jhcsmrk")){
            Intent intent;
            if (!Build.MODEL.equals("NLS-MT90")){
                Intent  manualScanner_intent = new Intent(getContext(), ManualScannerActivity.class);
                manualScanner_intent.putExtra("flag","sale");
                startActivity(manualScanner_intent);
            }
        }else if (menusBean.getMenuDesc().equals("jhczzsmrk")){
            Intent intent;
            if (!Build.MODEL.equals("NLS-MT90")){
                Intent  diyScanner_intent = new Intent(getContext(), DIYScannerActivity.class);
                diyScanner_intent.putExtra("flag","sale");
                startActivity(diyScanner_intent);
            }
        }else if(menusBean.getMenuDesc().equals("lysmrk")){
            Intent intent;
            intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        }else if(menusBean.getMenuDesc().equals("lysmhh")){
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


    @Override
    public void onItemClick(int position) {
        if (GeneralUtils.isNullOrZeroLenght(getHomeInfo().getNoticeList().get(position).getLink_url())){
            return;
        }
        Intent intent = new Intent(getContext(), WebViewActivity.class);
//        intent.putExtra("title","公告详情");
        intent.putExtra("url", Constants.URL + getHomeInfo().getNoticeList().get(position).getLink_url()+getHomeInfo().getNoticeList().get(position).getEntity_id()+"&content="+
                getHomeInfo().getNoticeList().get(position).getContent()+"&contentImg="+getHomeInfo().getNoticeList().get(position).getContent_img()+"&title="+getHomeInfo().getNoticeList()
        .get(position).getTitle());

        startActivity(intent);

    }

    @Override
    public void onRefresh() {
        mPresenter.get_menus();
        LocationUtils.getInstance().stopLocalService();
    }

    public void stop(){
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.e("onDetach");
        unregisterEventBus(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(ConfirmPlan event){
        if (event.getIndex() > 3 || event.getIndex() < 0) return;
        if (event.isConf()){
            mPresenter.get_menus();
        }
    }
}
