package com.hjq.toast.dtoast;

import android.view.View;

import androidx.annotation.IdRes;


/**
 * @author Nathaniel
 */
public interface IToast {
    void show();

    void showLong();

    void cancel();

    View getView();

    IToast setView(View mView);

    IToast setDuration(@CustomToastUtils.Duration int duration);

    IToast setGravity(int gravity);

    IToast setGravity(int gravity, int xOffset, int yOffset);

    IToast setAnimation(int animation);

    IToast setPriority(int mPriority);

    IToast setText(@IdRes int id, String text);
}
