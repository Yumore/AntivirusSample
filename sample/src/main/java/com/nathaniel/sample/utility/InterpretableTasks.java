package com.nathaniel.sample.utility;

import com.nathaniel.utility.LoggerUtils;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.utility
 * @datetime 2021/8/15 - 20:35
 */
public class InterpretableTasks implements Runnable {
    private final Object locked = new Object();
    private volatile boolean suspended = false;

    public void suspend() {
        suspended = true;
    }

    public void resume() {
        suspended = false;
        synchronized (locked) {
            locked.notifyAll();
        }
    }


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (!suspended) {
                //Do work here
            } else {
                //Has been suspended
                try {
                    while (suspended) {
                        synchronized (locked) {
                            locked.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        LoggerUtils.logger("Cancelled");
    }
}