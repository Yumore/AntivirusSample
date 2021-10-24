package com.nathaniel.baseui.refresh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nathaniel.baseui.R;

/**
 * 下拉刷新尾部View
 * 尾部View必须实现RefreshLayout.OnFooterStateListener，并通过这个接口的回调来更新尾部View的状态。
 *
 * @author nathaniel
 */
public class FooterView extends RelativeLayout implements RefreshLayout.OnFooterStateListener {

    private final AnimationDrawable animationDrawable;
    private ImageView ivLoading;
    private TextView tvState;
    private boolean hasMore = true;

    private String footerPulling;
    private String footerRelease;
    private String footerLoading;
    private String footerLoadingFinish;
    private String footerLoadingFailure;
    private String footerNothing;

    @SuppressLint("UseCompatLoadingForDrawables")
    public FooterView(Context context) {
        super(context);
        animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.progress_round);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.footer_view_layout, this, false);
        this.addView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        initView(layout);

        Resources resources = context.getResources();
        footerPulling = resources.getString(R.string.footer_pulling);
        footerRelease = resources.getString(R.string.footer_release);
        footerLoading = resources.getString(R.string.footer_loading);
        footerLoadingFinish = resources.getString(R.string.footer_loading_finish);
        footerLoadingFailure = resources.getString(R.string.footer_loading_failure);
        footerNothing = resources.getString(R.string.footer_nothing);
    }

    private void initView(View view) {
        ivLoading = view.findViewById(R.id.iv_loading);
        tvState = view.findViewById(R.id.tv_state);
    }

    @Override
    public void onScrollChange(View tail, int scrollOffset, int scrollRatio) {
        if (hasMore) {
            if (scrollRatio < 100) {
                tvState.setText(footerPulling);
                ivLoading.setImageResource(R.mipmap.icon_arrow_down_grey);
                ivLoading.setRotation(180);
            } else {
                tvState.setText(footerRelease);
                ivLoading.setImageResource(R.mipmap.icon_arrow_down_grey);
                ivLoading.setRotation(0);
            }
        }
    }

    @Override
    public void onRefresh(View footerView) {
        if (hasMore) {
            tvState.setText(footerLoading);
            ivLoading.setImageDrawable(animationDrawable);
            animationDrawable.start();
        }
    }

    @Override
    public void onRetract(View footerView, boolean isSuccess) {
        if (hasMore) {
            tvState.setText(isSuccess ? footerLoadingFinish : footerLoadingFailure);
            ivLoading.setImageBitmap(null);
        }
    }

    @Override
    public void onHasMore(View tail, boolean hasMore) {
        this.hasMore = hasMore;
        if (!hasMore) {
            tvState.setText(footerNothing);
            ivLoading.setImageBitmap(null);
        }
    }
}
