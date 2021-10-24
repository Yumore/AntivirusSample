package com.nathaniel.baseui.adapter;

import android.view.View;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @datetime 2020/4/19 - 16:58
 */
public interface OnItemClickListener {
    /**
     * item click
     *
     * @param adapter  adapter
     * @param view     view
     * @param position position
     */
    void onItemClick(BaseRecyclerAdapter<?> adapter, View view, int position);
}
