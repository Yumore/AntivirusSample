package com.nathaniel.sample.starter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nathaniel.sample.module.AntivirusModule;
import com.nathaniel.utility.ContextHelper;
import com.nathaniel.utility.JsonFileUtils;
import com.nathaniel.utility.SingletonUtils;
import com.nathaniel.utility.entity.BaseEntity;
import com.nathaniel.utility.entity.SpecimenEntity;
import com.wxy.appstartfaster.executor.TaskExceutorManager;
import com.wxy.appstartfaster.task.BaseStarterTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class StarterTaskTwo extends BaseStarterTask {

    @Override
    public void runTask() {
        long start = System.currentTimeMillis();
        Context context = ContextHelper.getInstance().getContext();
        BaseEntity<SpecimenEntity> baseEntity = new Gson().fromJson(JsonFileUtils.getJson(context, "specimens.json"), new TypeToken<BaseEntity<SpecimenEntity>>() {
        }.getType());
        SingletonUtils.getSingleton(AntivirusModule.class).setSpecimenEntities(baseEntity.getDataList());

        Log.i("Task:", "TestAppStartTaskTwo执行耗时: " + (System.currentTimeMillis() - start));
    }

    @Override
    public Executor runOnExecutor() {
        return TaskExceutorManager.getInstance().getCPUThreadPoolExecutor();
    }

    @Override
    public List<Class<? extends BaseStarterTask>> getDependsTaskList() {
        List<Class<? extends BaseStarterTask>> dependsTaskList = new ArrayList<>();
        dependsTaskList.add(StarterTaskOne.class);
        return dependsTaskList;
    }

    @Override
    public boolean runOnMainThread() {
        return false;
    }


}
