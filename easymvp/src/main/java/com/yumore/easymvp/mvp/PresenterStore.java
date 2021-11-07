package com.yumore.easymvp.mvp;

import java.util.HashMap;

/**
 * @author nathaniel
 */
public class PresenterStore<P extends BasePresenter> {

    private static final String DEFAULT_KEY = "PresenterStore.DefaultKey";
    private final HashMap<String, P> hashMap = new HashMap<>();

    public final void put(String key, P presenter) {
        P oldPresenter = hashMap.put(DEFAULT_KEY + ":" + key, presenter);
        if (oldPresenter != null) {
            oldPresenter.onCleared();
        }
    }

    public final P get(String key) {
        return hashMap.get(DEFAULT_KEY + ":" + key);
    }

    public final void clear() {
        for (P presenter : hashMap.values()) {
            presenter.onCleared();
        }
        hashMap.clear();
    }

    public int getSize() {
        return hashMap.size();
    }

    public HashMap<String, P> getMap() {
        return hashMap;
    }
}
