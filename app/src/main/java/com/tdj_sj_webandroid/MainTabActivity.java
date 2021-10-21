package com.tdj_sj_webandroid;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.tdj_sj_webandroid.base.BaseActivity;
import com.tdj_sj_webandroid.fragment.MapFragment;
import com.tdj_sj_webandroid.fragment.MyFragment;
import com.tdj_sj_webandroid.fragment.NewHomePageFragment;
import com.tdj_sj_webandroid.fragment.PsFragment;
import com.tdj_sj_webandroid.model.BackHomePage;
import com.tdj_sj_webandroid.model.ConfirmPlan;
import com.tdj_sj_webandroid.mvp.presenter.IPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainTabActivity extends BaseActivity {
    @BindView(R.id.home)
    FrameLayout frameLayout_home;
    @BindView(R.id.ps)
    FrameLayout frameLayout_ps;
    @BindView(R.id.my)
    FrameLayout frameLayout_my;
    @BindView(R.id.home_image)
    ImageView home_imag;
    @BindView(R.id.ps_image)
    ImageView ps_image;
    @BindView(R.id.my_image)
    ImageView my_image;
    @BindView(R.id.home_tv)
    TextView home_tv;
    @BindView(R.id.ps_tv)
    TextView ps_tv;
    @BindView(R.id.my_tv)
    TextView my_tv;
    @BindView(R.id.map)
    FrameLayout map;
    @BindView(R.id.map_image)
    ImageView map_image;
    @BindView(R.id.map_tv)
    TextView map_tv;

    private FragmentManager fragmentManager;
    private NewHomePageFragment homePageFragment;
    private MapFragment mapFragment;
    private PsFragment psFragment;
    private MyFragment myFragment;
    private long mExitTime;
    @BindView(R.id.fg_content)
    FrameLayout fg_content;
    public ImmersionBar immersionBar;

    @Override
    protected IPresenter loadPresenter() {
        return null;
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
        immersionBar= ImmersionBar.with(this);
        immersionBar .statusBarDarkFont(true).init();
        fragmentManager = getSupportFragmentManager();
        setTabSelection(0);
        int index = getIntent().getIntExtra("index", -1);
        if (index != -1){
            setTabSelection(index);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tab_main;
    }

    @OnClick({R.id.home, R.id.ps,R.id.my,R.id.ps_image,R.id.map})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home:
                setTabSelection(0);
                immersionBar .statusBarDarkFont(true).init();
                break;
            case R.id.ps:
                setTabSelection(2);
                immersionBar .statusBarDarkFont(true).init();
                break;

            case R.id.ps_image:
                immersionBar .statusBarDarkFont(true).init();
                setTabSelection(2);
                break;
            case R.id.my:
                immersionBar .statusBarDarkFont(true).init();
                setTabSelection(3);
                break;
            case R.id.map:
                immersionBar .statusBarDarkFont(true).init();
                setTabSelection(1);
                break;
        }
    }



    public void setTabSelection(int index) {
        resetBtn();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (index) {
            case 0:
                home_imag.setImageResource(R.mipmap.shouye);
                home_tv.setTextColor(getResources().getColor(R.color.text_visible));
                if (homePageFragment == null) {
                    homePageFragment = new NewHomePageFragment();
                }
                if (homePageFragment.isAdded()) {
                    transaction.show(homePageFragment);
                } else {
                    transaction.add(R.id.fg_content, homePageFragment);
                }
                if (psFragment != null && psFragment.isVisible()) {
                    transaction.hide(psFragment);
                }
                if (myFragment != null && myFragment.isVisible()) {
                    transaction.hide(myFragment);
                }
                if (mapFragment != null && mapFragment.isVisible()) {
                    transaction.hide(mapFragment);
                }
                break;
            case 1:
                map_image.setImageResource(R.mipmap.mapmap);
                map_tv.setTextColor(getResources().getColor(R.color.text_visible));
                if (mapFragment == null) {
                    mapFragment = new MapFragment();
                }
                if (mapFragment.isAdded()) {
                    transaction.show(mapFragment);
                } else {
                    transaction.add(R.id.fg_content, mapFragment);
                }
                if (homePageFragment != null && homePageFragment.isVisible()) {
                    transaction.hide(homePageFragment);
                }
                if (myFragment != null && myFragment.isVisible()) {
                    transaction.hide(myFragment);
                }
                if (psFragment != null && psFragment.isVisible()) {
                    transaction.hide(psFragment);
                }
                break;
            case 2:
                ps_image.setImageResource(R.mipmap.peisong);
                ps_tv.setTextColor(getResources().getColor(R.color.text_visible));
                if (psFragment == null) {
                    psFragment = new PsFragment();
                }
                if (psFragment.isAdded()) {
                    transaction.show(psFragment);
                } else {
                    transaction.add(R.id.fg_content, psFragment);
                }
                if (homePageFragment != null && homePageFragment.isVisible()) {
                    transaction.hide(homePageFragment);
                }
                if (myFragment != null && myFragment.isVisible()) {
                    transaction.hide(myFragment);
                }
                if (mapFragment != null && mapFragment.isVisible()) {
                    transaction.hide(mapFragment);
                }
                break;
            case 3:
                my_image.setImageResource(R.mipmap.wodes);
                my_tv.setTextColor(getResources().getColor(R.color.text_visible));
                if (myFragment == null) {
                    myFragment = new MyFragment();
                }
                if (myFragment.isAdded()) {
                    transaction.show(myFragment);
                } else {
                    transaction.add(R.id.fg_content, myFragment);
                }
                if (homePageFragment != null && homePageFragment.isVisible()) {
                    transaction.hide(homePageFragment);
                }
                if (psFragment != null && psFragment.isVisible()) {
                    transaction.hide(psFragment);
                }
                if (mapFragment != null && mapFragment.isVisible()) {
                    transaction.hide(mapFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void resetBtn() {
        home_imag.setImageResource(R.mipmap.shouyes);
        home_tv.setTextColor(getResources().getColor(R.color.text_gone));
        ps_image.setImageResource(R.mipmap.peisong_h);
        ps_tv.setTextColor(getResources().getColor(R.color.text_gone));
        my_image.setImageResource(R.mipmap.wode);
        my_tv.setTextColor(getResources().getColor(R.color.text_gone));
        map_image.setImageResource(R.mipmap.mapmap_h);
        map_tv.setTextColor(getResources().getColor(R.color.text_gone));


    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        //如果用以下这种做法则不保存状态，再次进来的话会显示默认tab
//        //总是执行这句代码来调用父类去保存视图层的状态
//        //super.onSaveInstanceState(outState);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
        {
//            if (getVisibleFragment()!=null&&getVisibleFragment() instanceof RentingsFragment){
//                RentingsFragment rentingFragment= (RentingsFragment) getVisibleFragment();
//                rentingFragment.loadDataback();
//            }
            Toast.makeText(MainTabActivity.this, getResources().getString(R.string.msg_close), Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();

        } else {
            finish();
        }
    }
    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = MainTabActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for(Fragment fragment : fragments){
            if(fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (index != -1){
            setTabSelection(index);
            index = -1;
        }
    }

    private int index = -1;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(ConfirmPlan event){
        if (event.getIndex() > 3 || event.getIndex() < 0) return;
        index = event.getIndex();
        setTabSelection(index);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(BackHomePage event){
        if (event.getIndex() > 3 || event.getIndex() < 0) return;
        index = event.getIndex();
        setTabSelection(index);
    }


    public static void lunchMainTabAc(Context context,int index){
        Intent intent = new Intent(context, MainTabActivity.class);
        intent.putExtra("index",index);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
