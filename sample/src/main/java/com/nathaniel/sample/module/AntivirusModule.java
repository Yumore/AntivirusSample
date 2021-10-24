package com.nathaniel.sample.module;

import android.content.Context;

import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.JsonFileUtils;
import com.nathaniel.utility.entity.PackageEntity;
import com.nathaniel.utility.entity.PermissionEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample.module
 * @datetime 2021/10/17 - 8:33
 */
public class AntivirusModule {
    private PackageEntity packageEntity;

    public boolean isDangerous(Context context, String permission) {
        List<PermissionEntity> permissionEntities = JsonFileUtils.getPermissionList(context);
        if (EmptyUtils.isEmpty(permissionEntities)) {
            return false;
        }
        for (PermissionEntity permissionEntity : permissionEntities) {
            return permission.equalsIgnoreCase(permissionEntity.getPermission_en());
        }
        return false;
    }

    public List<DetailEntity> getAppDetail() {
        Map<String, List<String>> map = new HashMap<>();
        map.put("权限列表", packageEntity.getPermissionList());
        map.put("广播列表", packageEntity.getReceiverList());
        map.put("内容提供者", packageEntity.getProviderList());
        map.put("服务列表", packageEntity.getServiceList());
        map.put("进程列表", packageEntity.getProcessList());
        map.put("Activity", packageEntity.getActivityList());
        Set<String> keySet = map.keySet();
        List<DetailEntity> detailEntities = new ArrayList<>();
        for (String key : keySet) {
            detailEntities.add(new DetailEntity(key, map.get(key)));
        }
        return detailEntities;
    }

    public PackageEntity getPackageEntity() {
        return packageEntity;
    }

    public void setPackageEntity(PackageEntity packageEntity) {
        this.packageEntity = packageEntity;
    }
}
