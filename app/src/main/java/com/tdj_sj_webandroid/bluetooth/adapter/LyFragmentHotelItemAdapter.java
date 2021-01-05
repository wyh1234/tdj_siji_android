package com.tdj_sj_webandroid.bluetooth.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tdj_sj_webandroid.bluetooth.fragment.LyGoodsHotelItemFragment;
import com.tdj_sj_webandroid.fragment.NuclearGoodsHotelItemFragment;

import java.util.List;

public class LyFragmentHotelItemAdapter extends FragmentStatePagerAdapter {

    private List<String> mTitles;

    public LyFragmentHotelItemAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return LyGoodsHotelItemFragment.newInstance(position);
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
