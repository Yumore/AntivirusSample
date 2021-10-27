package com.nathaniel.sample.utility;

import java.util.concurrent.FutureTask;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample.utility
 * @datetime 2021/10/27 - 23:02
 */
public class NamedThread<V> implements Runnable {
    private final FutureTask<V> futureTask;
    private final String taskName;

    public NamedThread(FutureTask<V> futureTask, String taskName) {
        this.futureTask = futureTask;
        this.taskName = taskName;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(taskName);
        futureTask.run();
    }
}
