package com.tdj_sj_webandroid.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tdj_sj_webandroid.R;

import java.util.List;

public class SearchPopAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SearchPopAdapter(@Nullable List<String> data) {
        super(R.layout.search_pop_adapter,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_num,item);
    }
}
