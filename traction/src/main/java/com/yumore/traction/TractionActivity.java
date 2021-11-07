package com.yumore.traction;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.nathaniel.baseui.adapter.FragmentAdapter;
import com.nathaniel.baseui.callback.OnFragmentToActivity;
import com.nathaniel.baseui.surface.BaseActivity;
import com.yumore.provider.RouterConstants;
import com.yumore.provider.provider.IMasterProvider;
import com.yumore.traction.databinding.ActivityTractionBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nathaniel
 */
@Route(path = RouterConstants.TRACTION_HOME)
public class TractionActivity extends BaseActivity<ActivityTractionBinding> implements ViewPager.OnPageChangeListener, OnFragmentToActivity<Integer>, View.OnClickListener {
    private final int[] videoRes = new int[]{
        R.raw.guide1,
        R.raw.guide2,
        R.raw.guide3
    };
    private LinearLayout.LayoutParams focusedParams, unfocusedParams;

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void loadData() {
        List<Fragment> fragmentList = new ArrayList<>();
        focusedParams = new LinearLayout.LayoutParams(dip2px(getApplicationContext(), 7), dip2px(getApplicationContext(), 7));
        focusedParams.leftMargin = dip2px(getApplicationContext(), 15);
        unfocusedParams = new LinearLayout.LayoutParams(dip2px(getApplicationContext(), 10), dip2px(getApplicationContext(), 10));
        unfocusedParams.leftMargin = dip2px(getApplicationContext(), 15);
        View dotView;
        for (int i = 0; i < videoRes.length; i++) {
            dotView = new View(this);
            if (i == 0) {
                dotView.setLayoutParams(unfocusedParams);
                dotView.setBackgroundResource(R.drawable.tranction_shape_circle_magenta_solid);
            } else {
                dotView.setLayoutParams(focusedParams);
                dotView.setBackgroundResource(R.drawable.tranction_shape_circle_gray_solid);
            }
            getBinding().indicatorLayout.addView(dotView);
        }

        for (int i = 0; i < videoRes.length; i++) {
            TractionFragment fragment = new TractionFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("res", videoRes[i]);
            bundle.putInt("page", i);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList);
        getBinding().tractionViewpager.setOffscreenPageLimit(fragmentList.size());
        getBinding().tractionViewpager.setAdapter(fragmentAdapter);
    }

    @Override
    public void bindView() {
        getBinding().tractionViewpager.addOnPageChangeListener(this);
        getBinding().tractionLayout.bringToFront();
        getBinding().tractionEnter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.traction_enter) {
            IMasterProvider sampleProvider = ARouter.getInstance().navigation(IMasterProvider.class);
            sampleProvider.setTractionEnable(true);
            ARouter.getInstance().build(RouterConstants.EXAMPLE_NAVIGATE).navigation();
            finish();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < getBinding().indicatorLayout.getChildCount(); i++) {
            View dotView = getBinding().indicatorLayout.getChildAt(i);
            if (i == position) {
                dotView.setLayoutParams(unfocusedParams);
                dotView.setBackgroundResource(R.drawable.tranction_shape_circle_magenta_solid);
            } else {
                dotView.setLayoutParams(focusedParams);
                dotView.setBackgroundResource(R.drawable.tranction_shape_circle_gray_solid);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCallback(String action, Integer integer) {
        int position = getBinding().tractionViewpager.getCurrentItem();
        if (integer == position) {
            integer++;
            getBinding().tractionViewpager.setCurrentItem(integer, true);
        }
    }

    @NonNull
    @Override
    protected ActivityTractionBinding getViewBinding() {
        return ActivityTractionBinding.inflate(getLayoutInflater());
    }
}
