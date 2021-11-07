package com.yumore.easymvp.mvp;

/**
 * @author nathaniel
 */
public interface BaseMvpView {
    /**
     * 展示错误信息
     *
     * @param message message
     */
    void showError(String message);

    /**
     * 加载完成
     */
    void complete();

    /**
     * 展示加载动画
     *
     * @param display display
     */
    void showProgress(boolean display);
}
