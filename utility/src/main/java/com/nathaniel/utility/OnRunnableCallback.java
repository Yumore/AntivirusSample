package com.nathaniel.utility;

/**
 * @author Nathaniel
 */
public interface OnRunnableCallback<T> {
    /**
     * 开始预处理
     */
    void prepareRunnable();

    /**
     * 执行回调
     *
     * @param t T
     */
    void runnableCallback(T t);
}
