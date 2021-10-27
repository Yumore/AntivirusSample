package com.nathaniel.utility.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.nathaniel.utility.StringListConverter;
import com.nathaniel.utility.entity.PackageEntity;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.List;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PACKAGE_ENTITY".
*/
public class PackageEntityDao extends AbstractDao<PackageEntity, Long> {

    public static final String TABLENAME = "PACKAGE_ENTITY";

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PACKAGE_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: id
                "\"PACKAGE_NAME\" TEXT," + // 1: packageName
                "\"VERSION_NAME\" TEXT," + // 2: versionName
                "\"VERSION_CODE\" INTEGER NOT NULL ," + // 3: versionCode
                "\"PERMISSION_LIST\" TEXT," + // 4: permissionList
                "\"PROCESS_LIST\" TEXT," + // 5: processList
                "\"RECEIVER_LIST\" TEXT," + // 6: receiverList
                "\"PROVIDER_LIST\" TEXT," + // 7: providerList
                "\"SERVICE_LIST\" TEXT," + // 8: serviceList
                "\"ACTIVITY_LIST\" TEXT," + // 9: activityList
                "\"APP_NAME\" TEXT," + // 10: appName
                "\"SIGNATURE\" TEXT," + // 11: signature
                "\"WIFI_RX\" INTEGER NOT NULL ," + // 12: wifiRx
                "\"WIFI_TX\" INTEGER NOT NULL ," + // 13: wifiTx
                "\"WIFI_TOTAL\" INTEGER NOT NULL ," + // 14: wifiTotal
            "\"MOBILE_RX\" INTEGER NOT NULL ," + // 15: mobileRx
            "\"MOBILE_TX\" INTEGER NOT NULL ," + // 16: mobileTx
            "\"MOBILE_TOTAL\" INTEGER NOT NULL ," + // 17: mobileTotal
            "\"UID\" INTEGER NOT NULL ," + // 18: uid
            "\"OVERLAY\" INTEGER NOT NULL ," + // 19: overlay
            "\"BITMAPS\" BLOB," + // 20: bitmaps
            "\"VIRUS_LEVEL\" TEXT," + // 21: virusLevel
            "\"VEST_BAGGED\" INTEGER NOT NULL ," + // 22: vestBagged
            "\"VIRUS_NAME\" TEXT," + // 23: virusName
            "\"VIRUS_NUMBER\" INTEGER NOT NULL ," + // 24: virusNumber
            "\"VIRUS_DESCRIBE\" TEXT," + // 25: virusDescribe
            "\"CACHE_SIZE\" INTEGER NOT NULL ," + // 26: cacheSize
            "\"DATA_SIZE\" INTEGER NOT NULL ," + // 27: dataSize
            "\"CODE_SIZE\" INTEGER NOT NULL ," + // 28: codeSize
            "\"SIZE_VALUBE\" INTEGER NOT NULL );"); // 29: sizeValube
    }

    private final StringListConverter permissionListConverter = new StringListConverter();
    private final StringListConverter processListConverter = new StringListConverter();
    private final StringListConverter receiverListConverter = new StringListConverter();
    private final StringListConverter providerListConverter = new StringListConverter();
    private final StringListConverter serviceListConverter = new StringListConverter();
    private final StringListConverter activityListConverter = new StringListConverter();

    public PackageEntityDao(DaoConfig config) {
        super(config);
    }

    public PackageEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PackageEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());

        String packageName = entity.getPackageName();
        if (packageName != null) {
            stmt.bindString(2, packageName);
        }

        String versionName = entity.getVersionName();
        if (versionName != null) {
            stmt.bindString(3, versionName);
        }
        stmt.bindLong(4, entity.getVersionCode());

        List permissionList = entity.getPermissionList();
        if (permissionList != null) {
            stmt.bindString(5, permissionListConverter.convertToDatabaseValue(permissionList));
        }

        List processList = entity.getProcessList();
        if (processList != null) {
            stmt.bindString(6, processListConverter.convertToDatabaseValue(processList));
        }

        List receiverList = entity.getReceiverList();
        if (receiverList != null) {
            stmt.bindString(7, receiverListConverter.convertToDatabaseValue(receiverList));
        }

        List providerList = entity.getProviderList();
        if (providerList != null) {
            stmt.bindString(8, providerListConverter.convertToDatabaseValue(providerList));
        }

        List serviceList = entity.getServiceList();
        if (serviceList != null) {
            stmt.bindString(9, serviceListConverter.convertToDatabaseValue(serviceList));
        }

        List activityList = entity.getActivityList();
        if (activityList != null) {
            stmt.bindString(10, activityListConverter.convertToDatabaseValue(activityList));
        }

        String appName = entity.getAppName();
        if (appName != null) {
            stmt.bindString(11, appName);
        }

        String signature = entity.getSignature();
        if (signature != null) {
            stmt.bindString(12, signature);
        }
        stmt.bindLong(13, entity.getWifiRx());
        stmt.bindLong(14, entity.getWifiTx());
        stmt.bindLong(15, entity.getWifiTotal());
        stmt.bindLong(16, entity.getMobileRx());
        stmt.bindLong(17, entity.getMobileTx());
        stmt.bindLong(18, entity.getMobileTotal());
        stmt.bindLong(19, entity.getUid());
        stmt.bindLong(20, entity.getOverlay() ? 1L: 0L);

        byte[] bitmaps = entity.getBitmaps();
        if (bitmaps != null) {
            stmt.bindBlob(21, bitmaps);
        }

        String virusLevel = entity.getVirusLevel();
        if (virusLevel != null) {
            stmt.bindString(22, virusLevel);
        }
        stmt.bindLong(23, entity.getVestBagged() ? 1L: 0L);

        String virusName = entity.getVirusName();
        if (virusName != null) {
            stmt.bindString(24, virusName);
        }
        stmt.bindLong(25, entity.getVirusNumber());

        String virusDescribe = entity.getVirusDescribe();
        if (virusDescribe != null) {
            stmt.bindString(26, virusDescribe);
        }
        stmt.bindLong(27, entity.getCacheSize());
        stmt.bindLong(28, entity.getDataSize());
        stmt.bindLong(29, entity.getCodeSize());
        stmt.bindLong(30, entity.getSizeValube() ? 1L : 0L);
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PACKAGE_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PackageEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());

        String packageName = entity.getPackageName();
        if (packageName != null) {
            stmt.bindString(2, packageName);
        }

        String versionName = entity.getVersionName();
        if (versionName != null) {
            stmt.bindString(3, versionName);
        }
        stmt.bindLong(4, entity.getVersionCode());

        List permissionList = entity.getPermissionList();
        if (permissionList != null) {
            stmt.bindString(5, permissionListConverter.convertToDatabaseValue(permissionList));
        }

        List processList = entity.getProcessList();
        if (processList != null) {
            stmt.bindString(6, processListConverter.convertToDatabaseValue(processList));
        }

        List receiverList = entity.getReceiverList();
        if (receiverList != null) {
            stmt.bindString(7, receiverListConverter.convertToDatabaseValue(receiverList));
        }

        List providerList = entity.getProviderList();
        if (providerList != null) {
            stmt.bindString(8, providerListConverter.convertToDatabaseValue(providerList));
        }

        List serviceList = entity.getServiceList();
        if (serviceList != null) {
            stmt.bindString(9, serviceListConverter.convertToDatabaseValue(serviceList));
        }

        List activityList = entity.getActivityList();
        if (activityList != null) {
            stmt.bindString(10, activityListConverter.convertToDatabaseValue(activityList));
        }

        String appName = entity.getAppName();
        if (appName != null) {
            stmt.bindString(11, appName);
        }

        String signature = entity.getSignature();
        if (signature != null) {
            stmt.bindString(12, signature);
        }
        stmt.bindLong(13, entity.getWifiRx());
        stmt.bindLong(14, entity.getWifiTx());
        stmt.bindLong(15, entity.getWifiTotal());
        stmt.bindLong(16, entity.getMobileRx());
        stmt.bindLong(17, entity.getMobileTx());
        stmt.bindLong(18, entity.getMobileTotal());
        stmt.bindLong(19, entity.getUid());
        stmt.bindLong(20, entity.getOverlay() ? 1L: 0L);

        byte[] bitmaps = entity.getBitmaps();
        if (bitmaps != null) {
            stmt.bindBlob(21, bitmaps);
        }

        String virusLevel = entity.getVirusLevel();
        if (virusLevel != null) {
            stmt.bindString(22, virusLevel);
        }
        stmt.bindLong(23, entity.getVestBagged() ? 1L : 0L);

        String virusName = entity.getVirusName();
        if (virusName != null) {
            stmt.bindString(24, virusName);
        }
        stmt.bindLong(25, entity.getVirusNumber());

        String virusDescribe = entity.getVirusDescribe();
        if (virusDescribe != null) {
            stmt.bindString(26, virusDescribe);
        }
        stmt.bindLong(27, entity.getCacheSize());
        stmt.bindLong(28, entity.getDataSize());
        stmt.bindLong(29, entity.getCodeSize());
        stmt.bindLong(30, entity.getSizeValube() ? 1L: 0L);
    }

    @Override
    public PackageEntity readEntity(Cursor cursor, int offset) {
        PackageEntity entity = new PackageEntity( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // packageName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // versionName
            cursor.getLong(offset + 3), // versionCode
            cursor.isNull(offset + 4) ? null : permissionListConverter.convertToEntityProperty(cursor.getString(offset + 4)), // permissionList
            cursor.isNull(offset + 5) ? null : processListConverter.convertToEntityProperty(cursor.getString(offset + 5)), // processList
            cursor.isNull(offset + 6) ? null : receiverListConverter.convertToEntityProperty(cursor.getString(offset + 6)), // receiverList
            cursor.isNull(offset + 7) ? null : providerListConverter.convertToEntityProperty(cursor.getString(offset + 7)), // providerList
            cursor.isNull(offset + 8) ? null : serviceListConverter.convertToEntityProperty(cursor.getString(offset + 8)), // serviceList
            cursor.isNull(offset + 9) ? null : activityListConverter.convertToEntityProperty(cursor.getString(offset + 9)), // activityList
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // appName
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // signature
            cursor.getLong(offset + 12), // wifiRx
            cursor.getLong(offset + 13), // wifiTx
            cursor.getLong(offset + 14), // wifiTotal
            cursor.getLong(offset + 15), // mobileRx
            cursor.getLong(offset + 16), // mobileTx
            cursor.getLong(offset + 17), // mobileTotal
            cursor.getInt(offset + 18), // uid
            cursor.getShort(offset + 19) != 0, // overlay
            cursor.isNull(offset + 20) ? null : cursor.getBlob(offset + 20), // bitmaps
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // virusLevel
            cursor.getShort(offset + 22) != 0, // vestBagged
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // virusName
            cursor.getLong(offset + 24), // virusNumber
            cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25), // virusDescribe
            cursor.getLong(offset + 26), // cacheSize
            cursor.getLong(offset + 27), // dataSize
            cursor.getLong(offset + 28), // codeSize
            cursor.getShort(offset + 29) != 0 // sizeValube
        );
        return entity;
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }

    @Override
    public void readEntity(Cursor cursor, PackageEntity entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setPackageName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setVersionName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setVersionCode(cursor.getLong(offset + 3));
        entity.setPermissionList(cursor.isNull(offset + 4) ? null : permissionListConverter.convertToEntityProperty(cursor.getString(offset + 4)));
        entity.setProcessList(cursor.isNull(offset + 5) ? null : processListConverter.convertToEntityProperty(cursor.getString(offset + 5)));
        entity.setReceiverList(cursor.isNull(offset + 6) ? null : receiverListConverter.convertToEntityProperty(cursor.getString(offset + 6)));
        entity.setProviderList(cursor.isNull(offset + 7) ? null : providerListConverter.convertToEntityProperty(cursor.getString(offset + 7)));
        entity.setServiceList(cursor.isNull(offset + 8) ? null : serviceListConverter.convertToEntityProperty(cursor.getString(offset + 8)));
        entity.setActivityList(cursor.isNull(offset + 9) ? null : activityListConverter.convertToEntityProperty(cursor.getString(offset + 9)));
        entity.setAppName(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setSignature(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setWifiRx(cursor.getLong(offset + 12));
        entity.setWifiTx(cursor.getLong(offset + 13));
        entity.setWifiTotal(cursor.getLong(offset + 14));
        entity.setMobileRx(cursor.getLong(offset + 15));
        entity.setMobileTx(cursor.getLong(offset + 16));
        entity.setMobileTotal(cursor.getLong(offset + 17));
        entity.setUid(cursor.getInt(offset + 18));
        entity.setOverlay(cursor.getShort(offset + 19) != 0);
        entity.setBitmaps(cursor.isNull(offset + 20) ? null : cursor.getBlob(offset + 20));
        entity.setVirusLevel(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setVestBagged(cursor.getShort(offset + 22) != 0);
        entity.setVirusName(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setVirusNumber(cursor.getLong(offset + 24));
        entity.setVirusDescribe(cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25));
        entity.setCacheSize(cursor.getLong(offset + 26));
        entity.setDataSize(cursor.getLong(offset + 27));
        entity.setCodeSize(cursor.getLong(offset + 28));
        entity.setSizeValube(cursor.getShort(offset + 29) != 0);
    }

    /**
     * Properties of entity PackageEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property PackageName = new Property(1, String.class, "packageName", false, "PACKAGE_NAME");
        public final static Property VersionName = new Property(2, String.class, "versionName", false, "VERSION_NAME");
        public final static Property VersionCode = new Property(3, long.class, "versionCode", false, "VERSION_CODE");
        public final static Property PermissionList = new Property(4, String.class, "permissionList", false, "PERMISSION_LIST");
        public final static Property ProcessList = new Property(5, String.class, "processList", false, "PROCESS_LIST");
        public final static Property ReceiverList = new Property(6, String.class, "receiverList", false, "RECEIVER_LIST");
        public final static Property ProviderList = new Property(7, String.class, "providerList", false, "PROVIDER_LIST");
        public final static Property ServiceList = new Property(8, String.class, "serviceList", false, "SERVICE_LIST");
        public final static Property ActivityList = new Property(9, String.class, "activityList", false, "ACTIVITY_LIST");
        public final static Property AppName = new Property(10, String.class, "appName", false, "APP_NAME");
        public final static Property Signature = new Property(11, String.class, "signature", false, "SIGNATURE");
        public final static Property WifiRx = new Property(12, long.class, "wifiRx", false, "WIFI_RX");
        public final static Property WifiTx = new Property(13, long.class, "wifiTx", false, "WIFI_TX");
        public final static Property WifiTotal = new Property(14, long.class, "wifiTotal", false, "WIFI_TOTAL");
        public final static Property MobileRx = new Property(15, long.class, "mobileRx", false, "MOBILE_RX");
        public final static Property MobileTx = new Property(16, long.class, "mobileTx", false, "MOBILE_TX");
        public final static Property MobileTotal = new Property(17, long.class, "mobileTotal", false, "MOBILE_TOTAL");
        public final static Property Uid = new Property(18, int.class, "uid", false, "UID");
        public final static Property Overlay = new Property(19, boolean.class, "overlay", false, "OVERLAY");
        public final static Property Bitmaps = new Property(20, byte[].class, "bitmaps", false, "BITMAPS");
        public final static Property VirusLevel = new Property(21, String.class, "virusLevel", false, "VIRUS_LEVEL");
        public final static Property VestBagged = new Property(22, boolean.class, "vestBagged", false, "VEST_BAGGED");
        public final static Property VirusName = new Property(23, String.class, "virusName", false, "VIRUS_NAME");
        public final static Property VirusNumber = new Property(24, long.class, "virusNumber", false, "VIRUS_NUMBER");
        public final static Property VirusDescribe = new Property(25, String.class, "virusDescribe", false, "VIRUS_DESCRIBE");
        public final static Property CacheSize = new Property(26, long.class, "cacheSize", false, "CACHE_SIZE");
        public final static Property DataSize = new Property(27, long.class, "dataSize", false, "DATA_SIZE");
        public final static Property CodeSize = new Property(28, long.class, "codeSize", false, "CODE_SIZE");
        public final static Property SizeValube = new Property(29, boolean.class, "sizeValube", false, "SIZE_VALUBE");
    }

    @Override
    protected final Long updateKeyAfterInsert(PackageEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    @Override
    public Long getKey(PackageEntity entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(PackageEntity entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
