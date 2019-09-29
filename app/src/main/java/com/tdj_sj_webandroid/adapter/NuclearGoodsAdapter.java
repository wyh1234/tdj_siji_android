package com.tdj_sj_webandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.WebViewActivity;
import com.tdj_sj_webandroid.model.StorageManagement;
import com.tdj_sj_webandroid.utils.Constants;

import java.util.List;

public class NuclearGoodsAdapter extends BaseRecyclerViewAdapter<StorageManagement> {
    private Context context;

    public NuclearGoodsAdapter(Context context, List<StorageManagement> data) {
        super(context, data, R.layout.rk_list_items);
        this.context = context;
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, final StorageManagement bean, final int position) {
        ((TextView) holder.getView(R.id.order_no_tv)).setText(bean.getSku());
        ((TextView) holder.getView(R.id.order_name_tv)).setText(bean.getProductName());
        ((TextView) holder.getView(R.id.order_pice_tv)).setText(bean.getPrice() + "元");
        ((TextView) holder.getView(R.id.order_num_tv)).setText(bean.getQty() + bean.getUnit());

    }
}
