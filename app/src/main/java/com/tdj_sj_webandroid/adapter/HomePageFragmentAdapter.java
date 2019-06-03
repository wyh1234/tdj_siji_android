package com.tdj_sj_webandroid.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.model.HomeInfo;
import com.tdj_sj_webandroid.utils.ImageLoad;

import java.util.List;

public class HomePageFragmentAdapter extends BaseRecyclerViewAdapter<HomeInfo.MenusBean> {
    private Context context;
    public  HomePageFragmentAdapter(Context context, List<HomeInfo.MenusBean> data) {
        super(context, data, R.layout.homepage_layout_item);
        this.context=context;
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, HomeInfo.MenusBean bean, int position) {
        ((TextView) holder.getView(R.id.tv)).setText(bean.getMenuName());
        ImageLoad.loadImageView(context,bean.getMenuIcon(),((ImageView) holder.getView(R.id.iv)));
    }
}
