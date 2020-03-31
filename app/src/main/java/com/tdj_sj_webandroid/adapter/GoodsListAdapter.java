package com.tdj_sj_webandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.tdj_sj_webandroid.DDJNuclearGoodsActivity;
import com.tdj_sj_webandroid.NuclearGoodsActivity;
import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.model.GoodsInfo;
import com.tdj_sj_webandroid.model.NuclearGoodsHotel;
import com.tdj_sj_webandroid.utils.ImageLoad;

import java.util.List;

public class GoodsListAdapter extends BaseRecyclerViewAdapter<GoodsInfo.GoodsListBean>{
    public String title;
    private Context context;
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GoodsListAdapter(Context context, List<GoodsInfo.GoodsListBean> data) {
        super(context, data, R.layout.goods_list_item);
        this.context=context;
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, final GoodsInfo.GoodsListBean bean, int position) {
        ImageLoad.loadImageView(context,bean.getProductImage().replace("http","https"),((ImageView) holder.getView(R.id.iv)));
        if (bean.getProductCriteria().equals("1")){
            ((TextView) holder.getView(R.id.tv)).setText("通");
        }else {
            ((TextView) holder.getView(R.id.tv)).setText("精");
        }
        ((TextView) holder.getView(R.id.tv_nickname)).setText(bean.getName());
        if (bean.getLevelType()==3){
            ((TextView) holder.getView(R.id.tv_tiltle)).setText("￥"+bean.getPrice()+"元/"+bean.getUnit()+"("+bean.getLevel2Value() + ""+ bean.getLevel2Unit()+"*"+bean.getLevel3Value()+  bean.getLevel3Unit()+ ""+")");
        }else {
            ((TextView) holder.getView(R.id.tv_tiltle)).setText("￥"+bean.getPrice()+"元/"+bean.getUnit()+"("+bean.getLevel2Value()+bean.getLevel2Unit()+")");
        }
        if (bean.getLevelType()==1){
            ((TextView) holder.getView(R.id.tv_tiltle)).setText("￥"+bean.getPrice()+"元/"+bean.getUnit());
        }
        ((TextView) holder.getView(R.id.tv_num)).setText("X"+bean.getOrderQty());
        ((TextView) holder.getView(R.id.tv_tag)).setText(bean.getStoreName());
        ((TextView) holder.getView(R.id.tv_driverNo)).setText(bean.getLineCode()+"-"+bean.getCustomerLineCode());
        if (title.contains("入库丢失")){
            ((RelativeLayout) holder.getView(R.id.rl_bottom)).setVisibility(View.GONE);
            ((TextView) holder.getView(R.id.tv_name)).setVisibility(View.VISIBLE);
            if (bean.getItemStatus()==7){
                ((TextView) holder.getView(R.id.tv_name)).setText("买家自送");
            }else if (bean.getItemStatus()==8){
                ((TextView) holder.getView(R.id.tv_name)).setText("买家无货");
            }
            if (bean.getDriver_name_code()==null){
                ((TextView) holder.getView(R.id.tv_name)).setText("漏扫");
            }else {
                ((TextView) holder.getView(R.id.tv_name)).setText("入库人："+bean.getDriver_name_code());
            }
        }else if (title.contains("未入库")){
            ((RelativeLayout) holder.getView(R.id.rl_bottom)).setVisibility(View.GONE);
            ((TextView) holder.getView(R.id.tv_name)).setVisibility(View.GONE);
        }else if (title.contains("串线")){
            ((TextView) holder.getView(R.id.tv_dqwz)).setText("当前位置："+bean.getCheck_customer_code());
            ((TextView) holder.getView(R.id.tv_dq_name)).setText(bean.getDriver_name_code());
            ((RelativeLayout) holder.getView(R.id.rl_bottom)).setVisibility(View.VISIBLE);
            ((TextView) holder.getView(R.id.tv_name)).setVisibility(View.GONE);

        }else {
            ((RelativeLayout) holder.getView(R.id.rl_hh)).setVisibility(View.GONE);
            ((RelativeLayout) holder.getView(R.id.rl_bottom)).setVisibility(View.GONE);

        }
        ((Button) holder.getView(R.id.tv_question)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (Build.MODEL.equals("NLS-MT90")){
                    intent = new Intent(context, NuclearGoodsActivity.class);
                }else {
                    intent = new Intent(context, DDJNuclearGoodsActivity.class);
                }
                intent.putExtra("customer_id",bean.getCustomer_id()+"");
                intent.putExtra("customer_code",bean.getLineCode()+"-"+bean.getCustomerLineCode());
                intent.putExtra("num",num);

                context.startActivity(intent);
            }
        });





    }
}
