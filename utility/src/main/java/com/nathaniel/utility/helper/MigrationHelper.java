package com.nathaniel.utility.helper;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.nathaniel.utility.LoggerUtils;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.internal.DaoConfig;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * please call {@link #migrate(SQLiteDatabase, Class[])} or {@link #migrate(Database, Class[])}
 *
 * @author nathaniel
 */
public final class MigrationHelper {
    private static final String SQLITE_MASTER = "sqlite_master";
    private static final String SQLITE_TEMP_MASTER = "sqlite_temp_master";
    private static final String TAG = MigrationHelper.class.getSimpleName();
    public static boolean DEBUG = false;
    private static WeakReference<ReCreateAllTableListener> weakListener;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SafeVarargs
    public static void migrate(SQLiteDatabase sqLiteDatabase, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        printLog("【The Old Database Version】" + sqLiteDatabase.getVersion());
        Database database = new StandardDatabase(sqLiteDatabase);
        migrate(database, daoClasses);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SafeVarargs
    public static void migrate(SQLiteDatabase sqLiteDatabase, ReCreateAllTableListener reCreateAllTableListener, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        weakListener = new WeakReference<>(reCreateAllTableListener);
        migrate(sqLiteDatabase, daoClasses);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SafeVarargs
    public static void migrate(Database database, ReCreateAllTableListener reCreateAllTableListener, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        weakListener = new WeakReference<>(reCreateAllTableListener);
        migrate(database, daoClasses);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SafeVarargs
    public static void migrate(Database database, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        printLog("【Generate temp table】start");
        generateTempTables(database, daoClasses);
        printLog("【Generate temp table】complete");

        ReCreateAllTableListener listener = null;
        if (weakListener != null) {
            listener = weakListener.get();
        }

        if (listener != null) {
            listener.onDropAllTables(database, true);
            printLog("【Drop all table by listener】");
            listener.onCreateAllTables(database, false);
            printLog("【Create all table by listener】");
        } else {
            dropAllTables(database, true, daoClasses);
            createAllTables(database, false, daoClasses);
        }
        printLog("【Restore data】start");
        restoreData(database, daoClasses);
        printLog("【Restore data】complete");
    }

    private static void generateTempTables(Database database, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (Class<? extends AbstractDao<?, ?>> daoClass : daoClasses) {
            String tempTableName = null;

            DaoConfig daoConfig = new DaoConfig(database, daoClass);
            String tableName = daoConfig.tablename;
            if (!isTableExists(database, false, tableName)) {
                printLog("【New Table】" + tableName);
                continue;
            }
            try {
                tempTableName = daoConfig.tablename.concat("_TEMP");
                StringBuilder dropTableStringBuilder = new StringBuilder();
                dropTableStringBuilder.append("DROP TABLE IF EXISTS ").append(tempTableName).append(";");
                database.execSQL(dropTableStringBuilder.toString());

                StringBuilder insertTableStringBuilder = new StringBuilder();
                insertTableStringBuilder.append("CREATE TEMPORARY TABLE ").append(tempTableName);
                insertTableStringBuilder.append(" AS SELECT * FROM `").append(tableName).append("`;");
                database.execSQL(insertTableStringBuilder.toString());
                printLog("【Table】" + tableName + "\n ---Columns-->" + getColumnsStr(daoConfig));
                printLog("【Generate temp table】" + tempTableName);
            } catch (SQLException e) {
                Log.e(TAG, "【Failed to generate temp table】" + tempTableName, e);
            }
        }
    }

    private static boolean isTableExists(Database database, boolean isTemp, String tableName) {
        if (database == null || TextUtils.isEmpty(tableName)) {
            return false;
        }
        String dbName = isTemp ? SQLITE_TEMP_MASTER : SQLITE_MASTER;
        String sql = "SELECT COUNT(*) FROM `" + dbName + "` WHERE type = ? AND name = ?";
        Cursor cursor = null;
        int count = 0;
        try {
            cursor = database.rawQuery(sql, new String[]{"table", tableName});
            if (cursor == null || !cursor.moveToFirst()) {
                return false;
            }
            count = cursor.getInt(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return count > 0;
    }

    private static String getColumnsStr(DaoConfig daoConfig) {
        if (daoConfig == null) {
            return "no columns";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < daoConfig.allColumns.length; i++) {
            builder.append(daoConfig.allColumns[i]);
            builder.append(",");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SafeVarargs
    private static void dropAllTables(Database db, boolean ifExists, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        reflectMethod(db, "dropTable", ifExists, daoClasses);
        printLog("【Drop all table by reflect】");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SafeVarargs
    private static void createAllTables(Database db, boolean ifNotExists, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        reflectMethod(db, "createTable", ifNotExists, daoClasses);
        printLog("【Create all table by reflect】");
    }

    /**
     * dao class already define the sql exec method, so just invoke it
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressWarnings("unchecked")
    @SafeVarargs
    private static void reflectMethod(Database db, String methodName, boolean isExists, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        if (daoClasses.length < 1) {
            return;
        }
        try {
            for (Class clazz : daoClasses) {
                Method method = clazz.getDeclaredMethod(methodName, Database.class, boolean.class);
                method.invoke(null, db, isExists);
            }
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @SafeVarargs
    private static void restoreData(Database database, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (Class<? extends AbstractDao<?, ?>> daoClass : daoClasses) {
            DaoConfig daoConfig = new DaoConfig(database, daoClass);
            String tableName = daoConfig.tablename;
            String tempTableName = daoConfig.tablename.concat("_TEMP");

            if (!isTableExists(database, true, tempTableName)) {
                continue;
            }

            try {
                // get all columns from tempTable, take careful to use the columns list
                List<TableInfo> newTableInfos = TableInfo.getTableInfo(database, tableName);
                List<TableInfo> tempTableInfos = TableInfo.getTableInfo(database, tempTableName);
                ArrayList<String> selectColumns = new ArrayList<>(newTableInfos.size());
                ArrayList<String> intoColumns = new ArrayList<>(newTableInfos.size());
                for (TableInfo tableInfo : tempTableInfos) {
                    if (newTableInfos.contains(tableInfo)) {
                        String column = '`' + tableInfo.name + '`';
                        intoColumns.add(column);
                        selectColumns.add(column);
                    }
                }
                // NOT NULL columns list
                for (TableInfo tableInfo : newTableInfos) {
                    if (tableInfo.notnull && !tempTableInfos.contains(tableInfo)) {
                        String column = '`' + tableInfo.name + '`';
                        intoColumns.add(column);

                        String value;
                        if (tableInfo.dfltValue != null) {
                            value = "'" + tableInfo.dfltValue + "' AS ";
                        } else {
                            value = "'' AS ";
                        }
                        selectColumns.add(value + column);
                    }
                }

                if (intoColumns.size() != 0) {
                    StringBuilder insertTableStringBuilder = new StringBuilder();
                    insertTableStringBuilder.append("REPLACE INTO `").append(tableName).append("` (");
                    insertTableStringBuilder.append(TextUtils.join(",", intoColumns));
                    insertTableStringBuilder.append(") SELECT ");
                    insertTableStringBuilder.append(TextUtils.join(",", selectColumns));
                    insertTableStringBuilder.append(" FROM ").append(tempTableName).append(";");
                    database.execSQL(insertTableStringBuilder.toString());
                    printLog("【Restore data】 to " + tableName);
                }
                StringBuilder dropTableStringBuilder = new StringBuilder();
                dropTableStringBuilder.append("DROP TABLE ").append(tempTableName);
                database.execSQL(dropTableStringBuilder.toString());
                printLog("【Drop temp table】" + tempTableName);
            } catch (SQLException e) {
                Log.e(TAG, "【Failed to restore data from temp table 】" + tempTableName, e);
            }
        }
    }

    private static List<String> getColumns(Database db, String tableName) {
        List<String> columns = null;
        try (Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 0", null)) {
            if (null != cursor && cursor.getColumnCount() > 0) {
                columns = Arrays.asList(cursor.getColumnNames());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null == columns) {
                columns = new ArrayList<>();
            }
        }
        return columns;
    }

    private static void printLog(String info) {
        LoggerUtils.logger(TAG, info);
    }

    public interface ReCreateAllTableListener {
        void onCreateAllTables(Database database, boolean ifNotExists);

        void onDropAllTables(Database database, boolean ifExists);
    }

    private static class TableInfo {
        int cid;
        String name;
        String type;
        boolean notnull;
        String dfltValue;
        boolean pk;

        private static List<TableInfo> getTableInfo(Database db, String tableName) {
            String sql = "PRAGMA table_info(`" + tableName + "`)";
            printLog(sql);
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor == null) {
                return new ArrayList<>();
            }

            TableInfo tableInfo;
            List<TableInfo> tableInfos = new ArrayList<>();
            while (cursor.moveToNext()) {
                tableInfo = new TableInfo();
                tableInfo.cid = cursor.getInt(0);
                tableInfo.name = cursor.getString(1);
                tableInfo.type = cursor.getString(2);
                tableInfo.notnull = cursor.getInt(3) == 1;
                tableInfo.dfltValue = cursor.getString(4);
                tableInfo.pk = cursor.getInt(5) == 1;
                tableInfos.add(tableInfo);
                printLog(tableName + "：" + tableInfo);
            }
            cursor.close();
            return tableInfos;
        }

        @Override
        public boolean equals(Object o) {
            return this == o
                || o != null
                && getClass() == o.getClass()
                && name.equals(((TableInfo) o).name);
        }

        @Override
        public String toString() {
            return "TableInfo{" +
                "cid=" + cid +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", notnull=" + notnull +
                ", dfltValue='" + dfltValue + '\'' +
                ", pk=" + pk +
                '}';
        }
    }
}
