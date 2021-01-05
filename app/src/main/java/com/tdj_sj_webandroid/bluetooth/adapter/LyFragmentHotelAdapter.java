package com.tdj_sj_webandroid.bluetooth.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tdj_sj_webandroid.bluetooth.fragment.LyGoodsHotelFragment;

import java.util.List;

public class LyFragmentHotelAdapter extends FragmentStatePagerAdapter {

    private List<String> mTitles;

    public LyFragmentHotelAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return LyGoodsHotelFragment.newInstance(position);
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
