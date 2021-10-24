package com.nathaniel.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.nathaniel.utility.helper.InitializeHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * apply与commit异同：
 * 1. apply没有返回值而commit返回boolean表明修改是否提交成功
 * 2. apply是将修改数据原子提交到内存, 而后异步真正提交到硬件磁盘, 而commit是同步的提交到硬件磁盘，
 * 因此，在多个并发的提交commit的时候，他们会等待正在处理的commit保存到磁盘后在操作，从而降低了效率。
 * 而apply只是原子的提交到内容，后面有调用apply的函数的将会直接覆盖前面的内存数据，这样从一定程度上提高了很多效率。
 * 3. apply方法不会提示任何失败的提示。
 * 由于在一个进程中，sharedPreference是单实例，一般不会出现并发冲突，
 * 如果对提交的结果不关心的话，建议使用apply，当然需要确保提交成功且有后续操作的话，还是需要用commit的。
 * ----------------------------------
 * TODO 注意
 * 子类的方法需要使用 protected
 * 不对外公开
 *
 * @author Nathaniel
 * @version v1.0.0
 */
public abstract class BasePreferences implements InitializeHelper {
    private static final String TAG = BasePreferences.class.getSimpleName();
    private static final String DEFAULT_SHARED_PREFERENCES_NAME = "config.sdf";
    protected Context context;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    /**
     * 设置Module下的SharedPreferences的文件名
     *
     * @return SharedPreferences的文件名
     */
    protected abstract String getSharedPreferencesName();

    @SuppressLint("CommitPrefEdits")
    @Override
    public void initialize(@NonNull Context context) {
        this.context = context;
        if (TextUtils.isEmpty(getSharedPreferencesName())) {
            sharedPreferences = context.getSharedPreferences(DEFAULT_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        } else {
            sharedPreferences = context.getSharedPreferences(getSharedPreferencesName(), Context.MODE_PRIVATE);
        }
        editor = sharedPreferences.edit();
    }

    protected int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    protected void putInt(String key, int value) {
        editor.putInt(key, value).apply();
    }

    protected long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    protected void putLong(String key, long value) {
        editor.putLong(key, value).apply();
    }

    protected String getString(final String key, final String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    protected void putString(String key, String value) {
        editor.putString(key, value).apply();
    }

    protected boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    protected void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }

    protected <T> T getObject(String key, Class<T> clazz) {
        String json = getString(key, null);
        if (EmptyUtils.isEmpty(json)) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    protected <T> void putObject(String key, T data) {
        Gson gson = new Gson();
        putString(key, gson.toJson(data));
    }

    protected <T> void putList(String key, List<T> dataList) {
        Gson gson = new Gson();
        String json = gson.toJson(dataList);
        editor.putString(key, json);
    }

    protected <T> List<T> getList(String key, Type type) {
        List<T> dataList = new ArrayList<>();
        String json = getString(key, null);
        if (EmptyUtils.isEmpty(json) || "[]".equals(json)) {
            return dataList;
        } else {
            Gson gson = new Gson();
            if (gson.fromJson(json, type) instanceof JsonArray) {
                dataList = gson.fromJson(json, type);
            } else {
                LoggerUtils.logger(TAG, key + " json is " + json + ", after convert is not a jsonArray");
            }
        }
        return dataList;
    }

    protected void remove(String key) {
        editor.remove(key).apply();
    }

    protected void remove(String... keys) {
        if (EmptyUtils.isEmpty(keys)) {
            return;
        }
        for (String key : keys) {
            editor.remove(key);
        }
        editor.apply();
    }

    protected boolean hasKey(String key) {
        return sharedPreferences.contains(key);
    }

    protected boolean hasKey(String... keys) {
        boolean flag = true;
        if (EmptyUtils.isEmpty(keys)) {
            return false;
        }
        for (String key : keys) {
            flag = flag && sharedPreferences.contains(key);
        }
        return flag;
    }

    protected void clear() {
        editor.clear();
        editor.commit();
    }
}