package com.example.rabee.breath.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.rabee.breath.fragments.HomeFragment;
import com.example.rabee.breath.fragments.NotificationFragment;
import com.example.rabee.breath.fragments.SettingsFragment;

/**
 * Created by Rabee on 1/19/2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public PagerAdapter(FragmentManager fm,int NumOfTabs) {
        super(fm);
        this.mNumOfTabs=NumOfTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                HomeFragment homeFragment=new HomeFragment();
                return homeFragment;
            case 1:
                NotificationFragment notificationFragment=new NotificationFragment();
                return notificationFragment;
            case 2 :
                SettingsFragment settingsFragment=new SettingsFragment();
                return settingsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
