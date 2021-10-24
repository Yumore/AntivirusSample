package com.nathaniel.utility.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.nathaniel.utility.LoggerUtils;
import com.nathaniel.utility.greendao.DaoMaster;
import com.nathaniel.utility.greendao.SummaryEntityDao;

import org.greenrobot.greendao.database.Database;


/**
 * @author nathaniel
 * @date 2016/3/3
 */
public class SQLiteOpenHelper extends DaoMaster.OpenHelper {
    private static final String TAG = SQLiteOpenHelper.class.getSimpleName();

    public SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database database, int oldVersion, int newVersion) {
        LoggerUtils.logger(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            MigrationHelper.migrate(database, new MigrationHelper.ReCreateAllTableListener() {
                @Override
                public void onCreateAllTables(Database database, boolean ifNotExists) {
                    DaoMaster.createAllTables(database, ifNotExists);
                }

                @Override
                public void onDropAllTables(Database database, boolean ifExists) {
                    DaoMaster.dropAllTables(database, ifExists);
                }
            }, SummaryEntityDao.class);
        }
    }
}