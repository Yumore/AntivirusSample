package com.nathaniel.utility;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.utility
 * @datetime 2021/10/23 - 17:07
 */
@Deprecated
public abstract class Singleton<T> {

    private T instance;

    /**
     * 创建实例
     *
     * @return T
     */
    protected abstract T createInstance();

    public final T getInstance() {
        synchronized (this) {
            if (instance == null) {
                instance = createInstance();
            }
            return instance;
        }
    }
}