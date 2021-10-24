package com.nathaniel.utility.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.utility.entity
 * @datetime 2021/10/23 - 15:28
 */
@Entity
public class PermissionEntity {
    @Id(autoincrement = true)
    private long id;
    private String permission_en;
    private String permission_cn;
    private String permission_desc;
    private int permission_level;
    private String permission_harm;

    @Generated(hash = 1302235986)
    public PermissionEntity(long id, String permission_en, String permission_cn,
                            String permission_desc, int permission_level, String permission_harm) {
        this.id = id;
        this.permission_en = permission_en;
        this.permission_cn = permission_cn;
        this.permission_desc = permission_desc;
        this.permission_level = permission_level;
        this.permission_harm = permission_harm;
    }

    @Generated(hash = 1153328455)
    public PermissionEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPermission_en() {
        return permission_en;
    }

    public void setPermission_en(String permission_en) {
        this.permission_en = permission_en;
    }

    public String getPermission_cn() {
        return permission_cn;
    }

    public void setPermission_cn(String permission_cn) {
        this.permission_cn = permission_cn;
    }

    public String getPermission_desc() {
        return permission_desc;
    }

    public void setPermission_desc(String permission_desc) {
        this.permission_desc = permission_desc;
    }

    public int getPermission_level() {
        return permission_level;
    }

    public void setPermission_level(int permission_level) {
        this.permission_level = permission_level;
    }

    public String getPermission_harm() {
        return permission_harm;
    }

    public void setPermission_harm(String permission_harm) {
        this.permission_harm = permission_harm;
    }
}