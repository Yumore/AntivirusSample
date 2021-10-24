package com.nathaniel.utility;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.valuelink.common.utility
 * @datetime 2021/7/24 - 23:16
 */
public class FolderMapCallback implements Callable<List<Map<String, Object>>> {
    private static final String TAG = FolderMapCallback.class.getSimpleName();
    private final String folderPath;
    /**
     * 存储当前线程数量
     */
    private Integer jobCount = 0;

    /**
     * 初始化一个线程
     *
     * @param folderPath folderPath
     */
    public FolderMapCallback(String folderPath) {
        this.folderPath = folderPath;
    }


    /**
     * 线程执行内容
     */

    @Override
    public List<Map<String, Object>> call() {
        jobCount++;
        long startTime = System.currentTimeMillis();
        //准备返回的路径
        List<Map<String, Object>> resultArray = new ArrayList<>();
        //获取路径-校验路径是否有问题
        File file = new File(folderPath);
        if ((!file.isDirectory()) && (!file.isFile())) {
            return resultArray;
        }
        // 该目录下所有文件
        List<String> fileArray = new ArrayList<>();
        // 获取文件夹内容以及判断是否有权限
        File[] childFileArray = file.listFiles();
        if (childFileArray == null) {
            LoggerUtils.logger(TAG, "无权限");
            return null;
        }

        //准备子线程列表
        List<FutureTask<List<Map<String, Object>>>> workList = new ArrayList<>();

        //判断是否有子文件夹
        for (File childFile : childFileArray) {
            if (childFile.isFile()) {
                //文件就添加路径
                fileArray.add(childFile.getAbsolutePath());
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
                FolderMapCallback scanFolderToFolderMap = new FolderMapCallback(childFile.getAbsolutePath());
                FutureTask<List<Map<String, Object>>> futureTask = new FutureTask<>(scanFolderToFolderMap);
                workList.add(futureTask);
                Thread worker = new Thread(futureTask, childFile.getAbsolutePath());
                worker.start();
            }
        }

        //获取所有线程返回信息
        for (FutureTask<List<Map<String, Object>>> workObj : workList) {
            try {
                List<Map<String, Object>> mapArray = workObj.get();
                if (mapArray != null) {
                    resultArray.addAll(mapArray);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (fileArray.size() != 0) {
            Map<String, Object> fileMap = new HashMap<>();
            fileMap.put("folderName", file.getName());
            fileMap.put("folderPath", folderPath);

            Collections.sort(fileArray);
            fileMap.put("folderFiles", fileArray);
            fileMap.put("date", file.lastModified());
            resultArray.add(fileMap);
        }

        //返回扫描结果
        LoggerUtils.logger(TAG, "扫描任务" + (jobCount--) + ": 扫描 " + folderPath + " 结束, 用时" + (System.currentTimeMillis() - startTime));
        return resultArray;
    }

}