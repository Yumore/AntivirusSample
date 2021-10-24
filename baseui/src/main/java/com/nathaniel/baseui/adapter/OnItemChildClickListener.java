package com.nathaniel.baseui.adapter;

import android.view.View;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @datetime 2020/4/19 - 16:58
 */
public interface OnItemChildClickListener {
    /**
     * item click
     *
     * @param adapter  adapter
     * @param view     view
     * @param position position
     * @return true/false
     */
    boolean onItemChildClick(BaseRecyclerAdapter<?> adapter, View view, int position);
}
