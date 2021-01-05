package com.tdj_sj_webandroid.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.tdj_sj_webandroid.DDJStorageManagementActivity;
import com.tdj_sj_webandroid.DIYScannerActivity;
import com.tdj_sj_webandroid.ManualScannerActivity;
import com.tdj_sj_webandroid.NuclearGoodsHotelActivity;
import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.StorageManagementActivity;
import com.tdj_sj_webandroid.WebViewActivity;
import com.tdj_sj_webandroid.bluetooth.MainActivity;
import com.tdj_sj_webandroid.model.HomeInfo;
import com.tdj_sj_webandroid.utils.Constants;
import com.tdj_sj_webandroid.utils.ImageLoad;
import com.yzq.zxinglibrary.android.CaptureActivity;

import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class ItemAdapter  extends  RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private Context context;
    private List<HomeInfo.MenusBean> list;

    private ItemAdapter.OnItemClickListener listener;


    public void setOnItemClickListener(ItemAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
    public interface OnItemClickListener {

        void onItemClick( View v, HomeInfo.MenusBean menusBean);
    }
    public ItemAdapter(Context context, List<HomeInfo.MenusBean>list){
        this.context=context;
        this.list=list;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_home_item_layout, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, final int pos) {
        ImageLoad.loadImageView(context,list.get(pos).getMenuIcon(), itemViewHolder.iv);
        itemViewHolder.tv_bottom.setText(list.get(pos).getMenuName());
        itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v,list.get(pos));
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv_bottom;
        ImageView iv;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_bottom=itemView.findViewById(R.id.tv_bottom);
            iv=itemView.findViewById(R.id.iv);
        }
    }
}
