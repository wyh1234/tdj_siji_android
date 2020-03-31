package com.tdj_sj_webandroid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.apkfuns.logutils.LogUtils;
import com.tdj_sj_webandroid.NuclearGoodsHotelActivity;
import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.adapter.NuclearGoodsHotelAdapter;
import com.tdj_sj_webandroid.base.BaseFrgment;
import com.tdj_sj_webandroid.model.CustomApiResult;
import com.tdj_sj_webandroid.model.NuclearGoodsHotel;
import com.tdj_sj_webandroid.model.Resume;
import com.tdj_sj_webandroid.mvp.presenter.NuclearGoodsHotelPresenter;
import com.tdj_sj_webandroid.utils.GeneralUtils;
import com.tdj_sj_webandroid.utils.ListUtils;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NuclearGoodsHotelFragment extends BaseFrgment<NuclearGoodsHotelPresenter> {
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.recyclerView_list)
    RecyclerView recyclerView_list;
    private int index;
    private List<NuclearGoodsHotel.OrderListBean> data=new ArrayList<>();
    private NuclearGoodsHotelAdapter adapter;
    private NuclearGoodsHotelActivity activity;
    private String keywords="";

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public static NuclearGoodsHotelFragment newInstance(int str) {
        Bundle args = new Bundle();
        args.putInt("intent", str);
        NuclearGoodsHotelFragment f = new NuclearGoodsHotelFragment();
        f.setArguments(args);
        return f;
    }
    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        LinearLayoutManager layout = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView_list.setLayoutManager(layout);
        adapter=new NuclearGoodsHotelAdapter(getActivity(),data);
        recyclerView_list.setAdapter(adapter);

    }
    /**StateView的根布局，默认是整个界面，如果需要变换可以重写此方法*/
    public View getStateViewRoot() {
        return ll;
    }
    @Override
    protected void loadData() {

    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        index=getArguments().getInt("intent");
        LogUtils.e(index);
        mPresenter.checkGoods(index+1,keywords);

    }
    public void stop() {
            if (!ListUtils.isEmpty(data)) {
                data.clear();
                adapter.notifyDataSetChanged();
            }

    }
    @Override
    protected NuclearGoodsHotelPresenter loadPresenter() {
        return new NuclearGoodsHotelPresenter();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.e("onAttach");
        if (context instanceof NuclearGoodsHotelActivity){
            activity=(NuclearGoodsHotelActivity)context;
        }
        registerEventBus(this);

    }
    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.e("onDetach");
        unregisterEventBus(this);
    }
    @Override
    protected int getContentId() {
        return R.layout.nuclear_goods_hotel_fragment_layout;
    }

    public void checkGoods_onError(ApiException e) {
        stop();
        mStateView.showEmpty();//显示重试的布局
    }

    public void checkGoods_Success(CustomApiResult<NuclearGoodsHotel> response) {

        adapter.setIndex(index+1);
        activity.titles.clear();
        activity.tv_title.setText(response.getData().getDate()+"核货");
        activity.tv_stationName.setText(response.getData().getUser().getStationName());
        activity.tv_driverNo.setText(response.getData().getUser().getDriverNo());
        activity.titles.add("扫码核货("+response.getData().getTab1()+")");
        activity.titles.add("已核货(补扫单)("+response.getData().getTab2()+")");
        activity.titles.add("核货完成("+response.getData().getTab3()+")");
        activity.adatper.notifyDataSetChanged();

        stop();
         if (ListUtils.isEmpty(response.getData().getOrder_list())) {
                //获取不到数据,显示空布局

                mStateView.showEmpty();
                return;
       }
            mStateView.showContent();//显示内容
            data.addAll(response.getData().getOrder_list());
            adapter.notifyDataSetChanged();

    }
    /*code 不同事件接受處理*/
    @Subscribe( threadMode = ThreadMode.MAIN)
    public void Resume(Resume resume) {
        if (resume.getTag()==1){
            mPresenter.checkGoods(index+1,resume.getKeywords());
            setKeywords(resume.getKeywords());

        }else {
            mPresenter.checkGoods(index+1,getKeywords());
        }
    }
}
