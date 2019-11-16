package com.tdj_sj_webandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tdj_sj_webandroid.fragment.NuclearGoodsHotelFragment;

import java.util.List;

public class FragmentHotelAdapter extends FragmentStatePagerAdapter {

    private List<String> mTitles;

    public FragmentHotelAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return NuclearGoodsHotelFragment.newInstance(position);
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
