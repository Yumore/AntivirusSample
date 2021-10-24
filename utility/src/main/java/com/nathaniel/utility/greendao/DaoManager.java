package com.nathaniel.utility.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.nathaniel.utility.BuildConfig;
import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.LoggerUtils;
import com.nathaniel.utility.helper.InitializeHelper;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * @author Nathaniel
 * @date 2020/1/4 - 15:11
 */
public class DaoManager implements InitializeHelper {
    private static final String DATABASE_DEFAULT = "Nathaniel.db";
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private DaoMaster.DevOpenHelper devOpenHelper;
    private Context context;

    public DaoMaster getDaoMaster() {
        if (EmptyUtils.isEmpty(daoMaster)) {
            devOpenHelper = new DaoMaster.DevOpenHelper(context, DATABASE_DEFAULT, null);
            daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        }
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        if (EmptyUtils.isEmpty(daoSession)) {
            daoSession = getDaoMaster().newSession();
        }
        return daoSession;
    }

    private void closeHelper() {
        if (devOpenHelper != null) {
            devOpenHelper.close();
            devOpenHelper = null;
        }
    }

    private void closeSession() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
    }

    public void closeConnection() {
        closeHelper();
        closeSession();
    }

    @Override
    public void initialize(@NonNull Context context) {
        LoggerUtils.logger(LoggerUtils.TAG, "DaoManager-initialize-64-", "开始初始化数据");
        if (BuildConfig.DEBUG) {
            QueryBuilder.LOG_SQL = true;
            QueryBuilder.LOG_VALUES = true;
        } else {
            QueryBuilder.LOG_SQL = true;
            QueryBuilder.LOG_VALUES = true;
        }
        this.context = context;
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, DATABASE_DEFAULT, null);
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }
}
