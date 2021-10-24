package com.nathaniel.utility.helper;

import com.nathaniel.utility.SingletonUtils;
import com.nathaniel.utility.entity.FileEntity;
import com.nathaniel.utility.greendao.DaoManager;

import java.util.List;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.utility.daohelper
 * @datetime 2021/6/13 - 20:42
 */
public class FileDaoHelper implements BaseDaoHelper<FileEntity> {


    @Override
    public FileEntity queryById(long id) {
        return null;
    }

    @Override
    public List<FileEntity> queryAll() {
        return null;
    }

    @Override
    public void inertOrUpdate(FileEntity fileEntity) {
        SingletonUtils.getSingleton(DaoManager.class).getDaoSession().getFileEntityDao().insertOrReplace(fileEntity);
    }

    @Override
    public void inertOrUpdate(List<FileEntity> fileEntities) {

    }

    @Override
    public void deleteById(long id) {
        SingletonUtils.getSingleton(DaoManager.class).getDaoSession().getFileEntityDao().deleteByKey(id);
    }

    @Override
    public boolean exitEntity(FileEntity fileEntity) {
        return false;
    }
}
