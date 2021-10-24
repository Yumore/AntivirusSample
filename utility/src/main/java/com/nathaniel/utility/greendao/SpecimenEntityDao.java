package com.nathaniel.utility.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.nathaniel.utility.entity.SpecimenEntity;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * DAO for table "SPECIMEN_ENTITY".
 */
public class SpecimenEntityDao extends AbstractDao<SpecimenEntity, Long> {

    public static final String TABLENAME = "SPECIMEN_ENTITY";

    public SpecimenEntityDao(DaoConfig config) {
        super(config);
    }


    public SpecimenEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"SPECIMEN_ENTITY\" (" + //
            "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: id
            "\"VIRUS_NAME\" TEXT," + // 1: virusName
            "\"VIRUS_NUMBER\" INTEGER NOT NULL ," + // 2: virusNumber
            "\"VIRUS_LEVEL\" TEXT," + // 3: virusLevel
            "\"VIRUS_DESCRIBE\" TEXT);"); // 4: virusDescribe
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SPECIMEN_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SpecimenEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());

        String virusName = entity.getVirusName();
        if (virusName != null) {
            stmt.bindString(2, virusName);
        }
        stmt.bindLong(3, entity.getVirusNumber());

        String virusLevel = entity.getVirusLevel();
        if (virusLevel != null) {
            stmt.bindString(4, virusLevel);
        }

        String virusDescribe = entity.getVirusDescribe();
        if (virusDescribe != null) {
            stmt.bindString(5, virusDescribe);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SpecimenEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());

        String virusName = entity.getVirusName();
        if (virusName != null) {
            stmt.bindString(2, virusName);
        }
        stmt.bindLong(3, entity.getVirusNumber());

        String virusLevel = entity.getVirusLevel();
        if (virusLevel != null) {
            stmt.bindString(4, virusLevel);
        }

        String virusDescribe = entity.getVirusDescribe();
        if (virusDescribe != null) {
            stmt.bindString(5, virusDescribe);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }

    @Override
    public void readEntity(Cursor cursor, SpecimenEntity entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setVirusName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setVirusNumber(cursor.getLong(offset + 2));
        entity.setVirusLevel(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setVirusDescribe(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }

    @Override
    public SpecimenEntity readEntity(Cursor cursor, int offset) {
        SpecimenEntity entity = new SpecimenEntity( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // virusName
            cursor.getLong(offset + 2), // virusNumber
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // virusLevel
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // virusDescribe
        );
        return entity;
    }

    @Override
    protected final Long updateKeyAfterInsert(SpecimenEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    @Override
    public Long getKey(SpecimenEntity entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /**
     * Properties of entity SpecimenEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property VirusName = new Property(1, String.class, "virusName", false, "VIRUS_NAME");
        public final static Property VirusNumber = new Property(2, long.class, "virusNumber", false, "VIRUS_NUMBER");
        public final static Property VirusLevel = new Property(3, String.class, "virusLevel", false, "VIRUS_LEVEL");
        public final static Property VirusDescribe = new Property(4, String.class, "virusDescribe", false, "VIRUS_DESCRIBE");
    }

    @Override
    public boolean hasKey(SpecimenEntity entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
