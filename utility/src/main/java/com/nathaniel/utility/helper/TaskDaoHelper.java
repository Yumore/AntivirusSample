package com.nathaniel.utility.helper;

import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.SingletonUtils;
import com.nathaniel.utility.entity.TaskEntity;
import com.nathaniel.utility.greendao.DaoManager;
import com.nathaniel.utility.greendao.TaskEntityDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.utility.daohelper
 * @datetime 2021/6/13 - 20:23
 */
public class TaskDaoHelper extends BaseDaoHelper<TaskEntity> {

    public void insertMultiply(final List<TaskEntity> taskEntities) {
        if (EmptyUtils.isEmpty(taskEntities)) {
            return;
        }
        SingletonUtils.getSingleton(DaoManager.class).getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (TaskEntity taskEntity : taskEntities) {
                    SingletonUtils.getSingleton(DaoManager.class).getDaoSession().getTaskEntityDao().insertOrReplace(taskEntity);
                }
            }
        });
    }

    public List<TaskEntity> queryByUrl(String url) {
        QueryBuilder<TaskEntity> queryBuilder = SingletonUtils.getSingleton(DaoManager.class).getDaoSession().queryBuilder(TaskEntity.class);
        return queryBuilder.where(TaskEntityDao.Properties.Url.eq(url)).list();
    }

    public boolean existEntity(long id) {
        QueryBuilder<TaskEntity> queryBuilder = SingletonUtils.getSingleton(DaoManager.class).getDaoSession().queryBuilder(TaskEntity.class);
        List<TaskEntity> taskEntities = queryBuilder.where(TaskEntityDao.Properties.Id.eq(id)).list();
        return !EmptyUtils.isEmpty(taskEntities);
    }

    @Override
    protected TaskEntity queryById(long id) {
        return null;
    }

    @Override
    public List<TaskEntity> queryAll() {
        return SingletonUtils.getSingleton(DaoManager.class).getDaoSession().loadAll(TaskEntity.class);
    }

    @Override
    protected long inertOrUpdate(TaskEntity taskEntity) {
        return SingletonUtils.getSingleton(DaoManager.class).getDaoSession().getTaskEntityDao().insertOrReplace(taskEntity);
    }

    @Override
    protected void deleteById(long id) {

    }

    @Override
    protected boolean exitEntity(TaskEntity taskEntity) {
        return false;
    }
}
