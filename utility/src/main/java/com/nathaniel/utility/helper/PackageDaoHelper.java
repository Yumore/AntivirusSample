package com.nathaniel.utility.helper;


import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.LoggerUtils;
import com.nathaniel.utility.entity.PackageEntity;
import com.nathaniel.utility.greendao.DaoManager;

import java.util.List;

/**
 * @author Nathaniel
 * @date 2020/1/4 - 14:42
 */
public class PackageDaoHelper extends BaseDaoHelper<PackageEntity> {

    /**
     * 插入多条数据，在子线程操作
     *
     * @param packageEntities packageEntities
     */
    public void insertMultiply(final List<PackageEntity> packageEntities) {
        if (EmptyUtils.isEmpty(packageEntities)) {
            return;
        }
        LoggerUtils.logger(LoggerUtils.TAG, "PackageDaoHelper-insertMultiply-26-", "开始写入数据");
        DaoManager.getInstance().getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (PackageEntity applicationEntity : packageEntities) {
                    DaoManager.getInstance().getDaoSession().getPackageEntityDao().update(applicationEntity);

                }
            }
        });
    }


    @Override
    public PackageEntity queryById(long id) {
        return DaoManager.getInstance().getDaoSession().getPackageEntityDao().load(id);
    }

    /**
     * 查询所有记录
     */
    @Override
    public List<PackageEntity> queryAll() {
        return DaoManager.getInstance().getDaoSession().loadAll(PackageEntity.class);
    }

    @Override
    public long inertOrUpdate(PackageEntity packageEntity) {
        return DaoManager.getInstance().getDaoSession().getPackageEntityDao().insertOrReplace(packageEntity);
    }

    @Override
    public void deleteById(long id) {
        DaoManager.getInstance().getDaoSession().getPackageEntityDao().deleteByKey(id);
    }

    @Override
    public boolean exitEntity(PackageEntity packageEntity) {
        return false;
    }
}
