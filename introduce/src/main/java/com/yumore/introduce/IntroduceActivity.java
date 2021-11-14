package com.yumore.introduce;

import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nathaniel.baseui.callback.HandlerCallback;
import com.nathaniel.baseui.surface.BaseActivity;
import com.nathaniel.baseui.utility.GlobalHandler;
import com.nathaniel.baseui.utility.ScreenUtils;
import com.nathaniel.utility.RouterConstants;
import com.nathaniel.utility.provider.IMasterProvider;
import com.yumore.introduce.databinding.ActivityIntroduceBinding;

import java.util.ArrayList;
import java.util.List;


/**
 * @author nathaniel
 */
public class IntroduceActivity extends BaseActivity<ActivityIntroduceBinding> implements ViewPager.OnPageChangeListener, View.OnClickListener, HandlerCallback {
    private static final int[] IMAGE_RESOURCES = {
        R.drawable.icon_indicator_00,
        R.drawable.icon_indicator_01,
        R.drawable.icon_indicator_02,
        R.drawable.icon_indicator_03,
        R.drawable.icon_indicator_04,
        R.drawable.icon_indicator_05,
        R.drawable.icon_indicator_06,
        R.drawable.icon_indicator_07,
        R.drawable.icon_indicator_08,
        R.drawable.icon_indicator_09,
        R.drawable.icon_indicator_10,
        R.drawable.icon_indicator_11,
        R.drawable.icon_indicator_12,
        R.drawable.icon_indicator_13
    };
    private static final int HANDLER_MESSAGE = 1, DELAY_MILLIS = 5 * 1000;
    private static final int DOT_SIZE = 8, DOT_MARGIN = 16;
    private List<Fragment> fragmentList;
    private LinearLayout.LayoutParams normalParams, focusParams;
    private int currentPosition;
    private int[] imageResources;
    private FragmentAdapter tractionAdapter;


    @Override
    protected ActivityIntroduceBinding initViewBinding() {
        return ActivityIntroduceBinding.inflate(getLayoutInflater());
    }

    @Override
    public void loadData() {
        fragmentList = new ArrayList<>();
        imageResources = IMAGE_RESOURCES;
        for (int imageResource : imageResources) {
            fragmentList.add(IntroduceFragment.newInstance(imageResource));
        }
        tractionAdapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList);
        normalParams = new LinearLayout.LayoutParams(ScreenUtils.dip2px(getApplicationContext(), DOT_SIZE), ScreenUtils.dip2px(getApplicationContext(), DOT_SIZE));
        normalParams.leftMargin = ScreenUtils.dip2px(getApplicationContext(), DOT_SIZE);
        focusParams = new LinearLayout.LayoutParams(ScreenUtils.dip2px(getApplicationContext(), DOT_MARGIN), ScreenUtils.dip2px(getApplicationContext(), DOT_SIZE));
        focusParams.leftMargin = ScreenUtils.dip2px(getApplicationContext(), DOT_SIZE);
    }

    @Override
    public void bindView() {
        viewBinding.tractionViewPagerVp.setAdapter(tractionAdapter);
        viewBinding.tractionViewPagerVp.setOffscreenPageLimit(fragmentList.size());
        View dotView;
        for (int i = 0; i < imageResources.length; i++) {
            dotView = new View(this);
            if (i == 0) {
                dotView.setLayoutParams(focusParams);
                dotView.setBackgroundResource(R.drawable.icon_dot_focus);
            } else {
                dotView.setLayoutParams(normalParams);
                dotView.setBackgroundResource(R.drawable.icon_dot_normal);
            }
            viewBinding.tractionDotsLayout.addView(dotView);
        }
        viewBinding.tractionDotsLayout.setGravity(Gravity.CENTER);
        viewBinding.tractionViewPagerVp.addOnPageChangeListener(this);
        viewBinding.enterButtonTv.setOnClickListener(this);
        GlobalHandler.getInstance().setHandlerCallback(this);
        GlobalHandler.getInstance().sendEmptyMessageDelayed(HANDLER_MESSAGE, DELAY_MILLIS);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        for (int i = 0; i < viewBinding.tractionDotsLayout.getChildCount(); i++) {
            View dotView = viewBinding.tractionDotsLayout.getChildAt(i);
            if (i == position) {
                dotView.setLayoutParams(focusParams);
                dotView.setBackgroundResource(R.drawable.icon_dot_focus);
            } else {
                dotView.setLayoutParams(normalParams);
                dotView.setBackgroundResource(R.drawable.icon_dot_normal);
            }

        }
        viewBinding.enterButtonTv.setVisibility(position >= 1 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onClick(View view) {
        IMasterProvider sampleProvider = ARouter.getInstance().navigation(IMasterProvider.class);
        sampleProvider.setIntroduceEnable(true);
        ARouter.getInstance().build(RouterConstants.TRACTION_HOME).navigation();
        finish();
    }

    @Override
    public void handleMessage(@NonNull Message message) {
        if (message.what == 1) {
            if (currentPosition == fragmentList.size() - 1) {
                currentPosition = 0;
                viewBinding.tractionViewPagerVp.setCurrentItem(0, false);
            } else {
                currentPosition++;
                viewBinding.tractionViewPagerVp.setCurrentItem(currentPosition, true);
            }
            GlobalHandler.getInstance().sendEmptyMessageDelayed(HANDLER_MESSAGE, DELAY_MILLIS);
        }
    }

    @Override
    protected void onDestroy() {
        GlobalHandler.getInstance().removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
