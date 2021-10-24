package com.nathaniel.utility;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.utility
 * @datetime 2021/10/24 - 19:41
 */
public abstract class PolicyTask implements Runnable, Comparable<PolicyTask> {
    private final int priority;

    public PolicyTask(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(PolicyTask policyTask) {
        return policyTask.priority - priority;
    }
}
