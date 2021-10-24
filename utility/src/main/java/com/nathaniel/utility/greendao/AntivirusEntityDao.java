package com.nathaniel.utility.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.nathaniel.utility.entity.AntivirusEntity;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * DAO for table "ANTIVIRUS_ENTITY".
 */
public class AntivirusEntityDao extends AbstractDao<AntivirusEntity, Long> {

    public static final String TABLENAME = "ANTIVIRUS_ENTITY";

    /**
     * Creates the underlying database table.
     */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"ANTIVIRUS_ENTITY\" (" + //
            "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: id
            "\"PACKAGE_NAME\" TEXT," + // 1: packageName
            "\"SIGNATURE\" TEXT," + // 2: signature
            "\"VIRUS_LEVEL\" TEXT," + // 3: virusLevel
            "\"VERSION_NAME\" TEXT," + // 4: versionName
            "\"VERSION_CODE\" TEXT," + // 5: versionCode
            "\"APP_NAME\" TEXT);"); // 6: appName
    }


    public AntivirusEntityDao(DaoConfig config) {
        super(config);
    }

    public AntivirusEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ANTIVIRUS_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AntivirusEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());

        String packageName = entity.getPackageName();
        if (packageName != null) {
            stmt.bindString(2, packageName);
        }

        String signature = entity.getSignature();
        if (signature != null) {
            stmt.bindString(3, signature);
        }

        String virusLevel = entity.getVirusLevel();
        if (virusLevel != null) {
            stmt.bindString(4, virusLevel);
        }

        String versionName = entity.getVersionName();
        if (versionName != null) {
            stmt.bindString(5, versionName);
        }

        String versionCode = entity.getVersionCode();
        if (versionCode != null) {
            stmt.bindString(6, versionCode);
        }

        String appName = entity.getAppName();
        if (appName != null) {
            stmt.bindString(7, appName);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AntivirusEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());

        String packageName = entity.getPackageName();
        if (packageName != null) {
            stmt.bindString(2, packageName);
        }

        String signature = entity.getSignature();
        if (signature != null) {
            stmt.bindString(3, signature);
        }

        String virusLevel = entity.getVirusLevel();
        if (virusLevel != null) {
            stmt.bindString(4, virusLevel);
        }

        String versionName = entity.getVersionName();
        if (versionName != null) {
            stmt.bindString(5, versionName);
        }

        String versionCode = entity.getVersionCode();
        if (versionCode != null) {
            stmt.bindString(6, versionCode);
        }

        String appName = entity.getAppName();
        if (appName != null) {
            stmt.bindString(7, appName);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }

    @Override
    public void readEntity(Cursor cursor, AntivirusEntity entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setPackageName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSignature(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setVirusLevel(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setVersionName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setVersionCode(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setAppName(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
    }

    @Override
    public AntivirusEntity readEntity(Cursor cursor, int offset) {
        AntivirusEntity entity = new AntivirusEntity( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // packageName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // signature
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // virusLevel
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // versionName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // versionCode
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // appName
        );
        return entity;
    }
     
    @Override
    public Long getKey(AntivirusEntity entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final Long updateKeyAfterInsert(AntivirusEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /**
     * Properties of entity AntivirusEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property PackageName = new Property(1, String.class, "packageName", false, "PACKAGE_NAME");
        public final static Property Signature = new Property(2, String.class, "signature", false, "SIGNATURE");
        public final static Property VirusLevel = new Property(3, String.class, "virusLevel", false, "VIRUS_LEVEL");
        public final static Property VersionName = new Property(4, String.class, "versionName", false, "VERSION_NAME");
        public final static Property VersionCode = new Property(5, String.class, "versionCode", false, "VERSION_CODE");
        public final static Property AppName = new Property(6, String.class, "appName", false, "APP_NAME");
    }

    @Override
    public boolean hasKey(AntivirusEntity entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
