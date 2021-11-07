package com.yumore.introduce;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @author nathaniel
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> fragmentList;

    public FragmentAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList) {
        super(fragmentManager);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        // Integer.MAX_VALUE;
        return fragmentList.size();
    }
}
