package com.nathaniel.utility.entity;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.nathaniel.utility.StringListConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.Arrays;
import java.util.List;


/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample
 * @datetime 4/29/21 - 8:00 PM
 */
@Entity
public class PackageEntity {
    @Id(autoincrement = true)
    private long id;
    private String packageName;
    private String versionName;
    private long versionCode;
    @Convert(converter = StringListConverter.class, columnType = String.class)
    private List<String> permissionList;
    @Convert(converter = StringListConverter.class, columnType = String.class)
    private List<String> processList;
    @Convert(converter = StringListConverter.class, columnType = String.class)
    private List<String> receiverList;
    @Convert(converter = StringListConverter.class, columnType = String.class)
    private List<String> providerList;
    @Convert(converter = StringListConverter.class, columnType = String.class)
    private List<String> serviceList;
    @Convert(converter = StringListConverter.class, columnType = String.class)
    private List<String> activityList;
    private String appName;
    @Transient
    private Drawable appIcon;
    private String signature;
    private long wifiRx;
    private long wifiTx;
    private long wifiTotal;
    private long mobileRx;
    private long mobileTx;
    private long mobileTotal;
    private int uid;
    private boolean overlay;
    private byte[] bitmaps;
    private String virusLevel = "安全";
    private boolean vestBagged;
    private String virusName;
    private long virusNumber;
    private String virusDescribe;

    @Generated(hash = 871948562)
    public PackageEntity(long id, String packageName, String versionName, long versionCode,
                         List<String> permissionList, List<String> processList, List<String> receiverList,
                         List<String> providerList, List<String> serviceList, List<String> activityList,
                         String appName, String signature, long wifiRx, long wifiTx, long wifiTotal, long mobileRx,
                         long mobileTx, long mobileTotal, int uid, boolean overlay, byte[] bitmaps,
                         String virusLevel, boolean vestBagged, String virusName, long virusNumber,
                         String virusDescribe) {
        this.id = id;
        this.packageName = packageName;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.permissionList = permissionList;
        this.processList = processList;
        this.receiverList = receiverList;
        this.providerList = providerList;
        this.serviceList = serviceList;
        this.activityList = activityList;
        this.appName = appName;
        this.signature = signature;
        this.wifiRx = wifiRx;
        this.wifiTx = wifiTx;
        this.wifiTotal = wifiTotal;
        this.mobileRx = mobileRx;
        this.mobileTx = mobileTx;
        this.mobileTotal = mobileTotal;
        this.uid = uid;
        this.overlay = overlay;
        this.bitmaps = bitmaps;
        this.virusLevel = virusLevel;
        this.vestBagged = vestBagged;
        this.virusName = virusName;
        this.virusNumber = virusNumber;
        this.virusDescribe = virusDescribe;
    }

    @Generated(hash = 1977018204)
    public PackageEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public long getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(long versionCode) {
        this.versionCode = versionCode;
    }

    public List<String> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<String> permissionList) {
        this.permissionList = permissionList;
    }

    public List<String> getProcessList() {
        return processList;
    }

    public void setProcessList(List<String> processList) {
        this.processList = processList;
    }

    public List<String> getReceiverList() {
        return receiverList;
    }

    public void setReceiverList(List<String> receiverList) {
        this.receiverList = receiverList;
    }

    public List<String> getProviderList() {
        return providerList;
    }

    public void setProviderList(List<String> providerList) {
        this.providerList = providerList;
    }

    public List<String> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<String> serviceList) {
        this.serviceList = serviceList;
    }

    public List<String> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<String> activityList) {
        this.activityList = activityList;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public long getWifiRx() {
        return wifiRx;
    }

    public void setWifiRx(long wifiRx) {
        this.wifiRx = wifiRx;
    }

    public long getWifiTx() {
        return wifiTx;
    }

    public void setWifiTx(long wifiTx) {
        this.wifiTx = wifiTx;
    }

    public long getWifiTotal() {
        return wifiTotal;
    }

    public void setWifiTotal(long wifiTotal) {
        this.wifiTotal = wifiTotal;
    }

    public long getMobileRx() {
        return mobileRx;
    }

    public void setMobileRx(long mobileRx) {
        this.mobileRx = mobileRx;
    }

    public long getMobileTx() {
        return mobileTx;
    }

    public void setMobileTx(long mobileTx) {
        this.mobileTx = mobileTx;
    }

    public long getMobileTotal() {
        return mobileTotal;
    }

    public void setMobileTotal(long mobileTotal) {
        this.mobileTotal = mobileTotal;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public boolean isOverlay() {
        return overlay;
    }

    public byte[] getBitmaps() {
        return bitmaps;
    }

    public void setBitmaps(byte[] bitmaps) {
        this.bitmaps = bitmaps;
    }

    public String getVirusLevel() {
        return virusLevel;
    }

    public void setVirusLevel(String virusLevel) {
        this.virusLevel = virusLevel;
    }

    public boolean isVestBagged() {
        return vestBagged;
    }

    public String getVirusName() {
        return virusName;
    }

    public void setVirusName(String virusName) {
        this.virusName = virusName;
    }

    public long getVirusNumber() {
        return virusNumber;
    }

    public void setVirusNumber(long virusNumber) {
        this.virusNumber = virusNumber;
    }

    public String getVirusDescribe() {
        return virusDescribe;
    }

    public void setVirusDescribe(String virusDescribe) {
        this.virusDescribe = virusDescribe;
    }

    @NonNull
    @Override
    public String toString() {
        return "PackageEntity{" +
            "id=" + id +
            ", packageName='" + packageName + '\'' +
            ", versionName='" + versionName + '\'' +
            ", versionCode=" + versionCode +
            ", permissionList=" + permissionList +
            ", processList=" + processList +
            ", receiverList=" + receiverList +
            ", providerList=" + providerList +
            ", serviceList=" + serviceList +
            ", activityList=" + activityList +
            ", appName='" + appName + '\'' +
            ", appIcon=" + appIcon +
            ", signature='" + signature + '\'' +
            ", wifiRx=" + wifiRx +
            ", wifiTx=" + wifiTx +
            ", wifiTotal=" + wifiTotal +
            ", mobileRx=" + mobileRx +
            ", mobileTx=" + mobileTx +
            ", mobileTotal=" + mobileTotal +
            ", uid=" + uid +
            ", overlay=" + overlay +
            ", bitmaps=" + Arrays.toString(bitmaps) +
            ", virusLevel='" + virusLevel + '\'' +
            ", vestBagged=" + vestBagged +
            ", virusName='" + virusName + '\'' +
            ", virusNumber=" + virusNumber +
            ", virusDescribe='" + virusDescribe + '\'' +
            '}';
    }

    public boolean getOverlay() {
        return this.overlay;
    }

    public void setOverlay(boolean overlay) {
        this.overlay = overlay;
    }

    public boolean getVestBagged() {
        return this.vestBagged;
    }

    public void setVestBagged(boolean vestBagged) {
        this.vestBagged = vestBagged;
    }
}
