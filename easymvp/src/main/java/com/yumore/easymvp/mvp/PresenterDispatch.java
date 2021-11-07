package com.yumore.easymvp.mvp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nathaniel
 */
public class PresenterDispatch {
    private final PresenterProviders presenterProviders;

    public PresenterDispatch(PresenterProviders providers) {
        presenterProviders = providers;
    }

    public <P extends BasePresenter> void attachView(Context context, Object view) {
        PresenterStore presenterStore = presenterProviders.getPresenterStore();
        HashMap<String, P> hashMap = presenterStore.getMap();
        for (Map.Entry<String, P> entry : hashMap.entrySet()) {
            P presenter = entry.getValue();
            if (presenter != null) {
                presenter.attachView(context, view);
            }
        }
    }

    public <P extends BasePresenter> void detachView() {
        PresenterStore presenterStore = presenterProviders.getPresenterStore();
        HashMap<String, P> hashMap = presenterStore.getMap();
        for (Map.Entry<String, P> entry : hashMap.entrySet()) {
            P presenter = entry.getValue();
            if (presenter != null) {
                presenter.detachView();
            }
        }
    }

    public <P extends BasePresenter> void onCreatePresenter(@Nullable Bundle savedState) {
        PresenterStore presenterStore = presenterProviders.getPresenterStore();
        HashMap<String, P> hashMap = presenterStore.getMap();
        for (Map.Entry<String, P> entry : hashMap.entrySet()) {
            P presenter = entry.getValue();
            if (presenter != null) {
                presenter.onCreatePresenter(savedState);
            }
        }
    }

    public <P extends BasePresenter> void onDestroyPresenter() {
        PresenterStore presenterStore = presenterProviders.getPresenterStore();
        HashMap<String, P> hashMap = presenterStore.getMap();
        for (Map.Entry<String, P> entry : hashMap.entrySet()) {
            P presenter = entry.getValue();
            if (presenter != null) {
                presenter.onDestroyPresenter();
            }
        }
    }

    public <P extends BasePresenter> void onSaveInstanceState(Bundle outState) {
        PresenterStore presenterStore = presenterProviders.getPresenterStore();
        HashMap<String, P> hashMap = presenterStore.getMap();
        for (Map.Entry<String, P> entry : hashMap.entrySet()) {
            P presenter = entry.getValue();
            if (presenter != null) {
                presenter.onSaveInstanceState(outState);
            }
        }
    }
}
