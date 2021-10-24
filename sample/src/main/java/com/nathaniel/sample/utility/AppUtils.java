package com.nathaniel.sample.utility;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.TrafficStats;
import android.os.Build;
import android.os.PowerManager;
import android.os.RemoteException;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.nathaniel.sample.BuildConfig;
import com.nathaniel.sample.R;
import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.LoggerUtils;
import com.nathaniel.utility.SingletonUtils;
import com.nathaniel.utility.entity.PackageEntity;
import com.nathaniel.utility.helper.PackageDaoHelper;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample
 * @datetime 4/29/21 - 7:57 PM
 */
public class AppUtils {

    private static final String TAG = AppUtils.class.getSimpleName();

    private static List<String> getAllInstalledApkInfo(Context context) {
        List<String> packageNames = new ArrayList<>();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        @SuppressLint("QueryPermissionsNeeded")
        List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfoList) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            packageNames.add(activityInfo.applicationInfo.packageName);
        }
        return packageNames;
    }

    private static Drawable getAppIconByPackageName(Context context, String packageName) {
        Drawable drawable;
        try {
            drawable = context.getPackageManager().getApplicationIcon(packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            drawable = ContextCompat.getDrawable(context, R.mipmap.ic_launcher);
        }
        return drawable;
    }

    private static String getAppNameByPackageName(Context context, String packageName) {
        String appName = "";
        ApplicationInfo applicationInfo;
        PackageManager packageManager = context.getPackageManager();
        try {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            if (applicationInfo != null) {
                appName = (String) packageManager.getApplicationLabel(applicationInfo);
            }
        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }
        return appName;
    }

    private static String getVersionNameByPackageName(Context context, String packageName) {
        String versionName = "";
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            if (packageInfo != null) {
                versionName = packageInfo.versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }


    private static int getVersionCodeByPackageName(Context context, String packageName) {
        int versionCode = 0;
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            if (packageInfo != null) {
                versionCode = packageInfo.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static List<PackageEntity> getPackageEntityList(Context context) {
        List<PackageEntity> packageEntities = new ArrayList<>();
        List<String> packageNames = getAllInstalledApkInfo(context);
        for (String packageName : packageNames) {
            PackageEntity packageEntity = new PackageEntity();
            packageEntity.setAppName(getAppNameByPackageName(context, packageName));
            packageEntity.setPackageName(packageName);
            packageEntity.setAppIcon(getAppIconByPackageName(context, packageName));
            packageEntity.setVersionCode(getVersionCodeByPackageName(context, packageName));
            packageEntity.setVersionName(getVersionNameByPackageName(context, packageName));
            packageEntities.add(packageEntity);
        }
        return packageEntities;
    }

    @SuppressLint("HardwareIds")
    public static List<PackageEntity> getPackageEntities(Context context) {
        List<PackageEntity> packageEntities = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();
        StringBuilder stringBuilder = new StringBuilder();
        int flags = PackageManager.GET_META_DATA
            | PackageManager.GET_PERMISSIONS
            | PackageManager.GET_SERVICES
            | PackageManager.GET_RECEIVERS
            | PackageManager.GET_ACTIVITIES;
        @SuppressLint("QueryPermissionsNeeded")
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(flags);
        for (int i = 0; i < installedPackages.size(); i++) {
            PackageInfo packageInfo = installedPackages.get(i);
            PackageEntity packageEntity = new PackageEntity();
            packageEntity.setAppName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
            packageEntity.setPackageName(packageInfo.packageName);
            packageEntity.setVersionName(packageInfo.versionName);
            packageEntity.setVersionCode(packageInfo.versionCode);
            packageEntity.setAppIcon(packageInfo.applicationInfo.loadIcon(packageManager));
            packageEntity.setUid(packageInfo.applicationInfo.uid);
            packageEntity.setAppName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
            packageEntity.setOverlay(getOverlay(packageInfo));
            packageEntity.setBitmaps(drawable2bytes(packageInfo.applicationInfo.loadIcon(packageManager)));
            boolean appendable = i >= 1 && i < installedPackages.size() - 1;
            stringBuilder.append(packageInfo.packageName);
            if (appendable) {
                stringBuilder.append(",");
            }
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
                    summaryStats = networkStatsManager.querySummary(ConnectivityManager.TYPE_WIFI, subscriberId, getFirstDayTimestamp(), System.currentTimeMillis());
                    do {
                        summaryStats.getNextBucket(summaryBucket);
                        int summaryUid = summaryBucket.getUid();
                        int uid = getUidByPackageName(context, packageInfo.packageName);
                        if (uid == summaryUid) {
                            packageEntity.setWifiRx(summaryBucket.getRxBytes());
                            packageEntity.setWifiTx(summaryBucket.getTxBytes());
                            packageEntity.setWifiTotal(summaryBucket.getRxBytes() + summaryBucket.getTxBytes());
                            LoggerUtils.logger(TAG, "uid:" + summaryBucket.getUid() + " rx:" + summaryBucket.getRxBytes() + " tx:" + summaryBucket.getTxBytes());
                        }
                    } while (summaryStats.hasNextBucket());
                    summaryStats = networkStatsManager.querySummary(ConnectivityManager.TYPE_MOBILE, subscriberId, getFirstDayTimestamp(), System.currentTimeMillis());
                    do {
                        summaryStats.getNextBucket(summaryBucket);
                        int summaryUid = summaryBucket.getUid();
                        int uid = getUidByPackageName(context, packageInfo.packageName);
                        if (uid == summaryUid) {
                            packageEntity.setMobileRx(summaryBucket.getRxBytes());
                            packageEntity.setMobileRx(summaryBucket.getTxBytes());
                            packageEntity.setMobileTotal(summaryBucket.getRxBytes() + summaryBucket.getTxBytes());
                            LoggerUtils.logger(TAG, "uid:" + summaryBucket.getUid() + " rx:" + summaryBucket.getRxBytes() + " tx:" + summaryBucket.getTxBytes());
                        }
                    } while (summaryStats.hasNextBucket());
                } catch (SecurityException | RemoteException e) {
                    e.printStackTrace();
                }
            }
            context.getSystemService(Context.NOTIFICATION_SERVICE);
//            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            packageEntities.add(packageEntity);
//            }
        }
        SingletonUtils.getSingleton(PackageDaoHelper.class).insertMultiply(packageEntities);
        write2resXml(context, stringBuilder.toString());
        Collections.sort(packageEntities, (source, target) -> source.getAppName().compareTo(target.getAppName()));
        return packageEntities;
    }

    public static void write2resXml(Context context, CharSequence packageNames) {
        XmlResourceParser xmlResourceParser = context.getResources().getXml(R.xml.accessibility_service);
        try {
            int eventType = xmlResourceParser.getEventType();
            while (eventType != XmlResourceParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlResourceParser.START_DOCUMENT:
                        LoggerUtils.logger(TAG, "开始解析");
                        break;
                    case XmlPullParser.START_TAG:
                        LoggerUtils.logger(TAG, "解析标签开始");
                        String parserName = xmlResourceParser.getName();
                        if (parserName.contains("accessibility-service")) {
                            for (int i = 0; i < xmlResourceParser.getAttributeCount(); i++) {
                                if (xmlResourceParser.getAttributeName(i).equals("packageNames")) {
                                    LoggerUtils.logger(TAG, "开始写入数据：" + packageNames);
                                    // xmlResourceParser.setProperty(xmlResourceParser.getAttributeName(i), packageNames);
                                }
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        LoggerUtils.logger(TAG, "解析标签结束");
                        break;
                    default:
                        break;
                }
                eventType = xmlResourceParser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void getRunningTasks(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(1);
        Log.d("topActivity", "CURRENT Activity ::" + taskInfo.get(0).topActivity.getClassName());
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        LoggerUtils.logger(TAG, "componentInfo" + componentInfo.getPackageName());
    }

    public static boolean getOverlay(PackageInfo packageInfo) {
        LoggerUtils.logger(TAG, "开始解析PackageInfo ：" + packageInfo.packageName + ", " + (EmptyUtils.isEmpty(packageInfo.services) ? 0 : packageInfo.services.length));
        String[] requestedPermissions = packageInfo.requestedPermissions;
        ServiceInfo[] serviceInfos = packageInfo.services;
        int count = 0;
        if (!EmptyUtils.isEmpty(serviceInfos)) {
            for (ServiceInfo serviceInfo : serviceInfos) {
                LoggerUtils.logger(TAG, "ServiceInfo : " + serviceInfo.name + ", exported : " + serviceInfo.exported);
                if (EmptyUtils.isEmpty(serviceInfo.permission)) {
                    continue;
                }
                if (serviceInfo.permission.contains("BIND_ACCESSIBILITY_SERVICE") || serviceInfo.permission.contains("bind_accessibility_service")) {
                    count++;
                }
            }
        }
        if (!EmptyUtils.isEmpty(requestedPermissions)) {
            for (String requestedPermission : requestedPermissions) {
                if (requestedPermission.contains("system_alert_window") || requestedPermission.contains("SYSTEM_ALERT_WINDOW")) {
                    LoggerUtils.logger(TAG, packageInfo.packageName + " : system_alert_window");
                    count++;
                }
                if (requestedPermission.contains("disable_keyguard") || requestedPermission.contains("DISABLE_KEYGUARD")) {
                    LoggerUtils.logger(TAG, packageInfo.packageName + " : disable_keyguard");
                    count++;
                }
            }
        }
        return count > 0;
    }

    @SuppressLint("MissingPermission")
    private static boolean getOverlayV1(Context context, PackageInfo packageInfo) {
        //屏锁管理器
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
        //解锁
        kl.disableKeyguard();
        //获取电源管理器对象
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, BuildConfig.APPLICATION_ID + ":bright");
        //点亮屏幕
        wl.acquire();
        //释放
        wl.release();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            Context packageContext;
            try {
                packageContext = context.createPackageContext(packageInfo.packageName, Context.CONTEXT_IGNORE_SECURITY);
                if (Settings.canDrawOverlays(packageContext)) {
                    return true;
                }
                ActivityManager activityManager = (ActivityManager) packageContext.getSystemService(Context.ACTIVITY_SERVICE);
                NotificationManager notificationManager = (NotificationManager) packageContext.getSystemService(Context.NOTIFICATION_SERVICE);
                KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
                keyguardManager.isKeyguardSecure();
                ActivityInfo[] activityInfos = packageInfo.activities;
                //  ActivityManager.getService().setShowWhenLocked(mToken, showWhenLocked);
                PackageManager packageManager = packageContext.getPackageManager();
                if (!EmptyUtils.isEmpty(activityInfos)) {
                    for (ActivityInfo activityInfo : activityInfos) {
                        LoggerUtils.logger(TAG, "activityInfo: " + activityInfo.name + ", " + activityInfo.loadLabel(packageManager));

                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }
        } else {
            try {
                Context packageContext = context.createPackageContext(packageInfo.packageName, Context.CONTEXT_IGNORE_SECURITY);
                if (Settings.canDrawOverlays(packageContext)) {
                    return true;
                }
                WindowManager windowManager = (WindowManager) packageContext.getSystemService(Context.WINDOW_SERVICE);
                if (windowManager == null) {
                    LoggerUtils.logger(TAG, packageContext.getApplicationInfo().className);
                    return false;
                }
                View viewToAdd = new View(packageContext);
                WindowManager.LayoutParams params = new WindowManager.LayoutParams(0, 0, android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ?
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSPARENT);
                viewToAdd.setLayoutParams(params);
                windowManager.addView(viewToAdd, params);
                windowManager.removeView(viewToAdd);
                return true;
//                AppOpsManager appOpsMgr = (AppOpsManager) packageContext.getSystemService(Context.APP_OPS_SERVICE);
//                int mode = appOpsMgr.checkOpNoThrow("android:system_alert_window", packageInfo.applicationInfo.uid, packageInfo.packageName);
//                if (mode == 1 || mode == 0) {
//                    //权限已开起
//                    //此方法不可行，亲测华为8.0手机  未申请状态时  也返回1
//                    if (Build.MANUFACTURER.equalsIgnoreCase("huawei")) {
//                        String[] requestedPermissions = packageInfo.requestedPermissions;
//                        if (EmptyUtils.isEmpty(requestedPermissions)) {
//                            return false;
//                        } else {
//                            List<String> permissions = Arrays.asList(requestedPermissions);
//                            return permissions.contains("system_alert_window") || permissions.contains("SYSTEM_ALERT_WINDOW");
//                        }
//                    } else {
//                        return true;
//                    }
//                } else if (mode == 2) {
//                    //权限已关闭
//                    return false;
//                }
//                LoggerUtils.logger(TAG, "mode = " + mode);
            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }
        }
        return false;
    }

    public static List<PackageEntity> queryPackageList() {
        List<PackageEntity> packageEntities = SingletonUtils.getSingleton(PackageDaoHelper.class).queryAll();
        for (PackageEntity packageEntity : packageEntities) {
            packageEntity.setAppIcon(bytes2drawable(packageEntity.getBitmaps()));
        }
        return packageEntities;
    }

    public static long[] getNetworkUsage() {
        BufferedReader bufferedReader;
        String line;
        String[] values;
        long[] totalBytes = new long[2];
        try {
            bufferedReader = new BufferedReader(new FileReader("/proc/net/dev"));
            while ((line = bufferedReader.readLine()) != null) {
                // LoggerUtils.logger(TAG, String.format("source content is : %s", line.trim()));
                if (line.contains("eth") || line.contains("lo") || line.contains("wlan")) {
                    values = line.trim().split("\\s+");
                    totalBytes[0] += Long.parseLong(values[1]);
                    totalBytes[1] += Long.parseLong(values[9]);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return totalBytes;
    }


    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static long getWiFiSummaryMonth(Context context) {
        long summaryTotal = 0;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            return summaryTotal;
        } else {
            try {
                NetworkStatsManager networkStatsManager = (NetworkStatsManager) context.getSystemService(Context.NETWORK_STATS_SERVICE);
                NetworkStats.Bucket bucket = null;
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                // 获取到目前为止设备的Wi-Fi流量统计
                String subscriberId = null;
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    subscriberId = telephonyManager.getSubscriberId();
                } else {
                    subscriberId = PreferencesUtils.getInstance(context).getSubscribeId();
                }
                bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_WIFI, subscriberId, getFirstDayTimestamp(), System.currentTimeMillis());
                summaryTotal = bucket.getRxBytes() + bucket.getTxBytes();
                LoggerUtils.logger(TAG, "Total: " + (bucket.getRxBytes() + bucket.getTxBytes()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return summaryTotal;
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static long getMobileSummaryMonth(Context context) {
        long summaryTotal = 0;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            return summaryTotal;
        } else {
            try {
                NetworkStatsManager networkStatsManager = (NetworkStatsManager) context.getSystemService(Context.NETWORK_STATS_SERVICE);
                NetworkStats.Bucket bucket = null;
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                // 获取到目前为止设备的Wi-Fi流量统计
                String subscriberId = null;
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    subscriberId = telephonyManager.getSubscriberId();
                } else {
                    subscriberId = PreferencesUtils.getInstance(context).getSubscribeId();
                }
                bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_MOBILE, subscriberId, getFirstDayTimestamp(), System.currentTimeMillis());
                summaryTotal = bucket.getRxBytes() + bucket.getTxBytes();
                LoggerUtils.logger(TAG, "Total: " + (bucket.getRxBytes() + bucket.getTxBytes()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return summaryTotal;
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static void getNetworkUsage1(Context context, String packageName) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            return;
        }
        NetworkStatsManager networkStatsManager = (NetworkStatsManager) context.getSystemService(Context.NETWORK_STATS_SERVICE);
        // 获取subscriberId
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        NetworkStats summaryStats;
        long summaryRx = 0;
        long summaryTx = 0;
        NetworkStats.Bucket summaryBucket = new NetworkStats.Bucket();
        long summaryTotal = 0;
        try {
            String subscriberId = null;
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                subscriberId = telephonyManager.getSubscriberId();
            } else {
                subscriberId = PreferencesUtils.getInstance(context).getSubscribeId();
            }
            summaryStats = networkStatsManager.querySummary(ConnectivityManager.TYPE_MOBILE, subscriberId, getFirstDayTimestamp(), System.currentTimeMillis());
            do {
                summaryStats.getNextBucket(summaryBucket);
                int summaryUid = summaryBucket.getUid();
                int uid = getUidByPackageName(context, packageName);
                if (uid == summaryUid) {
                    summaryRx += summaryBucket.getRxBytes();
                    summaryTx += summaryBucket.getTxBytes();
                }
                LoggerUtils.logger(TAG, "uid:" + summaryBucket.getUid() + " rx:" + summaryBucket.getRxBytes() + " tx:" + summaryBucket.getTxBytes());
                summaryTotal += summaryBucket.getRxBytes() + summaryBucket.getTxBytes();
            } while (summaryStats.hasNextBucket());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static int getUidByPackageName(Context context, String packageName) {
        int uid = -1;
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_META_DATA);
            uid = packageInfo.applicationInfo.uid;
            LoggerUtils.logger(TAG, packageInfo.packageName + " uid:" + uid);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return uid;
    }

    public static long getFirstDayTimestamp() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTimeInMillis();
    }

    public static long[] getNetworkUsageByUid(long uid) {
        BufferedReader bufferedReader;
        String line;
        String[] values;
        long[] totalBytes = new long[2];
        try {
            @SuppressLint("DefaultLocale")
            String filePath = String.format("/proc/%d/net/dev", uid);
            bufferedReader = new BufferedReader(new FileReader(filePath));
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("eth") || line.contains("lo") || line.contains("wlan")) {
                    values = line.trim().split("\\s+");
                    totalBytes[0] += Long.parseLong(values[1]);
                    totalBytes[1] += Long.parseLong(values[9]);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return totalBytes;
    }

    public static List<String[]> getNetworkUsageAllUid() {
        BufferedReader bufferedReader;
        String line;
        List<String[]> stringsList = new ArrayList<>();
        try {
            String filePath = "/proc/net/xt_qtaguid/stats";
            if (!new File(filePath).exists()) {
                return stringsList;
            }
            bufferedReader = new BufferedReader(new FileReader(filePath));
            while ((line = bufferedReader.readLine()) != null) {
                LoggerUtils.logger(TAG, String.format("source content is : %s", line.trim()));
                if (line.contains("idx")) {
                    continue;
                }
                String[] values = line.trim().split("\\s+");
                stringsList.add(values);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringsList;
    }

    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context Context
     * @return 当前应用的版本名称
     */
    public static synchronized String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context Context
     * @return 当前应用的版本名称
     */
    public static synchronized int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context Context
     * @return 当前应用的版本名称
     */
    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取图标 bitmap
     *
     * @param context     Context
     * @param packageName packageName
     */
    private static synchronized Drawable getBitmap(Context context, String packageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            return packageManager.getApplicationIcon(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            return context.getResources().getDrawable(R.mipmap.ic_launcher);
        }
    }


    public static boolean accessPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.getPackageName());
                return mode == AppOpsManager.MODE_ALLOWED;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return true;
        }
    }

    public static byte[] drawable2bytes(Drawable drawable) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = drawableToBitmap(drawable);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Drawable bytes2drawable(byte[] byteArray) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return new BitmapDrawable(bitmap);
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public long getAllRxBytesMobile(Context context) {
        final long startTime = 0;
        NetworkStats.Bucket bucket;
        NetworkStatsManager networkStatsManager = (NetworkStatsManager) context.getSystemService(Context.NETWORK_STATS_SERVICE);
        try {
            bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_MOBILE,
                getSubscriberId(context, ConnectivityManager.TYPE_MOBILE),
                startTime,
                System.currentTimeMillis());
        } catch (RemoteException e) {
            return -1;
        }
        return bucket.getRxBytes();
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    private String getSubscriberId(Context context, int networkType) {
        if (ConnectivityManager.TYPE_MOBILE == networkType) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getSubscriberId();
        }
        return "";
    }

    public void listInstallPackages(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String[] packageNames = null;
        int uid = 1000;
        while (uid <= 19999) {
            packageNames = packageManager.getPackagesForUid(uid);
            if (packageNames != null && packageNames.length > 0) {
                for (String item : packageNames) {
                    try {
                        final PackageInfo packageInfo = packageManager.getPackageInfo(item, 0);
                        if (packageInfo == null) {
                            break;
                        }
                        CharSequence applicationLabel = packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageInfo.packageName, PackageManager.GET_META_DATA));
                        LoggerUtils.logger(TAG, LoggerUtils.Level.DEBUG, "应用名称 = " + applicationLabel.toString() + " (" + packageInfo.packageName + ")");
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            uid++;
        }
    }
}
