package com.nathaniel.baseui;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.viewbinding.ViewBinding;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @package com.nathaniel.baseui.binding
 * @datetime 2021/3/31 - 20:33
 */
interface IViewBinding {

    /**
     * before ui init
     */
    void beforeInit();

    /**
     * 是否使用沉浸式状态栏
     * @return true|false
     */
    boolean immersionEnable();

    /**
     * 初始化沉浸式状态栏
     */
    void initImmersion();

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
}
