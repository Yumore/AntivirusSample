package com.nathaniel.sample.utility;

import android.content.Context;

import com.nathaniel.utility.BasePreferences;
import com.nathaniel.utility.EmptyUtils;

import java.util.UUID;

/**
 * @author Nathaniel
 */
public class PreferencesUtils extends BasePreferences {
    private static final String PREFERENCES_NAME = "common.sdf";
    private static final String KEY_SUBSCRIBE_ID = "subscribeId";
    private static PreferencesUtils instance;

    private PreferencesUtils(Context context) {
        initialize(context);
    }

    public static PreferencesUtils getInstance(Context context) {
        if (EmptyUtils.isEmpty(instance)) {
            instance = new PreferencesUtils(context);
        }
        return instance;
    }

    /**
     * 对全局变量指定写入一个long值.
     *
     * @param key   KEY
     * @param value 值
     */
    public void writeRefreshTime(final String key, final long value) {
        putLong(key, value);
    }

    public Long getRefreshTime(final String key) {
        return getLong(key, 0);
    }


    @Override
    protected String getSharedPreferencesName() {
        return PREFERENCES_NAME;
    }

    public String getSubscribeId() {
        String subscribeId = getString(KEY_SUBSCRIBE_ID, null);
        if (EmptyUtils.isEmpty(subscribeId)) {
            subscribeId = UUID.randomUUID().toString();
            putString(KEY_SUBSCRIBE_ID, subscribeId);
        }
        return subscribeId;
    }
}
