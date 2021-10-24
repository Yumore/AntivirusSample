package com.nathaniel.utility;


import com.nathaniel.utility.entity.PathEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.valuelink.common.utility
 * @datetime 2021/7/24 - 23:16
 */
public class FolderCallable implements Callable<List<PathEntity>> {
    private static final String TAG = FolderCallable.class.getSimpleName();
    private final String folderPath;
    /**
     * 存储当前线程数量
     */
    private Integer jobCount = 0;

    public FolderCallable(String folderPath) {
        this.folderPath = folderPath;
    }

    @Override
    public List<PathEntity> call() {
        jobCount++;
        long startTime = System.currentTimeMillis();
        //准备返回的路径
        List<PathEntity> resultArray = new ArrayList<>();
        //获取路径-校验路径是否有问题
        File file = new File(folderPath);
        if ((!file.isDirectory()) && (!file.isFile())) {
            return resultArray;
        }
        // 该目录下所有文件
        List<File> fileArray = new ArrayList<>();
        // 获取文件夹内容以及判断是否有权限
        File[] childFileArray = file.listFiles();
        if (childFileArray == null) {
            LoggerUtils.logger(TAG, "无权限");
            return resultArray;
        }

        //准备子线程列表
        List<FutureTask<List<PathEntity>>> workList = new ArrayList<>();

        //判断是否有子文件夹
        for (File childFile : childFileArray) {
            if (childFile.isFile()) {
                //文件就添加路径
                fileArray.add(childFile);
            } else {
                if (jobCount >= 10) {
                    try {
                        //等带当前线程处理
                        Thread.currentThread().sleep(jobCount * 100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //文件夹就继续解析
                FolderCallable folderCallable = new FolderCallable(childFile.getAbsolutePath());
                FutureTask<List<PathEntity>> futureTask = new FutureTask<>(folderCallable);
                workList.add(futureTask);
                Thread worker = new Thread(futureTask, childFile.getAbsolutePath());
                worker.start();
            }
        }

        //获取所有线程返回信息
        for (FutureTask<List<PathEntity>> workObj : workList) {
            try {
                List<PathEntity> mapArray = workObj.get();
                if (mapArray != null) {
                    resultArray.addAll(mapArray);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (fileArray.size() != 0) {
            PathEntity pathEntity = new PathEntity();
            pathEntity.setFolderName(file.getName());
            pathEntity.setFolderPath(folderPath);
            Collections.sort(fileArray);
            pathEntity.setFolderFiles(fileArray);
            pathEntity.setModifyTime(file.lastModified());
            pathEntity.setScannerTime(System.currentTimeMillis() - startTime);
            resultArray.add(pathEntity);
        }
        //返回扫描结果
        LoggerUtils.logger(TAG, (jobCount--) + ":扫描 " + folderPath + " 结束,用时" + (System.currentTimeMillis() - startTime));
        return resultArray;
    }
}