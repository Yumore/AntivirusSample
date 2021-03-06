package com.nathaniel.sample.utility;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample.utility
 * @datetime 2021/10/30 - 14:09
 */
public class Utils {
    private static final String TAG = "Utils";
    private final Context mContext;
    private final PackageInstaller.SessionCallback mSessionCallback;
    private int mSessionId = -1;


    public Utils(Context context) {
        mContext = context;
        mSessionCallback = new InstallSessionCallback();
        context.getPackageManager().getPackageInstaller().registerSessionCallback(mSessionCallback);
    }

    public void installApp(String apkFilePath) {
        Log.d(TAG, "installApp()------->" + apkFilePath);
        File apkFile = new File(apkFilePath);
        if (!apkFile.exists()) {
            Log.d(TAG, "文件不存在");
        }

        PackageInfo packageInfo = mContext.getPackageManager().getPackageArchiveInfo(apkFilePath, PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
        if (packageInfo != null) {
            String packageName = packageInfo.packageName;
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;
            Log.d(TAG, "packageName=" + packageName + ", versionCode=" + versionCode + ", versionName=" + versionName);
        }

        PackageInstaller packageInstaller = mContext.getPackageManager().getPackageInstaller();
        PackageInstaller.SessionParams sessionParams
            = new PackageInstaller.SessionParams(PackageInstaller
            .SessionParams.MODE_FULL_INSTALL);
        Log.d(TAG, "apkFile length" + apkFile.length());
        sessionParams.setSize(apkFile.length());

        try {
            mSessionId = packageInstaller.createSession(sessionParams);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "sessionId---->" + mSessionId);
        if (mSessionId != -1) {
            boolean copySuccess = onTransfesApkFile(apkFilePath);
            Log.d(TAG, "copySuccess---->" + copySuccess);
            if (copySuccess) {
                execInstallAPP();
            }
        }
    }
// 安装app

    /**
     * 通过文件流传输apk
     *
     * @param apkFilePath
     * @return
     */
    private boolean onTransfesApkFile(String apkFilePath) {
        Log.d(TAG, "---------->onTransfesApkFile()<---------------------");
        InputStream in = null;
        OutputStream out = null;
        PackageInstaller.Session session = null;
        boolean success = false;
        try {
            File apkFile = new File(apkFilePath);
            session = mContext.getPackageManager().getPackageInstaller().openSession(mSessionId);
            out = session.openWrite("base.apk", 0, apkFile.length());
            in = new FileInputStream(apkFile);
            int total = 0, c;
            byte[] buffer = new byte[1024 * 1024];
            while ((c = in.read(buffer)) != -1) {
                total += c;
                out.write(buffer, 0, c);
            }
            session.fsync(out);
            Log.d(TAG, "streamed " + total + " bytes");
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != session) {
                session.close();
            }
            try {
                if (null != out) {
                    out.close();
                }
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    /**
     * 执行安装并通知安装结果
     */
    private void execInstallAPP() {
        Log.d(TAG, "--------------------->execInstallAPP()<------------------");
        PackageInstaller.Session session = null;
        try {
            session = mContext.getPackageManager().getPackageInstaller().openSession(mSessionId);
            Intent intent = new Intent(mContext, InstallResultReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext,
                1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
            session.commit(pendingIntent.getIntentSender());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != session) {
                session.close();
            }
        }
    }

    public void uninstall(String packageName) {

        Intent broadcastIntent = new Intent(mContext, InstallResultReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 1,
            broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PackageInstaller packageInstaller = mContext.getPackageManager().getPackageInstaller();
        packageInstaller.uninstall(packageName, pendingIntent.getIntentSender());
    }

// 卸载app

    public void saveCapSceen(Bitmap bitmap) {
        File file = new File("/sdcard/HaiEr/screentshot");
        if (!file.exists()) {
            file.mkdirs();
        }
        File file1 = new File(file, System.currentTimeMillis() + ".jpg");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file1));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class InstallSessionCallback extends PackageInstaller.SessionCallback {
        @Override
        public void onCreated(int sessionId) {
            // empty
            Log.d(TAG, "onCreated()" + sessionId);
        }

        @Override
        public void onBadgingChanged(int sessionId) {
            // empty
            Log.d(TAG, "onBadgingChanged()" + sessionId + "active");
        }

        @Override
        public void onActiveChanged(int sessionId, boolean active) {
            // empty
            Log.d(TAG, "onActiveChanged()" + sessionId + "active" + active);
        }

        @Override
        public void onProgressChanged(int sessionId, float progress) {
            Log.d(TAG, "onProgressChanged()" + sessionId);
            if (sessionId == mSessionId) {
                int progres = (int) (Integer.MAX_VALUE * progress);
                Log.d(TAG, "onProgressChanged" + progres);
            }
        }

        @Override
        public void onFinished(int sessionId, boolean success) {
            // empty, finish is handled by InstallResultReceiver
            Log.d(TAG, "onFinished()" + sessionId + "success" + success);
            if (mSessionId == sessionId) {
                if (success) {
                    Log.d(TAG, "onFinished() 安装成功");
                } else {
                    Log.d(TAG, "onFinished() 安装失败");
                }

            }
        }
    }


}