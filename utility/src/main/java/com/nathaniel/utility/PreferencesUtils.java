package com.nathaniel.utility;

import android.annotation.SuppressLint;
import android.content.Context;

import java.util.UUID;


/**
 * @author Nathaniel
 * @datetime 2019/10/18 - 17:50
 */
public class PreferencesUtils extends BasePreferences {

    private static final String PREFERENCE_FILENAME = "common.sdf";
    /**
     * 是否出现过
     */
    private static final String KEY_TRACTION_ENABLED = "tractionEnable";
    private static final String KEY_INTRODUCE_ENABLED = "introduceEnable";
    private static final boolean DEFAULT_BOOLEAN_VALUE = false;

    private static final String KEY_SUBSCRIBE_ID = "subscribeId";


    @SuppressLint("StaticFieldLeak")
    private static PreferencesUtils preferencesUtils;

    private PreferencesUtils(Context context) {
        initialize(context);
    }

    public static synchronized PreferencesUtils getInstance(Context context) {
        if (EmptyUtils.isEmpty(preferencesUtils)) {
            preferencesUtils = new PreferencesUtils(context);
        }
        return preferencesUtils;
    }

    @Override
    protected String getSharedPreferencesName() {
        return PREFERENCE_FILENAME;
    }

    public boolean getTractionEnable() {
        return getBoolean(KEY_TRACTION_ENABLED, DEFAULT_BOOLEAN_VALUE);
    }

    public void setTractionEnable(boolean tractionEnable) {
        putBoolean(KEY_TRACTION_ENABLED, tractionEnable);
    }

    public boolean getIntroduceEnable() {
        return getBoolean(KEY_INTRODUCE_ENABLED, DEFAULT_BOOLEAN_VALUE);
    }

    public void setIntroduceEnable(boolean introduceEnable) {
        putBoolean(KEY_INTRODUCE_ENABLED, introduceEnable);
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

    public String getSubscribeId() {
        String subscribeId = getString(KEY_SUBSCRIBE_ID, null);
        if (EmptyUtils.isEmpty(subscribeId)) {
            subscribeId = UUID.randomUUID().toString();
            putString(KEY_SUBSCRIBE_ID, subscribeId);
        }
        return subscribeId;
    }
}
