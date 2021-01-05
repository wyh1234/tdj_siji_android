package com.tdj_sj_webandroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.model.HomeInfo;
import com.tdj_sj_webandroid.utils.ImageLoad;
import com.tdj_sj_webandroid.utils.appupdate.ScrollLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public   class NewHomePageFragmentAdapter extends BaseQuickAdapter<String, BaseViewHolder>implements ItemAdapter.OnItemClickListener {
    private Context context;
    private Map<String, List<HomeInfo.MenusBean>> map;
    private ItemAdapter itemAdapter;
    private List<String> titleList;
    private ScrollLinearLayoutManager layout;



    private OnItemClickListener listener;
    private RecyclerView recyclerView;

    public NewHomePageFragmentAdapter(Context context,Map<String, List<HomeInfo.MenusBean>> map, @Nullable List<String> titleList) {
        super(R.layout.new_home_list_layout, titleList);
        this.titleList=titleList;
        this.map=map;
        this.context=context;

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public interface OnItemClickListener {

        void onItemClick( View v, HomeInfo.MenusBean menusBean);

    }
    



    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        layout = new ScrollLinearLayoutManager(context, 2);
        itemAdapter=new ItemAdapter(context,map.get(item));
        itemAdapter.setOnItemClickListener(this);
        helper.setText(R.id.tv_title,item);
        recyclerView=(RecyclerView) helper.getView(R.id.recylcerview);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(itemAdapter);

    }


    @Override
    public void onItemClick(View v, HomeInfo.MenusBean menusBean) {
        listener.onItemClick(v,menusBean);

    }

}
