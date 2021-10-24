package com.nathaniel.utility;

import android.util.LruCache;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.utility
 * @datetime 2021/10/16 - 13:33
 */
public class BitmapLruCache {
    private static final int DEFAULT_MAX_SIZE = 5 * 1024 * 1024;
    private static BitmapLruCache instance;
    private final LruCache<String, Object> lruCache;

    private BitmapLruCache() {
        lruCache = new LruCache<>(DEFAULT_MAX_SIZE);
    }

    public static BitmapLruCache getInstance() {
        if (instance == null) {
            instance = new BitmapLruCache();
        }
        return instance;
    }

    public LruCache<String, Object> getLruCache() {
        return lruCache;
    }
} 