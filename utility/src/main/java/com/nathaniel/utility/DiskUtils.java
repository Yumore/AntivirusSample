package com.nathaniel.utility;

import com.nathaniel.utility.entity.PathEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.valuelink.common.utility
 * @datetime 2021/7/24 - 18:12
 */
public class DiskUtils {
    private static final String TAG = DiskUtils.class.getSimpleName();
    private FutureTask<List<PathEntity>> futureTask;

    public void scannerFiles(String rootPath, ScannerCallback scannerCallback) {
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
        LoggerUtils.logger(TAG, "全部扫描结束,共扫描文件夹：" + pathEntities.size() + ", 用时" + (System.currentTimeMillis() - startTime));
    }

    public void traverFileV1(File file) {
        System.out.println(file);
        File[] files = file.listFiles();
        if (!EmptyUtils.isEmpty(files)) {
            for (File tempFile : files) {
                if (tempFile != null && tempFile.isDirectory()) {
                    traverFileV1(tempFile);
                }
            }
        }
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
        LoggerUtils.logger(TAG, "全部扫描结束,共扫描文件夹：" + mapList.size() + ", 用时" + (System.currentTimeMillis() - startTime));
        return mapList;
    }

    public void stopScanner() {
        if (EmptyUtils.isEmpty(futureTask)) {
            LoggerUtils.logger(TAG, "停止扫描");
            return;
        }
        final boolean interrupt = true;
        futureTask.cancel(interrupt);
    }
} 