package com.nathaniel.utility.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.nathaniel.utility.entity.FileEntity;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * DAO for table "FILE_ENTITY".
 */
public class FileEntityDao extends AbstractDao<FileEntity, Long> {

    public static final String TABLENAME = "FILE_ENTITY";

    public FileEntityDao(DaoConfig config) {
        super(config);
    }


    public FileEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"FILE_ENTITY\" (" + //
            "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: id
            "\"URL\" TEXT," + // 1: url
            "\"FILE_NAME\" TEXT," + // 2: fileName
            "\"LENGTH\" INTEGER NOT NULL ," + // 3: length
            "\"FINISH\" INTEGER NOT NULL );"); // 4: finish
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FILE_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, FileEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());

        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(2, url);
        }

        String fileName = entity.getFileName();
        if (fileName != null) {
            stmt.bindString(3, fileName);
        }
        stmt.bindLong(4, entity.getLength());
        stmt.bindLong(5, entity.getFinish());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, FileEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());

        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(2, url);
        }

        String fileName = entity.getFileName();
        if (fileName != null) {
            stmt.bindString(3, fileName);
        }
        stmt.bindLong(4, entity.getLength());
        stmt.bindLong(5, entity.getFinish());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }

    @Override
    public void readEntity(Cursor cursor, FileEntity entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setUrl(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setFileName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLength(cursor.getLong(offset + 3));
        entity.setFinish(cursor.getLong(offset + 4));
     }

    @Override
    public FileEntity readEntity(Cursor cursor, int offset) {
        FileEntity entity = new FileEntity( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // url
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // fileName
            cursor.getLong(offset + 3), // length
            cursor.getLong(offset + 4) // finish
        );
        return entity;
    }

    @Override
    protected final Long updateKeyAfterInsert(FileEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    @Override
    public Long getKey(FileEntity entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /**
     * Properties of entity FileEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property Url = new Property(1, String.class, "url", false, "URL");
        public final static Property FileName = new Property(2, String.class, "fileName", false, "FILE_NAME");
        public final static Property Length = new Property(3, long.class, "length", false, "LENGTH");
        public final static Property Finish = new Property(4, long.class, "finish", false, "FINISH");
    }

    @Override
    public boolean hasKey(FileEntity entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
