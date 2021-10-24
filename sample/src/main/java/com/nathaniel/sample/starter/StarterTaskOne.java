package com.nathaniel.sample.starter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nathaniel.sample.module.AntivirusModule;
import com.nathaniel.utility.ContextHelper;
import com.nathaniel.utility.JsonFileUtils;
import com.nathaniel.utility.SingletonUtils;
import com.nathaniel.utility.entity.AntivirusEntity;
import com.nathaniel.utility.entity.BaseEntity;
import com.nathaniel.utility.helper.AntivirusDaoHelper;
import com.wxy.appstartfaster.task.BaseStarterTask;

import java.util.List;

public class StarterTaskOne extends BaseStarterTask {

    @Override
    public void runTask() {
        long start = System.currentTimeMillis();
        Context context = ContextHelper.getInstance().getContext();
        BaseEntity<AntivirusEntity> baseEntity = new Gson().fromJson(JsonFileUtils.getJson(context, "antivirus.json"), new TypeToken<BaseEntity<AntivirusEntity>>() {
        }.getType());
        SingletonUtils.getInstance(AntivirusModule.class).setAntivirusEntities(baseEntity.getDataList());
        SingletonUtils.getInstance(AntivirusDaoHelper.class).inertOrUpdate(baseEntity.getDataList());
        Log.i("Task:", "TestAppStartTaskOne执行耗时: " + (System.currentTimeMillis() - start));
    }

    @Override
    public List<Class<? extends BaseStarterTask>> getDependsTaskList() {
        return null;
    }

    @Override
    public boolean runOnMainThread() {
        return true;
    }


}
