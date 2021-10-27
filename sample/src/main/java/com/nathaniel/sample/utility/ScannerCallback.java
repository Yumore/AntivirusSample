package com.nathaniel.sample.utility;

import com.nathaniel.utility.entity.PathEntity;

import java.util.List;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.utility
 * @datetime 2021/7/31 - 13:04
 */
public interface ScannerCallback {
    /**
     * 刷新扫描
     *
     * @param pathEntities 数据源
     */
    void scannerRefresh(List<PathEntity> pathEntities);
}
