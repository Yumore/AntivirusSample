package com.nathaniel.utility.helper;

import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.LoggerUtils;
import com.nathaniel.utility.SingletonUtils;
import com.nathaniel.utility.entity.AntivirusEntity;
import com.nathaniel.utility.greendao.DaoManager;

import java.util.List;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.utility.helper
 * @datetime 2021/10/24 - 16:55
 */
public class AntivirusDaoHelper implements BaseDaoHelper<AntivirusEntity> {
    @Override
    public AntivirusEntity queryById(long id) {
        return SingletonUtils.getInstance(DaoManager.class).getDaoSession().getAntivirusEntityDao().load(id);
    }

    @Override
    public List<AntivirusEntity> queryAll() {
        return SingletonUtils.getInstance(DaoManager.class).getDaoSession().getAntivirusEntityDao().loadAll();
    }

    @Override
    public void inertOrUpdate(AntivirusEntity antivirusEntity) {
        SingletonUtils.getInstance(DaoManager.class).getDaoSession().getAntivirusEntityDao().update(antivirusEntity);
    }

    @Override
    public void inertOrUpdate(final List<AntivirusEntity> antivirusEntities) {
        if (EmptyUtils.isEmpty(antivirusEntities)) {
            return;
        }
        LoggerUtils.logger("AntivirusDaoHelper-inertOrUpdate-39-", "开始插入数据:" + antivirusEntities.size());
        SingletonUtils.getInstance(DaoManager.class).getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (AntivirusEntity antivirusEntity : antivirusEntities) {
                    SingletonUtils.getInstance(DaoManager.class).getDaoSession().getAntivirusEntityDao().update(antivirusEntity);

                }
            }
        });
    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public boolean exitEntity(AntivirusEntity antivirusEntity) {
        return false;
    }
}
