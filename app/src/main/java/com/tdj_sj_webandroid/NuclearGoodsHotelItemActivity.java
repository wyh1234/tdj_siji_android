package com.tdj_sj_webandroid;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.tdj_sj_webandroid.adapter.FragmentHotelAdapter;
import com.tdj_sj_webandroid.adapter.FragmentHotelItemAdapter;
import com.tdj_sj_webandroid.base.BaseActivity;
import com.tdj_sj_webandroid.model.Resume;
import com.tdj_sj_webandroid.mvp.presenter.IPresenter;
import com.tdj_sj_webandroid.utils.GeneralUtils;
import com.tdj_sj_webandroid.utils.ToastUtils;
import com.tdj_sj_webandroid.utils.tablayout.WTabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NuclearGoodsHotelItemActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    public  TextView tv_title;
    @BindView(R.id.wtab)
    WTabLayout wtab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.btn_back)
    ImageView btn_back;

    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.right_text)
    TextView right_text;
    public FragmentHotelItemAdapter adatper;
    public String question,num,customer_id;

    public List<String> titles = new ArrayList<>();
    @OnClick({R.id.btn_back,R.id.right_text})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_back:
                Resume resume=new Resume();
                resume.setType("NuclearGoodsHotelItem");
                EventBus.getDefault().post(resume);
                finish();
                break;
            case R.id.right_text:
            /*    if (GeneralUtils.isNullOrZeroLenght(search_edit.getText().toString())){
                    ToastUtils.showToast(this,"请输入编号");

                }else {*/
                    Resume resume1=new Resume();
                    resume1.setKeywords(search_edit.getText().toString());
                    EventBus.getDefault().post(resume1);
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
        if (getIntent().getStringExtra("question")!=null){
            titles.add("入库丢失(0)");
            titles.add("未入库(0)");
            titles.add("串线(0)");
            tv_title.setText("核货问题汇总");
            question=getIntent().getStringExtra("question");
        }else {
            titles.add("入库丢失(0)");
            titles.add("正常(0)");
            titles.add("未入库(0)");
            titles.add("串线(0)");
            if ( getIntent().getStringExtra("customer_name")!=null){
                tv_title.setText(""+( getIntent().getStringExtra("customer_name")));
            }else {
                tv_title.setText("核货");
            }

            num=getIntent().getStringExtra("num");
            customer_id=getIntent().getStringExtra("customer_id");
        }

        wtab.setxTabDisplayNum(titles.size());
        adatper = new FragmentHotelItemAdapter(this.getSupportFragmentManager(), titles);
        viewPager.setAdapter(adatper);
        wtab.setupWithViewPager(viewPager);
        if (getIntent().getStringExtra("position")!=null){
            viewPager.setCurrentItem(Integer.parseInt(getIntent().getStringExtra("position")));
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.nuclear_goods_hotel_item_layout;
    }



}
