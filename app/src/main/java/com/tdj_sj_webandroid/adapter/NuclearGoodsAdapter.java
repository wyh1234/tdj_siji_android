package com.tdj_sj_webandroid.adapter;

import android.content.Context;
import android.widget.TextView;

import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.model.StorageManage;

import java.util.List;

public class NuclearGoodsAdapter extends BaseRecyclerViewAdapter<StorageManage.ResInStockCheckedListBean> {
    private Context context;

    public NuclearGoodsAdapter(Context context, List<StorageManage.ResInStockCheckedListBean> data) {
        super(context, data, R.layout.rk_list_items);
        this.context = context;
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, final StorageManage.ResInStockCheckedListBean bean, final int position) {
        ((TextView) holder.getView(R.id.order_no_tv)).setText(bean.getSku());
        ((TextView) holder.getView(R.id.order_name_tv)).setText(bean.getProductName());
        ((TextView) holder.getView(R.id.order_qty)).setText(bean.getQty());
        ((TextView) holder.getView(R.id.order_specification)).setText(bean.getSpecification());

    }
}
