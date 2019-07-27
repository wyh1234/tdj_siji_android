package com.tdj_sj_webandroid.adapter;

import android.content.Context;
import android.widget.TextView;

import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.model.StorageManagement;

import java.util.List;

public class StorageManagementAdapter  extends BaseRecyclerViewAdapter<StorageManagement>{

    public StorageManagementAdapter(Context context, List<StorageManagement> data) {
        super(context, data,  R.layout.rk_list_item);
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, StorageManagement bean, int position) {
        ((TextView) holder.getView(R.id.order_no_tv)).setText(bean.getSku());
        ((TextView)holder.getView(R.id.order_name_tv)).setText(bean.getProductName());
        ((TextView)holder.getView(R.id.order_pice_tv)).setText(bean.getPrice()+"元");
        ((TextView)holder.getView(R.id.order_num_tv)).setText(bean.getQty()+bean.getUnit());
        ((TextView)holder.getView(R.id.order_stastus_tv)).setText("入库");

    }
}
