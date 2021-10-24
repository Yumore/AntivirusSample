package com.nathaniel.utility;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.utility
 * @datetime 2021/10/24 - 21:28
 */
class PriorityRunnable<E extends Comparable<? super E>> implements Runnable, Comparable<PriorityRunnable<E>> {
    private final static AtomicInteger ATOMIC_INTEGER = new AtomicInteger();
    private final int sequence;
    private final Runnable runnable;
    private int priority;

    public PriorityRunnable(Runnable runnable, int priority) {
        sequence = ATOMIC_INTEGER.getAndIncrement();
        this.runnable = runnable;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    @Override
    public void run() {
        this.runnable.run();
    }

    @Override
    public int compareTo(PriorityRunnable<E> other) {
        if (this.priority == other.priority) {
            return other.sequence - this.sequence;
        } else {
            return other.priority - this.priority;
        }
    }
}
