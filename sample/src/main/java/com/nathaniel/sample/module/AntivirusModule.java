package com.nathaniel.sample.module;

import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.entity.AntivirusEntity;
import com.nathaniel.utility.entity.PackageEntity;
import com.nathaniel.utility.entity.PermissionEntity;
import com.nathaniel.utility.entity.SpecimenEntity;

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
    private List<AntivirusEntity> antivirusEntities;
    private List<SpecimenEntity> specimenEntities;
    private List<PermissionEntity> permissionEntities;
    private List<PackageEntity> packageEntities;

    public boolean isDangerous(String permission) {
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

    public List<AntivirusEntity> getAntivirusEntities() {
        return antivirusEntities;
    }

    public void setAntivirusEntities(List<AntivirusEntity> antivirusEntities) {
        this.antivirusEntities = antivirusEntities;
    }

    public List<SpecimenEntity> getSpecimenEntities() {
        return specimenEntities;
    }

    public void setSpecimenEntities(List<SpecimenEntity> specimenEntities) {
        this.specimenEntities = specimenEntities;
    }

    public List<PermissionEntity> getPermissionEntities() {
        return permissionEntities;
    }

    public void setPermissionEntities(List<PermissionEntity> permissionEntities) {
        this.permissionEntities = permissionEntities;
    }

    public List<PackageEntity> getPackageEntities() {
        return packageEntities;
    }

    public void setPackageEntities(List<PackageEntity> packageEntities) {
        this.packageEntities = packageEntities;
    }
}
