package com.nathaniel.sample.utility;

import android.annotation.SuppressLint;

import java.util.Arrays;
import java.util.List;

/**
 * @author admin
 */
public class DataUtils {
    @SuppressLint("DefaultLocale")
    public static String getRealDataSize(long dataSize) {
        if (dataSize > 1024 * 1024 * 1024) {
            dataSize = dataSize / (1024 * 1024 * 1024);
            return String.format("%d GB", dataSize);
        } else if (dataSize > 1024 * 1024) {
            dataSize = dataSize / (1024 * 1024);
            return String.format("%d MB", dataSize);
        } else if (dataSize > 1024) {
            dataSize = dataSize / 1024;
            return String.format("%d KB", dataSize);
        }
        return String.format("%d B", dataSize);
    }

    public static List<String> getDownloadUrls() {
        String[] downloadUrls = new String[]{
            "http://vfx.mtime.cn/Video/2019/03/18/mp4/190318231014076505.mp4",
            "http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4",
            "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
            "http://vjs.zencdn.net/v/oceans.mp4",
            "https://media.w3.org/2010/05/sintel/trailer.mp4",
            "http://mirror.aarnet.edu.au/pub/TED-talks/911Mothers_2010W-480p.mp4",
            "http://vfx.mtime.cn/Video/2019/03/19/mp4/190319212559089721.mp4",
            "http://vfx.mtime.cn/Video/2019/03/19/mp4/190319125415785691.mp4",
            "http://vfx.mtime.cn/Video/2019/03/18/mp4/190318231014076505.mp4",
            "http://down.shyz04.com/YYGJ/Apk/10021664_10155_ggTouTiao_637588490158343959.apk"
        };
        return Arrays.asList(downloadUrls);
    }
}
