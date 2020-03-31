package com.tdj_sj_webandroid;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.tdj_sj_webandroid.adapter.FragmentHotelAdapter;
import com.tdj_sj_webandroid.base.BaseActivity;
import com.tdj_sj_webandroid.model.Resume;
import com.tdj_sj_webandroid.model.SeachTag;
import com.tdj_sj_webandroid.mvp.presenter.IPresenter;
import com.tdj_sj_webandroid.utils.GeneralUtils;
import com.tdj_sj_webandroid.utils.ToastUtils;
import com.tdj_sj_webandroid.utils.tablayout.WTabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NuclearGoodsHotelActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    public  TextView tv_title;
    @BindView(R.id.tv_stationName)
    public  TextView tv_stationName;
    @BindView(R.id.tv_driverNo)
    public  TextView tv_driverNo;
    @BindView(R.id.wtab)
    WTabLayout wtab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_question)
    TextView tv_question;
    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.right_text)
    TextView right_text;


    @BindView(R.id.btn_back)
    ImageView btn_back;
    public FragmentHotelAdapter adatper;
    public List<String> titles = new ArrayList<>();
    @OnClick({R.id.btn_back,R.id.tv_question,R.id.right_text})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_question:
                Intent intent=new Intent(NuclearGoodsHotelActivity.this,NuclearGoodsHotelItemActivity.class);
                intent.putExtra("question","question");
                startActivity(intent);
                LogUtils.e("44444");

                break;
            case R.id.right_text:
             /*   if (GeneralUtils.isNullOrZeroLenght(search_edit.getText().toString())){
                    ToastUtils.showToast(this,"请输入编号");

                }else {*/
                    Resume resume=new Resume();
                    resume.setTag(1);
                    resume.setKeywords(search_edit.getText().toString());
                    EventBus.getDefault().post(resume);
//                }

                break;
        }
    }
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
        ImmersionBar.with(this).statusBarDarkFont(true).keyboardEnable(true)
                .init();
        titles.add("扫码核货(0)");
        titles.add("已核货(补扫单)(0)");
        titles.add("核货完成(0)");
        wtab.setxTabDisplayNum(titles.size());
        adatper = new FragmentHotelAdapter(this.getSupportFragmentManager(), titles);
        viewPager.setAdapter(adatper);
        wtab.setupWithViewPager(viewPager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.nuclear_goods_hotel_layout;
    }
}
