package com.nathaniel.baseui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nathaniel.baseui.R;


/**
 * @author Nathaniel
 * @version V1.0.0
 * @package com.nathaniel.baseui.adapter
 * @datetime 2020/4/5 - 1:32
 */
public class DefaultPassageView extends BasePassageView {
    private RelativeLayout commonPassageStatusRl;
    private ProgressBar commonPassageLoadingPb;
    private ImageView commonPassageStatusIv;
    private TextView commonPassageStatusTv;

    public DefaultPassageView(Context context) {
        super(context);
    }

    @Override
    protected int getPassageLayoutId() {
        return R.layout.common_defalut_passage;
    }

    @Override
    protected void initialize() {
        commonPassageStatusRl = findViewById(R.id.common_passage_status_rl);
        commonPassageLoadingPb = findViewById(R.id.common_passage_loading_pb);
        commonPassageStatusIv = findViewById(R.id.common_passage_status_iv);
        commonPassageStatusTv = findViewById(R.id.common_passage_status_tv);
    }

    @Override
    protected void setBeforeLoadingUi() {
        commonPassageStatusRl.setVisibility(View.GONE);
        commonPassageStatusTv.setText(R.string.passage_tips_initialize);
    }

    @Override
    protected void setOnLoadingUi() {
        commonPassageStatusRl.setVisibility(View.VISIBLE);
        commonPassageStatusTv.setVisibility(View.VISIBLE);
        commonPassageLoadingPb.setVisibility(View.VISIBLE);
        commonPassageStatusIv.setVisibility(View.GONE);
        commonPassageStatusTv.setText(R.string.passage_tips_loading);
    }

    @Override
    protected void setLoadSuccessUi() {
        commonPassageStatusRl.setVisibility(View.VISIBLE);
        commonPassageLoadingPb.setVisibility(View.GONE);
        commonPassageStatusIv.setVisibility(View.VISIBLE);
        commonPassageStatusIv.setImageResource(R.drawable.icon_passage_success);
        commonPassageStatusTv.setText(R.string.passage_tips_success);
    }

    @Override
    protected void setLoadFailUi() {
        commonPassageStatusRl.setVisibility(View.VISIBLE);
        commonPassageLoadingPb.setVisibility(View.GONE);
        commonPassageStatusIv.setVisibility(View.VISIBLE);
        commonPassageStatusIv.setImageResource(R.drawable.icon_passage_failure);
        commonPassageStatusTv.setText(R.string.passage_tips_failure);
    }

    @Override
    protected void setWithoutUi() {
        commonPassageStatusRl.setVisibility(View.GONE);
        commonPassageStatusTv.setText(R.string.passage_tips_without);
    }
}
