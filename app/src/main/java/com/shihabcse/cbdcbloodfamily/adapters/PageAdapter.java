package com.shihabcse.cbdcbloodfamily.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.shihabcse.cbdcbloodfamily.fragments.BloodDonorFragment;
import com.shihabcse.cbdcbloodfamily.fragments.BloodRequestFragment;
import com.shihabcse.cbdcbloodfamily.fragments.CoOrdinatorFragment;
import com.shihabcse.cbdcbloodfamily.fragments.OrganizationFragment;

public class PageAdapter extends FragmentPagerAdapter {

    private  int tabsNumber;

    public PageAdapter(@NonNull FragmentManager fm, int behavior, int tabs) {
        super(fm, behavior);
        this.tabsNumber = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new BloodRequestFragment();
            case 1:
                return new CoOrdinatorFragment();
            case 2:
                return new OrganizationFragment();
            case 3:
                return new BloodDonorFragment();
                default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabsNumber;
    }
}
