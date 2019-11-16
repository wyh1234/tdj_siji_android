package com.tdj_sj_webandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdj_sj_webandroid.DDJNuclearGoodsActivity;
import com.tdj_sj_webandroid.NuclearGoodsActivity;
import com.tdj_sj_webandroid.NuclearGoodsHotelItemActivity;
import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.model.NuclearGoodsHotel;
import com.tdj_sj_webandroid.model.StorageManagement;

import java.util.List;

public class NuclearGoodsHotelAdapter extends BaseRecyclerViewAdapter<NuclearGoodsHotel.OrderListBean>{
    private Context context;
    private int index;


    public void setIndex(int index) {
        this.index = index;
    }

    public NuclearGoodsHotelAdapter(Context context, List<NuclearGoodsHotel.OrderListBean> data) {
        super(context, data, R.layout.nuclear_goods_hotel_item);
        this.context=context;
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, final NuclearGoodsHotel.OrderListBean bean, int position) {
        ((TextView) holder.getView(R.id.tv_code)).setText(bean.getCode());
        ((TextView) holder.getView(R.id.tv_num)).setText("子订单共："+bean.getNum()+"个");
        ((TextView) holder.getView(R.id.tv_zc)).setText(bean.getZc()+"");
        ((TextView) holder.getView(R.id.tv_ds)).setText(bean.getDs()+"");
        ((TextView) holder.getView(R.id.tv_drk)).setText(bean.getDrk()+"");
        ((TextView) holder.getView(R.id.tv_ch)).setText(bean.getCh()+"");
        ((RelativeLayout) holder.getView(R.id.rl_one)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, NuclearGoodsHotelItemActivity.class);
                intent.putExtra("num",bean.getNum()+"");
                intent.putExtra("customer_id",bean.getCustomer()+"");
                intent.putExtra("position","1");
                context.startActivity(intent);
            }
        });
        ((RelativeLayout) holder.getView(R.id.rl_two)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, NuclearGoodsHotelItemActivity.class);
                intent.putExtra("num",bean.getNum()+"");
                intent.putExtra("customer_id",bean.getCustomer()+"");
                intent.putExtra("position","0");
                context.startActivity(intent);
            }
        });
        ((RelativeLayout) holder.getView(R.id.rl_three)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, NuclearGoodsHotelItemActivity.class);
                intent.putExtra("num",bean.getNum()+"");
                intent.putExtra("customer_id",bean.getCustomer()+"");
                intent.putExtra("position","2");
                context.startActivity(intent);
            }
        });
        ((RelativeLayout) holder.getView(R.id.rl_four)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, NuclearGoodsHotelItemActivity.class);
                intent.putExtra("num",bean.getNum()+"");
                intent.putExtra("customer_id",bean.getCustomer()+"");
                intent.putExtra("position","3");
                context.startActivity(intent);
            }
        });
        ((Button) holder.getView(R.id.btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (Build.MODEL.equals("NLS-MT90")){
                    intent = new Intent(context, NuclearGoodsActivity.class);
                }else {
                    intent = new Intent(context, DDJNuclearGoodsActivity.class);
                }
                intent.putExtra("customer_id",bean.getCustomer()+"");
                intent.putExtra("customer_code",bean.getCode());
                context.startActivity(intent);
            }
        });
        if (index==1){
            ((Button) holder.getView(R.id.btn)).setText("扫码核货");
            ((Button) holder.getView(R.id.btn)).setBackgroundResource(R.drawable.search_text);
        }else  if (index==2){
            ((Button) holder.getView(R.id.btn)).setText("已核货");
            ((Button) holder.getView(R.id.btn)).setBackgroundResource(R.drawable.search_text1);
        }else {
            ((Button) holder.getView(R.id.btn)).setText("核货完成");
            ((Button) holder.getView(R.id.btn)).setBackgroundResource(R.drawable.search_text2);
        }


    }
}
