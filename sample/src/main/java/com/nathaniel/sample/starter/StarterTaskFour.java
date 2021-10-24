package com.nathaniel.sample.starter;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.util.Log;

import com.nathaniel.sample.BuildConfig;
import com.nathaniel.sample.module.AntivirusModule;
import com.nathaniel.utility.BitmapCacheUtils;
import com.nathaniel.utility.ContextHelper;
import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.LoggerUtils;
import com.nathaniel.utility.PackageUtils;
import com.nathaniel.utility.SingletonUtils;
import com.nathaniel.utility.entity.PackageEntity;
import com.wxy.appstartfaster.task.BaseStarterTask;

import java.util.ArrayList;
import java.util.List;

public class StarterTaskFour extends BaseStarterTask {

    @Override
    public void runTask() {
        long start = System.currentTimeMillis();
        Context context = ContextHelper.getInstance().getContext();
        LoggerUtils.logger(LoggerUtils.TAG, "AntivirusActivity-getAppList-160", "开始扫描本地APP");
        List<PackageEntity> packageEntities = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();
        int flags = PackageManager.GET_META_DATA
            | PackageManager.GET_SIGNATURES
            | PackageManager.GET_PERMISSIONS
            | PackageManager.GET_PROVIDERS
            | PackageManager.GET_RECEIVERS
            | PackageManager.GET_SERVICES
            | PackageManager.GET_ACTIVITIES;
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(flags);
        for (int i = 0; i < installedPackages.size(); i++) {
            PackageInfo packageInfo = installedPackages.get(i);
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1
                || (applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 1
                || applicationInfo.packageName.equals(BuildConfig.APPLICATION_ID)) {
                // TODO 忽略系统app和自己
                continue;
            }
            PackageEntity packageEntity = new PackageEntity();
            packageEntity.setAppName(applicationInfo.loadLabel(packageManager).toString());
            packageEntity.setPackageName(applicationInfo.packageName);
            packageEntity.setVersionName(packageInfo.versionName);
            packageEntity.setVersionCode(packageInfo.getLongVersionCode());
            packageEntity.setAppIcon(BitmapCacheUtils.getBitmapFromCache(context, applicationInfo, packageManager));

            Signature[] signatures = packageInfo.signatures;
            if (!EmptyUtils.isEmpty(signatures)) {
                packageEntity.setSignature(PackageUtils.getSignatureMd5(signatures[0]));
            }

            PermissionInfo[] permissionInfos = packageInfo.permissions;
            if (!EmptyUtils.isEmpty(permissionInfos)) {
                List<String> permissions = new ArrayList<>();
                for (PermissionInfo permissionInfo : permissionInfos) {
                    if (permissions.contains(permissionInfo.name)) {
                        continue;
                    }
                    permissions.add(permissionInfo.name);
                }
                packageEntity.setPermissionList(permissions);
            }

            ProviderInfo[] providerInfos = packageInfo.providers;
            if (!EmptyUtils.isEmpty(providerInfos)) {
                List<String> providers = new ArrayList<>();
                for (ProviderInfo providerInfo : providerInfos) {
                    if (providers.contains(providerInfo.name)) {
                        continue;
                    }
                    providers.add(providerInfo.name);
                }
                packageEntity.setProviderList(providers);
            }

            List<String> processes = new ArrayList<>();
            ActivityInfo[] activityInfos = packageInfo.activities;
            if (!EmptyUtils.isEmpty(activityInfos)) {
                List<String> activities = new ArrayList<>();
                for (ActivityInfo activityInfo : activityInfos) {
                    if (activities.contains(activityInfo.name)) {
                        continue;
                    }
                    activities.add(activityInfo.name);
                    if (!EmptyUtils.isEmpty(activityInfo.processName) && !processes.contains(activityInfo.processName)) {
                        processes.add(activityInfo.processName);
                    }
                }
                packageEntity.setActivityList(activities);
            }

            ActivityInfo[] receiverInfos = packageInfo.receivers;
            if (!EmptyUtils.isEmpty(receiverInfos)) {
                List<String> receivers = new ArrayList<>();
                for (ActivityInfo activityInfo : receiverInfos) {
                    receivers.add(activityInfo.name);
                    if (!EmptyUtils.isEmpty(activityInfo.processName) && !processes.contains(activityInfo.processName)) {
                        processes.add(activityInfo.processName);
                    }
                }
                packageEntity.setReceiverList(receivers);
            }
            ServiceInfo[] serviceInfos = packageInfo.services;
            if (!EmptyUtils.isEmpty(serviceInfos)) {
                List<String> services = new ArrayList<>();
                for (ServiceInfo serviceInfo : serviceInfos) {
                    services.add(serviceInfo.name);
                    if (!EmptyUtils.isEmpty(serviceInfo.processName) && !processes.contains(serviceInfo.processName)) {
                        processes.add(serviceInfo.processName);
                    }
                }
                packageEntity.setServiceList(services);
            }
            packageEntity.setProcessList(processes);
            packageEntities.add(packageEntity);
        }
        SingletonUtils.getInstance(AntivirusModule.class).setPackageEntities(packageEntities);
        Log.i("Task:", "TestAppStartTaskFour执行耗时: " + (System.currentTimeMillis() - start));
    }

    @Override
    public List<Class<? extends BaseStarterTask>> getDependsTaskList() {
        List<Class<? extends BaseStarterTask>> dependsTaskList = new ArrayList<>();
        dependsTaskList.add(StarterTaskTwo.class);
        dependsTaskList.add(StarterTaskThree.class);
        return dependsTaskList;
    }

    @Override
    public boolean runOnMainThread() {
        return false;
    }

}
