package com.nathaniel.baseui.callback;

/**
 * @author Nathaniel
 * @date 2019/4/19 - 18:42
 */
public interface OnActivityToFragment<T> {
    /**
     * 通过事件从fragment中获取数据
     *
     * @param t data
     */
    void onCallback(T t);
}
