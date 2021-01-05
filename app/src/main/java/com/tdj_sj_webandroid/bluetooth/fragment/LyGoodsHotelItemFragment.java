package com.tdj_sj_webandroid.bluetooth.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.apkfuns.logutils.LogUtils;
import com.tdj_sj_webandroid.NuclearGoodsHotelItemActivity;
import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.adapter.GoodsListAdapter;
import com.tdj_sj_webandroid.base.BaseFrgment;
import com.tdj_sj_webandroid.bluetooth.LyGoodsHotelItemActivity;
import com.tdj_sj_webandroid.bluetooth.adapter.LyGoodsListAdapter;
import com.tdj_sj_webandroid.bluetooth.presenter.LyGoodsHotelItemPresenter;
import com.tdj_sj_webandroid.model.CustomApiResult;
import com.tdj_sj_webandroid.model.GoodsInfo;
import com.tdj_sj_webandroid.model.Resume;
import com.tdj_sj_webandroid.mvp.presenter.NuclearGoodsHotelItemPresenter;
import com.tdj_sj_webandroid.utils.Constants;
import com.tdj_sj_webandroid.utils.GeneralUtils;
import com.tdj_sj_webandroid.utils.ListUtils;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LyGoodsHotelItemFragment extends BaseFrgment<LyGoodsHotelItemPresenter> {
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.recyclerView_list)
    RecyclerView recyclerView_list;
    private int index;

    private List<GoodsInfo.GoodsListBean> data=new ArrayList<>();
    private LyGoodsListAdapter adapter;

    private LyGoodsHotelItemActivity activity;
    private String keywords="";

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public static LyGoodsHotelItemFragment newInstance(int str) {
        Bundle args = new Bundle();
        args.putInt("intent", str);
        LyGoodsHotelItemFragment f = new LyGoodsHotelItemFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        LinearLayoutManager layout = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView_list.setLayoutManager(layout);
            adapter=new LyGoodsListAdapter(getContext(),data);
        recyclerView_list.setAdapter(adapter);
    }
    /**StateView的根布局，默认是整个界面，如果需要变换可以重写此方法*/
    public View getStateViewRoot() {
        return ll;
    }
    @Override
    public void onUserVisible() {
        super.onUserVisible();
        index=getArguments().getInt("intent");
        LogUtils.e(index);
        if (GeneralUtils.isNullOrZeroLenght(activity.question)){
          mPresenter.checkGoods(Constants.statis+(index+1)+"&nums="+(activity.num)+"&customer_id="+(activity.customer_id),getKeywords());
        }else {
            mPresenter.checkGoods(Constants.problem+(index+1),getKeywords());
        }

    }
    public void stop() {
        if (!ListUtils.isEmpty(data)) {
            data.clear();
            adapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity=(LyGoodsHotelItemActivity)context;
        registerEventBus(this);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.e("onDetach");
        unregisterEventBus(this);
    }
    @Override
    protected void loadData() {

    }

    @Override
    protected LyGoodsHotelItemPresenter loadPresenter() {
        return new LyGoodsHotelItemPresenter();
    }
    public void checkGoods_onError(ApiException e) {
        stop();
        mStateView.showEmpty();//显示重试的布局
    }
    public void checkGoods_Success(CustomApiResult<GoodsInfo> result) {

        activity.titles.clear();
           if (!GeneralUtils.isNullOrZeroLenght(activity.question)){
              activity.titles.add("入库丢失("+result.getData().getTab1()+")");
               activity.titles.add("未入库("+result.getData().getTab2()+")");
               activity.titles.add("串线("+result.getData().getTab3()+")");
        }else {
               activity.titles.add("入库丢失("+result.getData().getTab1()+")");
               activity.titles.add("正常("+result.getData().getTab2()+")");
               activity.titles.add("未入库("+result.getData().getTab3()+")");
               activity.titles.add("串线("+result.getData().getTab4()+")");
        }
        activity.adatper.notifyDataSetChanged();
           LogUtils.e(activity.titles.get(index));
        adapter.setTitle(activity.titles.get(index));
        stop();
        if (ListUtils.isEmpty(result.getData().getGoodsList())) {
            //获取不到数据,显示空布局

            mStateView.showEmpty();
            return;
        }
        mStateView.showContent();//显示内容
        data.addAll(result.getData().getGoodsList());
        adapter.setNum(activity.num!=null?Integer.parseInt(activity.num):result.getData().getTab2());
        adapter.notifyDataSetChanged();


    }
    /*code 不同事件接受處理*/
    @Subscribe( threadMode = ThreadMode.MAIN)
    public void Resume(Resume resume) {
            setKeywords(GeneralUtils.isNullOrZeroLenght(resume.getKeywords())?"":resume.getKeywords());

        if (GeneralUtils.isNullOrZeroLenght(resume.getType())){
            if (GeneralUtils.isNullOrZeroLenght(activity.question)){

                mPresenter.checkGoods(Constants.statis+(index+1)+"&nums="+(activity.num)+"&customer_id="+(activity.customer_id),getKeywords());
            }else {
                mPresenter.checkGoods(Constants.problem+(index+1),getKeywords());
            }
        }

    }

    @Override
    protected int getContentId() {
        return R.layout.nuclear_goods_hotel_fragment_layout;
    }
//
//    /*code 不同事件接受處理*/
//    @Subscribe( threadMode = ThreadMode.MAIN)
//    public void Resume(Resume resume) {
//
//
//    }
}
