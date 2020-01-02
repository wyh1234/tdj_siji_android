package com.tdj_sj_webandroid.adapter;

import android.content.Context;
import android.widget.TextView;

import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.model.ScannerHistory;
import com.tdj_sj_webandroid.model.ScannerHistoryDetalis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ScannerHistoryDetalisAdapter extends BaseRecyclerViewAdapter<ScannerHistoryDetalis.ItemsBean> {
    private Context context;

    public ScannerHistoryDetalisAdapter(Context context, List<ScannerHistoryDetalis.ItemsBean> data) {
        super(context, data, R.layout.scanner_history_details_list);
        this.context = context;
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, final ScannerHistoryDetalis.ItemsBean bean, final int position) {
        ((TextView) holder.getView(R.id.tv_date)).setText(getTimes(new Date(bean.getCreateTime())));
        ((TextView) holder.getView(R.id.tv_order_no)).setText(bean.getQrCodeId()+"");
        ((TextView) holder.getView(R.id.tv_name)).setText(bean.getProductName()+"");
        ((TextView) holder.getView(R.id.tv_pice)).setText(bean.getProductPrice()+"元/"+bean.getProductUnit());

    }

    public static String getTimes(Date date) {//
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        return format.format(date);
    }
}
