package com.nathaniel.utility.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.utility.module
 * @datetime 2021/10/16 - 13:12
 */
@Entity
public class AntivirusEntity {
    /**
     * "id": 1,
     * "version_code": "2.8.0",
     * "version_name": "208000",
     * "app_name": "芭比公主裁缝小游戏",
     * "package_name": "a.gd.qqfkzzhd.cps.jssj002",
     * "app_signature_v2": "4bb3d48e53a823d7718275ee458a4735",
     * "update_time": 1625979419960,
     * "app_signature_v3": "4bb3d48e53a823d7718275ee458a4735",
     * "target_sdk": "23"
     */
    @Id(autoincrement = true)
    private long id;
    @SerializedName(value = "packageName", alternate = {"package_name"})
    private String packageName;
    @SerializedName(value = "signature", alternate = {"app_signature_v2"})
    private String signature;
    private String virusLevel;
    @SerializedName(value = "versionName", alternate = {"version_name"})
    private String versionName;
    @SerializedName(value = "versionCode", alternate = {"version_code"})
    private String versionCode;
    @SerializedName(value = "appName", alternate = {"app_name"})
    private String appName;


    @Generated(hash = 859423392)
    public AntivirusEntity(long id, String packageName, String signature,
                           String virusLevel, String versionName, String versionCode,
                           String appName) {
        this.id = id;
        this.packageName = packageName;
        this.signature = signature;
        this.virusLevel = virusLevel;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.appName = appName;
    }

    @Generated(hash = 1016454003)
    public AntivirusEntity() {
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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getVirusLevel() {
        return virusLevel;
    }

    public void setVirusLevel(String virusLevel) {
        this.virusLevel = virusLevel;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}