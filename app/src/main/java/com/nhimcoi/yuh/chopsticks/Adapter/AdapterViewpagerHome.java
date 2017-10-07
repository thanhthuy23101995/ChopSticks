package com.nhimcoi.yuh.chopsticks.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nhimcoi.yuh.chopsticks.Fragment.FragmentFood;
import com.nhimcoi.yuh.chopsticks.Fragment.FragmentPlaces;

/**
 * Created by Nhím Còi on 9/15/2017.
 */

public class AdapterViewpagerHome extends FragmentStatePagerAdapter {
    FragmentFood fragmentFood;
    FragmentPlaces fragmentPlaces;

    public AdapterViewpagerHome(FragmentManager fm) {
        super(fm);
        fragmentFood = new FragmentFood();
        fragmentPlaces = new FragmentPlaces();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return fragmentPlaces;
            case 1:
                return fragmentFood;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

}
