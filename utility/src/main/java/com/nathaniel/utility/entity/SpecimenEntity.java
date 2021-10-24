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
 * @datetime 2021/10/16 - 17:02
 */
@Entity
public class SpecimenEntity {
    /**
     * "id": 1,
     * "virus_name": "Android.Trojan.Pjapps",
     * "virus_number": 49657,
     * "virus_level": "高危",
     * "virus_describe": "Android Pjapps 是一种特洛伊木马，嵌入第三方应用程序，并在受攻击的设备上开启后门，并从远程命令和控制服务器接收指令。\r\n"
     */
    @Id(autoincrement = true)
    private long id;
    @SerializedName(value = "virusName", alternate = {"virus_name"})
    private String virusName;
    @SerializedName(value = "virusNumber", alternate = {"virus_number"})
    private long virusNumber;
    @SerializedName(value = "virusLevel", alternate = {"virus_level"})
    private String virusLevel;
    @SerializedName(value = "virusDescribe", alternate = {"virus_describe"})
    private String virusDescribe;

    @Generated(hash = 959651324)
    public SpecimenEntity(long id, String virusName, long virusNumber, String virusLevel,
                          String virusDescribe) {
        this.id = id;
        this.virusName = virusName;
        this.virusNumber = virusNumber;
        this.virusLevel = virusLevel;
        this.virusDescribe = virusDescribe;
    }

    @Generated(hash = 333761330)
    public SpecimenEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getVirusLevel() {
        return virusLevel;
    }

    public void setVirusLevel(String virusLevel) {
        this.virusLevel = virusLevel;
    }

    public String getVirusDescribe() {
        return virusDescribe;
    }

    public void setVirusDescribe(String virusDescribe) {
        this.virusDescribe = virusDescribe;
    }
}