package com.nathaniel.utility.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.nathaniel.utility.entity.SummaryEntity;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * DAO for table "SUMMARY_ENTITY".
 */
public class SummaryEntityDao extends AbstractDao<SummaryEntity, Long> {

    public static final String TABLENAME = "SUMMARY_ENTITY";

    public SummaryEntityDao(DaoConfig config) {
        super(config);
    }


    public SummaryEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"SUMMARY_ENTITY\" (" + //
            "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: id
            "\"IDX\" INTEGER NOT NULL ," + // 1: idx
            "\"IFACE\" TEXT," + // 2: iface
            "\"ACCT_TAG_HEX\" TEXT," + // 3: acct_tag_hex
            "\"UID_TAG_INT\" INTEGER NOT NULL ," + // 4: uid_tag_int
            "\"CNT_SET\" INTEGER NOT NULL ," + // 5: cnt_set
            "\"RX_BYTES\" INTEGER NOT NULL ," + // 6: rx_bytes
            "\"RX_PACKETS\" INTEGER NOT NULL ," + // 7: rx_packets
            "\"TX_BYTES\" INTEGER NOT NULL ," + // 8: tx_bytes
            "\"TX_PACKETS\" INTEGER NOT NULL ," + // 9: tx_packets
            "\"RX_TCP_BYTES\" INTEGER NOT NULL ," + // 10: rx_tcp_bytes
            "\"RX_TCP_PACKETS\" INTEGER NOT NULL ," + // 11: rx_tcp_packets
            "\"RX_UDP_BYTES\" INTEGER NOT NULL ," + // 12: rx_udp_bytes
            "\"RX_UDP_PACKETS\" INTEGER NOT NULL ," + // 13: rx_udp_packets
            "\"RX_OTHER_BYTES\" INTEGER NOT NULL ," + // 14: rx_other_bytes
            "\"RX_OTHER_PACKETS\" INTEGER NOT NULL ," + // 15: rx_other_packets
            "\"TX_TCP_BYTES\" INTEGER NOT NULL ," + // 16: tx_tcp_bytes
            "\"TX_TCP_PACKETS\" INTEGER NOT NULL ," + // 17: tx_tcp_packets
            "\"TX_UDP_BYTES\" INTEGER NOT NULL ," + // 18: tx_udp_bytes
            "\"TX_UDP_PACKETS\" INTEGER NOT NULL ," + // 19: tx_udp_packets
            "\"TX_OTHER_BYTES\" INTEGER NOT NULL ," + // 20: tx_other_bytes
            "\"TX_OTHER_PACKETS\" INTEGER NOT NULL );"); // 21: tx_other_packets
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SUMMARY_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SummaryEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getIdx());

        String iface = entity.getIface();
        if (iface != null) {
            stmt.bindString(3, iface);
        }

        String acct_tag_hex = entity.getAcct_tag_hex();
        if (acct_tag_hex != null) {
            stmt.bindString(4, acct_tag_hex);
        }
        stmt.bindLong(5, entity.getUid_tag_int());
        stmt.bindLong(6, entity.getCnt_set());
        stmt.bindLong(7, entity.getRx_bytes());
        stmt.bindLong(8, entity.getRx_packets());
        stmt.bindLong(9, entity.getTx_bytes());
        stmt.bindLong(10, entity.getTx_packets());
        stmt.bindLong(11, entity.getRx_tcp_bytes());
        stmt.bindLong(12, entity.getRx_tcp_packets());
        stmt.bindLong(13, entity.getRx_udp_bytes());
        stmt.bindLong(14, entity.getRx_udp_packets());
        stmt.bindLong(15, entity.getRx_other_bytes());
        stmt.bindLong(16, entity.getRx_other_packets());
        stmt.bindLong(17, entity.getTx_tcp_bytes());
        stmt.bindLong(18, entity.getTx_tcp_packets());
        stmt.bindLong(19, entity.getTx_udp_bytes());
        stmt.bindLong(20, entity.getTx_udp_packets());
        stmt.bindLong(21, entity.getTx_other_bytes());
        stmt.bindLong(22, entity.getTx_other_packets());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SummaryEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getIdx());

        String iface = entity.getIface();
        if (iface != null) {
            stmt.bindString(3, iface);
        }

        String acct_tag_hex = entity.getAcct_tag_hex();
        if (acct_tag_hex != null) {
            stmt.bindString(4, acct_tag_hex);
        }
        stmt.bindLong(5, entity.getUid_tag_int());
        stmt.bindLong(6, entity.getCnt_set());
        stmt.bindLong(7, entity.getRx_bytes());
        stmt.bindLong(8, entity.getRx_packets());
        stmt.bindLong(9, entity.getTx_bytes());
        stmt.bindLong(10, entity.getTx_packets());
        stmt.bindLong(11, entity.getRx_tcp_bytes());
        stmt.bindLong(12, entity.getRx_tcp_packets());
        stmt.bindLong(13, entity.getRx_udp_bytes());
        stmt.bindLong(14, entity.getRx_udp_packets());
        stmt.bindLong(15, entity.getRx_other_bytes());
        stmt.bindLong(16, entity.getRx_other_packets());
        stmt.bindLong(17, entity.getTx_tcp_bytes());
        stmt.bindLong(18, entity.getTx_tcp_packets());
        stmt.bindLong(19, entity.getTx_udp_bytes());
        stmt.bindLong(20, entity.getTx_udp_packets());
        stmt.bindLong(21, entity.getTx_other_bytes());
        stmt.bindLong(22, entity.getTx_other_packets());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }

    @Override
    public void readEntity(Cursor cursor, SummaryEntity entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setIdx(cursor.getInt(offset + 1));
        entity.setIface(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAcct_tag_hex(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUid_tag_int(cursor.getInt(offset + 4));
        entity.setCnt_set(cursor.getInt(offset + 5));
        entity.setRx_bytes(cursor.getLong(offset + 6));
        entity.setRx_packets(cursor.getInt(offset + 7));
        entity.setTx_bytes(cursor.getLong(offset + 8));
        entity.setTx_packets(cursor.getInt(offset + 9));
        entity.setRx_tcp_bytes(cursor.getLong(offset + 10));
        entity.setRx_tcp_packets(cursor.getInt(offset + 11));
        entity.setRx_udp_bytes(cursor.getLong(offset + 12));
        entity.setRx_udp_packets(cursor.getInt(offset + 13));
        entity.setRx_other_bytes(cursor.getLong(offset + 14));
        entity.setRx_other_packets(cursor.getInt(offset + 15));
        entity.setTx_tcp_bytes(cursor.getLong(offset + 16));
        entity.setTx_tcp_packets(cursor.getInt(offset + 17));
        entity.setTx_udp_bytes(cursor.getLong(offset + 18));
        entity.setTx_udp_packets(cursor.getInt(offset + 19));
        entity.setTx_other_bytes(cursor.getLong(offset + 20));
        entity.setTx_other_packets(cursor.getInt(offset + 21));
     }

    @Override
    public SummaryEntity readEntity(Cursor cursor, int offset) {
        SummaryEntity entity = new SummaryEntity( //
            cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // idx
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // iface
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // acct_tag_hex
            cursor.getInt(offset + 4), // uid_tag_int
            cursor.getInt(offset + 5), // cnt_set
            cursor.getLong(offset + 6), // rx_bytes
            cursor.getInt(offset + 7), // rx_packets
            cursor.getLong(offset + 8), // tx_bytes
            cursor.getInt(offset + 9), // tx_packets
            cursor.getLong(offset + 10), // rx_tcp_bytes
            cursor.getInt(offset + 11), // rx_tcp_packets
            cursor.getLong(offset + 12), // rx_udp_bytes
            cursor.getInt(offset + 13), // rx_udp_packets
            cursor.getLong(offset + 14), // rx_other_bytes
            cursor.getInt(offset + 15), // rx_other_packets
            cursor.getLong(offset + 16), // tx_tcp_bytes
            cursor.getInt(offset + 17), // tx_tcp_packets
            cursor.getLong(offset + 18), // tx_udp_bytes
            cursor.getInt(offset + 19), // tx_udp_packets
            cursor.getLong(offset + 20), // tx_other_bytes
            cursor.getInt(offset + 21) // tx_other_packets
        );
        return entity;
    }

    @Override
    protected final Long updateKeyAfterInsert(SummaryEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    @Override
    public Long getKey(SummaryEntity entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /**
     * Properties of entity SummaryEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property Idx = new Property(1, int.class, "idx", false, "IDX");
        public final static Property Iface = new Property(2, String.class, "iface", false, "IFACE");
        public final static Property Acct_tag_hex = new Property(3, String.class, "acct_tag_hex", false, "ACCT_TAG_HEX");
        public final static Property Uid_tag_int = new Property(4, int.class, "uid_tag_int", false, "UID_TAG_INT");
        public final static Property Cnt_set = new Property(5, int.class, "cnt_set", false, "CNT_SET");
        public final static Property Rx_bytes = new Property(6, long.class, "rx_bytes", false, "RX_BYTES");
        public final static Property Rx_packets = new Property(7, int.class, "rx_packets", false, "RX_PACKETS");
        public final static Property Tx_bytes = new Property(8, long.class, "tx_bytes", false, "TX_BYTES");
        public final static Property Tx_packets = new Property(9, int.class, "tx_packets", false, "TX_PACKETS");
        public final static Property Rx_tcp_bytes = new Property(10, long.class, "rx_tcp_bytes", false, "RX_TCP_BYTES");
        public final static Property Rx_tcp_packets = new Property(11, int.class, "rx_tcp_packets", false, "RX_TCP_PACKETS");
        public final static Property Rx_udp_bytes = new Property(12, long.class, "rx_udp_bytes", false, "RX_UDP_BYTES");
        public final static Property Rx_udp_packets = new Property(13, int.class, "rx_udp_packets", false, "RX_UDP_PACKETS");
        public final static Property Rx_other_bytes = new Property(14, long.class, "rx_other_bytes", false, "RX_OTHER_BYTES");
        public final static Property Rx_other_packets = new Property(15, int.class, "rx_other_packets", false, "RX_OTHER_PACKETS");
        public final static Property Tx_tcp_bytes = new Property(16, long.class, "tx_tcp_bytes", false, "TX_TCP_BYTES");
        public final static Property Tx_tcp_packets = new Property(17, int.class, "tx_tcp_packets", false, "TX_TCP_PACKETS");
        public final static Property Tx_udp_bytes = new Property(18, long.class, "tx_udp_bytes", false, "TX_UDP_BYTES");
        public final static Property Tx_udp_packets = new Property(19, int.class, "tx_udp_packets", false, "TX_UDP_PACKETS");
        public final static Property Tx_other_bytes = new Property(20, long.class, "tx_other_bytes", false, "TX_OTHER_BYTES");
        public final static Property Tx_other_packets = new Property(21, int.class, "tx_other_packets", false, "TX_OTHER_PACKETS");
    }

    @Override
    public boolean hasKey(SummaryEntity entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
