package com.nathaniel.utility;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.utility
 * @datetime 2021/10/24 - 19:47
 */
public class PolicyFutureTask extends FutureTask<PolicyTask> implements Comparable<PolicyFutureTask> {
    private final PolicyTask policyTask;

    public PolicyFutureTask(Callable<PolicyTask> callable, PolicyTask policyTask) {
        super(callable);
        this.policyTask = policyTask;
    }

    public PolicyTask getPolicyTask() {
        return policyTask;
    }

    @Override
    public int compareTo(PolicyFutureTask policyFutureTask) {
        return policyTask.compareTo(policyFutureTask.getPolicyTask());
    }
}
