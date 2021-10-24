package com.nathaniel.baseui;

import android.view.View;

import androidx.annotation.IdRes;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @package com.nathaniel.baseui.binding
 * @datetime 2021/3/31 - 20:33
 */
interface IViewBinding {
    /**
     * 获取布局文件
     *
     * @return layoutId
     */
    int getLayoutId();

    /**
     * initialize view
     */
    void initView();

    /**
     * before ui init
     */
    void beforeInit();

    /**
     * loading data
     */
    void loadData();

    /**
     * bind view with default value or bind click listener to view
     */
    void bindView();

    /**
     * show loading
     *
     * @param message message
     */
    void showLoading(CharSequence message);

    /**
     * dismiss loading
     */
    void dismissLoading();

    /**
     * obtain view
     *
     * @param viewId viewId
     * @param <T>    T
     * @return T
     */
    <T extends View> T obtainView(@IdRes int viewId);
}
