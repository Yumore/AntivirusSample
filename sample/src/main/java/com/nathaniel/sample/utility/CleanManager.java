package com.nathaniel.sample.utility;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.LoggerUtils;
import com.nathaniel.utility.ThreadManager;
import com.nathaniel.utility.entity.PathEntity;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample.utility
 * @datetime 2021/10/27 - 22:02
 */
public class CleanManager {
    private FutureTask<List<PathEntity>> futureTask;

    /**
     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
     */
    public void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
     */
    @SuppressLint("SdCardPath")
    public void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/databases"));
    }

    /**
     * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
     */
    @SuppressLint("SdCardPath")
    public void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * 按名字清除本应用数据库
     *
     * @param context Context
     * @param dbName  dbName
     */
    public void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * 清除/data/data/com.xxx.xxx/files下的内容
     */
    public void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     */
    public void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
     *
     * @param filePath
     */
    public void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * 清除本应用所有的数据
     *
     * @param context
     * @param filepath
     */
    public void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        if (filepath == null) {
            return;
        }
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     *
     * @param directory
     */
    private void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    /**
     * 获取文件的大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param deleteThisPath
     * @param filePath
     * @return
     */
    public void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 如果下面还有文件
                    File[] files = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化单位
     *
     * @param fileSize fileSize
     * @return 文件真实大小
     */
    public String getFormatSize(double fileSize) {
        double kiloByte = fileSize / 1024;
        if (kiloByte < 1) {
            return fileSize + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    public void scannerFiles(String rootPath, ScannerCallback scannerCallback) {
        LoggerUtils.logger("CleanManager-scannerFiles-216", "要扫描的目录：" + rootPath);
        long startTime = System.currentTimeMillis();
        FolderCallable folderCallable = new FolderCallable(rootPath);
        futureTask = new FutureTask<>(folderCallable);
        ThreadManager.getInstance().executor(futureTask);
        List<PathEntity> pathEntities;
        try {
            pathEntities = futureTask.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            pathEntities = new ArrayList<>();
        }
        if (!EmptyUtils.isEmpty(scannerCallback)) {
            scannerCallback.scannerRefresh(pathEntities);
        }
        LoggerUtils.logger("全部扫描结束,共扫描文件夹：" + pathEntities.size() + ", 用时" + (System.currentTimeMillis() - startTime));
    }

    public void traverFileV2(File file) {
        LinkedList<File> linkedList = new LinkedList<>();
        linkedList.add(file);
        while (!linkedList.isEmpty()) {
            File tmpFile = linkedList.poll();
            if (tmpFile == null || (!tmpFile.isFile() && tmpFile.isDirectory())) {
                continue;
            }
            File[] files = tmpFile.listFiles();
            if (EmptyUtils.isEmpty(files)) {
                continue;
            }
            linkedList.addAll(Arrays.asList(files));
        }
    }

    public void traverFileV3(File file) {
        LinkedList<File> linkedList = new LinkedList<>();
        linkedList.push(file);
        while (!linkedList.isEmpty()) {
            File tmpFile = linkedList.pop();
            File[] files = tmpFile.listFiles();
            if (EmptyUtils.isEmpty(files)) {
                continue;
            }
            for (File child : files) {
                linkedList.push(child);
            }
        }
    }

    public List<Map<String, Object>> scannerOldFiles(String rootPath) {
        long startTime = System.currentTimeMillis();
        FolderMapCallback scanFolderToFolderMap = new FolderMapCallback(rootPath);
        FutureTask<List<Map<String, Object>>> futureTask = new FutureTask<>(scanFolderToFolderMap);
        Thread worker = new Thread(futureTask, rootPath);
        worker.start();
        List<Map<String, Object>> mapList;
        try {
            mapList = futureTask.get();
        } catch (Exception e) {
            e.printStackTrace();
            mapList = new ArrayList<>();
        }
        LoggerUtils.logger("全部扫描结束,共扫描文件夹：" + mapList.size() + ", 用时" + (System.currentTimeMillis() - startTime));
        return mapList;
    }

    public void stopScanner() {
        if (EmptyUtils.isEmpty(futureTask)) {
            LoggerUtils.logger("停止扫描");
            return;
        }
        final boolean interrupt = true;
        futureTask.cancel(interrupt);
    }
}