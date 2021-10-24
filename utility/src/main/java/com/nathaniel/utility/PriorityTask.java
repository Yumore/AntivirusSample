package com.nathaniel.utility;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.utility
 * @datetime 2021/10/24 - 19:41
 */
public abstract class PriorityTask implements Runnable, Comparable<PriorityTask> {
    private final static AtomicInteger ATOMIC_INTEGER = new AtomicInteger();
    private final int sequence;
    private int priority;

    public PriorityTask(int priority) {
        sequence = ATOMIC_INTEGER.getAndIncrement();
        this.priority = priority;
    }

    public long getSequence() {
        return sequence;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(PriorityTask policyTask) {
        if (policyTask.priority == priority) {
            return policyTask.sequence - this.sequence;
        }
        return policyTask.priority - this.priority;
    }
}
