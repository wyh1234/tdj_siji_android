package com.tdj_sj_webandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tdj_sj_webandroid.fragment.NuclearGoodsHotelFragment;
import com.tdj_sj_webandroid.fragment.NuclearGoodsHotelItemFragment;

import java.util.List;

public class FragmentHotelItemAdapter  extends FragmentStatePagerAdapter {

    private List<String> mTitles;

    public FragmentHotelItemAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return NuclearGoodsHotelItemFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
