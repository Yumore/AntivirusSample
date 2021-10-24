package com.wxy.appstartfaster.util;


import com.wxy.appstartfaster.model.TaskSortModel;
import com.wxy.appstartfaster.task.BaseStarterTask;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;

public class AppStartTaskSortUtil {
    /**
     * 拓扑排序
     * taskIntegerHashMap每个Task的入度（key= Class < ? extends AppStartTask>）
     * taskHashMap每个Task            （key= Class < ? extends AppStartTask>）
     * taskChildHashMap每个Task的孩子  （key= Class < ? extends AppStartTask>）
     * deque 入度为0的Task
     */
    public static List<BaseStarterTask> getSortResult(List<BaseStarterTask> startTaskList, HashMap<Class<? extends BaseStarterTask>, BaseStarterTask> taskHashMap, HashMap<Class<? extends BaseStarterTask>, List<Class<? extends BaseStarterTask>>> taskChildHashMap) {
        List<BaseStarterTask> sortTaskList = new ArrayList<>();
        HashMap<Class<? extends BaseStarterTask>, TaskSortModel> taskIntegerHashMap = new HashMap<>();
        Deque<Class<? extends BaseStarterTask>> deque = new ArrayDeque<>();
        for (BaseStarterTask task : startTaskList) {
            if (!taskIntegerHashMap.containsKey(task.getClass())) {
                taskHashMap.put(task.getClass(), task);
                taskIntegerHashMap.put(task.getClass(), new TaskSortModel(task.getDependsTaskList() == null ? 0 : task.getDependsTaskList().size()));
                taskChildHashMap.put(task.getClass(), new ArrayList<Class<? extends BaseStarterTask>>());
                //入度为0的队列
                if (taskIntegerHashMap.get(task.getClass()).getIn() == 0) {
                    deque.offer(task.getClass());
                }
            } else {
                throw new RuntimeException("任务重复了: " + task.getClass());
            }
        }
        //把孩子都加进去
        for (BaseStarterTask task : startTaskList) {
            if (task.getDependsTaskList() != null) {
                for (Class<? extends BaseStarterTask> aclass : task.getDependsTaskList()) {
                    taskChildHashMap.get(aclass).add(task.getClass());
                }
            }
        }
        //循环去除入度0的，再把孩子入度变成0的加进去
        while (!deque.isEmpty()) {
            Class<? extends BaseStarterTask> aclass = deque.poll();
            sortTaskList.add(taskHashMap.get(aclass));
            for (Class<? extends BaseStarterTask> classChild : taskChildHashMap.get(aclass)) {
                taskIntegerHashMap.get(classChild).setIn(taskIntegerHashMap.get(classChild).getIn() - 1);
                if (taskIntegerHashMap.get(classChild).getIn() == 0) {
                    deque.offer(classChild);
                }
            }
        }
        if (sortTaskList.size() != startTaskList.size()) {
            throw new RuntimeException("出现环了");
        }
        return sortTaskList;
    }
}
