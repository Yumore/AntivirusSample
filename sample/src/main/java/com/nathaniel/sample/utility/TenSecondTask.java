package com.nathaniel.sample.utility;

import java.util.concurrent.Callable;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample.utility
 * @datetime 2021/10/24 - 21:42
 */
public class TenSecondTask<T> implements Callable<T> {
    private final StringBuffer buffer;
    int index;
    int priority;

    public TenSecondTask(int index, int priority, StringBuffer buffer) {
        this.index = index;
        this.priority = priority;
        this.buffer = buffer;
    }

    @Override
    public T call() throws Exception {
        Thread.sleep(10);
        buffer.append(String.format("%02d@%02d", this.priority, index)).append(", ");
        return null;
    }
}
