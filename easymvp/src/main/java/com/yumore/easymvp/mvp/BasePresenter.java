package com.yumore.easymvp.mvp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * @author nathaniel
 */
public class BasePresenter<V> {

    protected Context context;
    protected V baseView;

    protected void onCleared() {

    }

    public void attachView(Context context, V baseView) {
        this.context = context;
        this.baseView = baseView;
    }

    public void detachView() {
        this.baseView = null;
    }

    public boolean isAttachView() {
        return this.baseView != null;
    }

    public void onCreatePresenter(@Nullable Bundle savedState) {

    }

    public void onDestroyPresenter() {
        this.context = null;
        detachView();
    }

    public void onSaveInstanceState(Bundle outState) {

    }
}
