package com.nathaniel.sample.module;

import android.os.Environment;

import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.LoggerUtils;
import com.nathaniel.utility.entity.PathEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample.module
 * @datetime 2021/7/31 - 17:10
 */
public class ScannerModel {
    public List<PathEntity> getRootPaths() {
        List<PathEntity> pathEntities = new ArrayList<>();
        String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(rootPath);
        if (!file.isFile() && !file.isDirectory()) {
            return pathEntities;
        }
        File[] files = file.listFiles();
        if (EmptyUtils.isEmpty(files)) {
            return pathEntities;
        }
        for (File tmpFile : files) {
            PathEntity pathEntity = new PathEntity();
            pathEntity.setFolderName(tmpFile.getName());
            pathEntity.setFolderPath(tmpFile.getAbsolutePath());
            pathEntity.setModifyTime(tmpFile.lastModified());
            pathEntities.add(pathEntity);
        }
        LoggerUtils.logger(ScannerModel.class.getSimpleName(), pathEntities);
        return pathEntities;
    }
} 