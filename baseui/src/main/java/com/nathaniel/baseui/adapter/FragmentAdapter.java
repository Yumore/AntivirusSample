package com.nathaniel.baseui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.nathaniel.utility.EmptyUtils;

import java.util.List;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.baseui.adapter
 * @datetime 2021/6/7 - 20:55
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;
    private List<CharSequence> titleList;

    public FragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public FragmentAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList) {
        super(fragmentManager);
        this.fragmentList = fragmentList;
    }

    public void setTitleList(List<CharSequence> titleList) {
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return EmptyUtils.isEmpty(fragmentList) ? null : fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return EmptyUtils.isEmpty(fragmentList) ? 0 : fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return EmptyUtils.isEmpty(titleList) ? null : titleList.get(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
