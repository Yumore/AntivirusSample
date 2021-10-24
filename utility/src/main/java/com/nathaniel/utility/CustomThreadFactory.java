package com.nathaniel.utility;

import java.util.concurrent.ThreadFactory;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.utility
 * @datetime 2021/10/24 - 20:04
 */
public class CustomThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, String.format("thread-pool-%d", runnable.hashCode()));
    }
}
