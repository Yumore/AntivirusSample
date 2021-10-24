package com.nathaniel.utility.helper;


import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.SingletonUtils;
import com.nathaniel.utility.entity.PackageEntity;
import com.nathaniel.utility.greendao.DaoManager;

import java.util.List;

/**
 * @author Nathaniel
 * @date 2020/1/4 - 14:42
 */
public class PackageDaoHelper implements BaseDaoHelper<PackageEntity> {

    @Override
    public PackageEntity queryById(long id) {
        return SingletonUtils.getInstance(DaoManager.class).getDaoSession().getPackageEntityDao().load(id);
    }

    /**
     * 查询所有记录
     */
    @Override
    public List<PackageEntity> queryAll() {
        return SingletonUtils.getInstance(DaoManager.class).getDaoSession().loadAll(PackageEntity.class);
    }

    @Override
    public void inertOrUpdate(PackageEntity packageEntity) {
        SingletonUtils.getInstance(DaoManager.class).getDaoSession().getPackageEntityDao().insertOrReplace(packageEntity);
    }

    @Override
    public void inertOrUpdate(final List<PackageEntity> packageEntities) {
        if (EmptyUtils.isEmpty(packageEntities)) {
            return;
        }
        SingletonUtils.getInstance(DaoManager.class).getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (PackageEntity applicationEntity : packageEntities) {
                    SingletonUtils.getInstance(DaoManager.class).getDaoSession().getPackageEntityDao().update(applicationEntity);

                }
            }
        });
    }

    @Override
    public void deleteById(long id) {
        SingletonUtils.getInstance(DaoManager.class).getDaoSession().getPackageEntityDao().deleteByKey(id);
    }

    @Override
    public boolean exitEntity(PackageEntity packageEntity) {
        return false;
    }
}
