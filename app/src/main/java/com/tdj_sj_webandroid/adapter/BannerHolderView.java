package com.tdj_sj_webandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.tdj_sj_webandroid.R;
import com.tdj_sj_webandroid.model.HomeInfo;
import com.tdj_sj_webandroid.utils.ImageLoad;


/**
 * Created by Administrator on 2018/4/17.
 *
 */

public class BannerHolderView implements Holder<HomeInfo.NoticeList> {
    ImageView bannerImg;
    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner_layout, null);

        bannerImg= view.findViewById(R.id.banner_img);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, HomeInfo.NoticeList data) {
        ImageLoad.loadImageView(context,data.getImg(), bannerImg);
    }
}
