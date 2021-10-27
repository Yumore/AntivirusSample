package com.nathaniel.sample.starter;

import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.TrafficStats;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.TelephonyManager;

import com.nathaniel.sample.BuildConfig;
import com.nathaniel.sample.module.AntivirusModule;
import com.nathaniel.sample.utility.AppUtils;
import com.nathaniel.sample.utility.EventConstants;
import com.nathaniel.sample.utility.PreferencesUtils;
import com.nathaniel.utility.BitmapCacheUtils;
import com.nathaniel.utility.ContextHelper;
import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.LoggerUtils;
import com.nathaniel.utility.PackageUtils;
import com.nathaniel.utility.SingletonUtils;
import com.nathaniel.utility.entity.EventMessage;
import com.nathaniel.utility.entity.PackageEntity;
import com.wxy.appstartfaster.task.BaseStarterTask;

import org.greenrobot.eventbus.EventBus;

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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageEntity.setVersionCode(packageInfo.getLongVersionCode());
            } else {
                packageEntity.setVersionCode(packageInfo.versionCode);
            }
            packageEntity.setUid(packageInfo.applicationInfo.uid);
            packageEntity.setOverlay(AppUtils.getOverlay(packageInfo));
            packageEntity.setBitmaps(AppUtils.drawable2bytes(packageInfo.applicationInfo.loadIcon(packageManager)));
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
            AppUtils.getAppSizeInfo(context, packageEntity.getPackageName(), new IPackageStatsObserver() {
                @Override
                public void onGetStatsCompleted(PackageStats packageStats, boolean succeeded) {
                    if (EmptyUtils.isEmpty(packageStats) || !succeeded) {
                        packageEntity.setSizeValube(false);
                    }
                    packageEntity.setCodeSize(packageStats.codeSize);
                    packageEntity.setCacheSize(packageStats.cacheSize);
                    packageEntity.setDataSize(packageStats.dataSize);
                    packageEntity.setSizeValube(true);
                }

                @Override
                public IBinder asBinder() {
                    return null;
                }
            });
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                // TODO 需要统计重启机器的
                packageEntity.setMobileRx(TrafficStats.getUidRxBytes(packageInfo.applicationInfo.uid));
                packageEntity.setMobileTx(TrafficStats.getUidTxBytes(packageInfo.applicationInfo.uid));
                packageEntity.setMobileTotal(packageEntity.getMobileRx() + packageEntity.getMobileTx());
            } else {
                NetworkStatsManager networkStatsManager = (NetworkStatsManager) context.getSystemService(Context.NETWORK_STATS_SERVICE);
                // 获取subscriberId
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String subscriberId;
                try {
                    NetworkStats summaryStats;
                    NetworkStats.Bucket summaryBucket = new NetworkStats.Bucket();
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                        subscriberId = telephonyManager.getSubscriberId();
                    } else {
                        subscriberId = PreferencesUtils.getInstance(context).getSubscribeId();
                    }
                    summaryStats = networkStatsManager.querySummary(ConnectivityManager.TYPE_WIFI, subscriberId, AppUtils.getFirstDayTimestamp(), System.currentTimeMillis());
                    do {
                        summaryStats.getNextBucket(summaryBucket);
                        int summaryUid = summaryBucket.getUid();
                        int uid = AppUtils.getUidByPackageName(context, packageInfo.packageName);
                        if (uid == summaryUid) {
                            packageEntity.setWifiRx(summaryBucket.getRxBytes());
                            packageEntity.setWifiTx(summaryBucket.getTxBytes());
                            packageEntity.setWifiTotal(summaryBucket.getRxBytes() + summaryBucket.getTxBytes());
                            LoggerUtils.logger("uid:" + summaryBucket.getUid() + " rx:" + summaryBucket.getRxBytes() + " tx:" + summaryBucket.getTxBytes());
                        }
                    } while (summaryStats.hasNextBucket());
                    summaryStats = networkStatsManager.querySummary(ConnectivityManager.TYPE_MOBILE, subscriberId, AppUtils.getFirstDayTimestamp(), System.currentTimeMillis());
                    do {
                        summaryStats.getNextBucket(summaryBucket);
                        int summaryUid = summaryBucket.getUid();
                        int uid = AppUtils.getUidByPackageName(context, packageInfo.packageName);
                        if (uid == summaryUid) {
                            packageEntity.setMobileRx(summaryBucket.getRxBytes());
                            packageEntity.setMobileRx(summaryBucket.getTxBytes());
                            packageEntity.setMobileTotal(summaryBucket.getRxBytes() + summaryBucket.getTxBytes());
                            LoggerUtils.logger("uid:" + summaryBucket.getUid() + " rx:" + summaryBucket.getRxBytes() + " tx:" + summaryBucket.getTxBytes());
                        }
                    } while (summaryStats.hasNextBucket());
                } catch (SecurityException | RemoteException e) {
                    e.printStackTrace();
                }
            }
            packageEntities.add(packageEntity);
        }
        SingletonUtils.getInstance(AntivirusModule.class).setPackageEntities(packageEntities);
        EventBus.getDefault().post(new EventMessage<>(EventConstants.TASK_FINISH_PACKAGE));
        LoggerUtils.logger("TestAppStartTaskFour执行耗时: " + (System.currentTimeMillis() - start));
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
