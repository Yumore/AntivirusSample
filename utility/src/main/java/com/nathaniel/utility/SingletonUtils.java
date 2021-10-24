package com.nathaniel.utility;


import androidx.annotation.NonNull;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author nathaniel
 * @version v1.0
 * @datetime 9/8/20-2:55 PM
 */
public class SingletonUtils {
    private static final ConcurrentMap<Class<?>, Object> INSTANCE_MAP = new ConcurrentHashMap<>();
    private static final Object[] EMPTY_ARGS = new Object[0];

    private SingletonUtils() {
        throw new IllegalStateException("You can't initialized me.");
    }

    public static <T> T getSingleton(@NonNull Class<? extends T> clazz) {
        return getInstance(clazz, EMPTY_ARGS);
    }

    public static <T> T getInstance(@NonNull Class<? extends T> clazz, Object... args) {
        Object object = INSTANCE_MAP.get(clazz);
        if (object == null) {
            Class<?>[] parameterTypes = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = args[i].getClass();
            }
            return getInstance(clazz, parameterTypes, args);
        }
        return clazz.cast(object);
    }

    public static synchronized <T> T getInstance(@NonNull Class<? extends T> clazz, Class<?>[] parameterTypes, Object[] args) {
        Object object = INSTANCE_MAP.get(clazz);
        if (object == null) {
            try {
                if (parameterTypes != null && args != null) {
                    if (parameterTypes.length == args.length) {
                        Constructor<?> constructor = clazz.getDeclaredConstructor(parameterTypes);
                        constructor.setAccessible(true);
                        T instance = clazz.cast(constructor.newInstance(args));
                        INSTANCE_MAP.put(clazz, instance);
                        return instance;
                    } else {
                        throw new IllegalArgumentException("参数个数不匹配");
                    }
                } else if (parameterTypes == null && args == null) {
                    T instance = clazz.newInstance();
                    INSTANCE_MAP.put(clazz, instance);
                    return instance;
                } else {
                    throw new IllegalArgumentException("两个参数数组必须同时为null或同时不为null");
                }
            } catch (Exception e) {
                LoggerUtils.logger("创建实例失败", e);
            }
        }
        return null;
    }
}
