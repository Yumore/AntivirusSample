package com.nathaniel.sample.surface;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nathaniel.baseui.AbstractActivity;
import com.nathaniel.baseui.adapter.FragmentAdapter;
import com.nathaniel.sample.R;
import com.nathaniel.utility.EmptyUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample.surface
 * @datetime 2021/6/7 - 20:42
 */
public class BrowserActivity extends AbstractActivity implements ViewPager.OnPageChangeListener, TabLayout.BaseOnTabSelectedListener {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_browser;
    }

    @Override
    public void loadData() {
        List<Fragment> fragmentList = new ArrayList<>();
        String[] itemNames = getResources().getStringArray(R.array.item_names);
        String[] itemWebsites = getResources().getStringArray(R.array.item_websites);
        for (int i = 0; i < itemNames.length; i++) {
            fragmentList.add(ContainerFragment.getInstance(i, itemWebsites[i]));
            tabLayout.addTab(tabLayout.newTab().setText(itemNames[i]));
        }
        List<CharSequence> titleList = Arrays.asList(itemNames);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList);
        fragmentAdapter.setTitleList(titleList);
    }

    @Override
    public void bindView() {
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        TabLayout.Tab tab = tabLayout.getTabAt(i);
        if (!EmptyUtils.isEmpty(tab)) {
            tab.select();
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
