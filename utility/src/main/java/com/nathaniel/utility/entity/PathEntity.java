package com.nathaniel.utility.entity;

import java.io.File;
import java.util.List;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample.module
 * @datetime 2021/7/31 - 05:51
 */
public class PathEntity {
    private String folderName;
    private String folderPath;
    private List<File> folderFiles;
    private long modifyTime;
    private long scannerTime;

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public List<File> getFolderFiles() {
        return folderFiles;
    }

    public void setFolderFiles(List<File> folderFiles) {
        this.folderFiles = folderFiles;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public long getScannerTime() {
        return scannerTime;
    }

    public void setScannerTime(long scannerTime) {
        this.scannerTime = scannerTime;
    }
}