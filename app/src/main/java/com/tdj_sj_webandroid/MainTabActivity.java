package com.tdj_sj_webandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.tdj_sj_webandroid.base.BaseActivity;
import com.tdj_sj_webandroid.fragment.HomePageFragment;
import com.tdj_sj_webandroid.fragment.MyFragment;
import com.tdj_sj_webandroid.fragment.PsFragment;
import com.tdj_sj_webandroid.mvp.presenter.IPresenter;

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
    private FragmentManager fragmentManager;
    private HomePageFragment homePageFragment;
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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tab_main;
    }

    @OnClick({R.id.home, R.id.ps,R.id.my,R.id.ps_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home:
                setTabSelection(0);
                break;
            case R.id.ps:
                setTabSelection(1);
                break;

            case R.id.ps_image:
                setTabSelection(1);
                break;
            case R.id.my:
                setTabSelection(2);
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
                    homePageFragment = new HomePageFragment();
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
                break;
            case 1:
                ps_image.setImageResource(R.mipmap.pss_bg);
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
                break;
            case 2:
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
                break;
        }
        transaction.commit();
    }

    private void resetBtn() {
        home_imag.setImageResource(R.mipmap.shouyes);
        home_tv.setTextColor(getResources().getColor(R.color.text_gone));
        ps_image.setImageResource(R.mipmap.ps_bg);
        ps_tv.setTextColor(getResources().getColor(R.color.text_gone));
        my_image.setImageResource(R.mipmap.wode);
        my_tv.setTextColor(getResources().getColor(R.color.text_gone));


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //如果用以下这种做法则不保存状态，再次进来的话会显示默认tab
        //总是执行这句代码来调用父类去保存视图层的状态
        //super.onSaveInstanceState(outState);
    }

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

}
