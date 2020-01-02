package com.tdj_sj_webandroid.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.model.ScannerHistory;

import java.util.List;

public class ScannerHistoryAdapter extends BaseRecyclerViewAdapter<ScannerHistory.ItemsBean> {
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ScannerHistoryAdapter(Context context, List<ScannerHistory.ItemsBean> data) {
        super(context, data, R.layout.scanner_history_list);
        this.context = context;
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, final ScannerHistory.ItemsBean bean, final int position) {
        ((TextView) holder.getView(R.id.tv_name)).setText(bean.getStoreName());
        ((TextView) holder.getView(R.id.tv_phone)).setText(bean.getStorePhone()+"");
        ((TextView) holder.getView(R.id.tv_ck)).setText(bean.getScannerHouse());
        ((TextView) holder.getView(R.id.tv_num)).setText(bean.getRowSize()+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view,position);
            }
        });

    }
}
