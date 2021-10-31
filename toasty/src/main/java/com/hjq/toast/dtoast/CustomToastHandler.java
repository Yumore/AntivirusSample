package com.hjq.toast.dtoast;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.ViewManager;
import android.view.ViewParent;
import android.view.WindowManager;

import androidx.annotation.NonNull;


class CustomToastHandler extends Handler {
    private final static int REMOVE = 2;

    /**
     * 列表中成员要求非空
     */
    private final CustomPriorityQueue<CustomToast> toastQueue;

    private CustomToastHandler() {
        super(Looper.myLooper());
        toastQueue = new CustomPriorityQueue<>((x, y) -> {
            //往队列中add元素时，x为新增，y为原队列中元素
            // skip showing DToast
            if (y.isToastShowing()) {
                return 1;
            }
            if (x.getTimestamp() == y.getTimestamp()) {
                return 0;
            }
            //值小的排队首
            return x.getTimestamp() < y.getTimestamp() ? -1 : 1;
        });
    }

    static CustomToastHandler instance() {
        return SingletonHolder.TOAST_HANDLER;
    }

    /**
     * 新增Toast任务加入队列
     */
    public void add(CustomToast toast) {
        if (toast == null) {
            return;
        }
        CustomToast customToast = toast.clone();
        notifyNewToastComeIn(customToast);
    }

    /**
     * 当前有toast在展示
     */
    private boolean isShowing() {
        return toastQueue.size() > 0;
    }

    private void notifyNewToastComeIn(@NonNull CustomToast mToast) {
        boolean isShowing = isShowing();
        //检查有没有时间戳，没有则一定要打上时间戳
        if (mToast.getTimestamp() <= 0) {
            mToast.setTimestamp(System.currentTimeMillis());
        }
        //然后加入队列
        toastQueue.add(mToast);

        //如果有toast正在展示
        if (isShowing) {
            if (toastQueue.size() == 2) {
                //获取当前正在展示的toast
                CustomToast showing = toastQueue.peek();
                if (showing == null) {
                    return;
                }
                //允许新加入的toast终止当前的展示
                if (mToast.getPriority() >= showing.getPriority()) {
                    //立即终止当前正在展示toast,并开始展示下一个
                    sendRemoveMsg(showing);
                }  //do nothing ...

            }  //do nothing ...

        } else {
            showNextToast();
        }
    }

    private void remove(CustomToast toast) {
        toastQueue.remove(toast);
        removeInternal(toast);
    }

    void cancelAll() {
        removeMessages(REMOVE);
        if (!toastQueue.isEmpty()) {
            removeInternal(toastQueue.peek());
        }
        toastQueue.clear();
    }

    void cancelActivityToast(Activity activity) {
        if (activity == null) {
            return;
        }
        for (CustomToast customToast : toastQueue) {
            if (customToast instanceof ActivityToast && customToast.getContext() == activity) {
                remove(customToast);
            }
        }
    }

    private void removeInternal(CustomToast toast) {
        if (toast != null && toast.isToastShowing()) {
            // 2018/11/26 逻辑存在问题：队列中多个Toast使用相同ContentView时可能造成混乱。
            // 不过，不同时展示多个Toast的话，也不会出现此问题.因为next.show()在last.removeView()动作之后。
            // DToast不会同时展示多个Toast，因此成功避免了此问题
            WindowManager windowManager = toast.getWindowManager();
            if (windowManager != null) {
                try {
                    RomUtils.log("removeInternal: removeView");
                    windowManager.removeViewImmediate(toast.getViewInternal());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            toast.toastShowing = false;
        }
    }

    /**
     * 多个弹窗连续出现时：
     * 1.相同优先级时，会终止上一个，直接展示后一个；
     * 2.不同优先级时，如果后一个的优先级更高则会终止上一个，直接展示后一个。
     */
    private void showNextToast() {
        if (toastQueue.isEmpty()) return;
        CustomToast toast = toastQueue.peek();
        if (null == toast) {
            toastQueue.poll();
            showNextToast();
        } else {
            if (toastQueue.size() > 1) {
                CustomToast next = toastQueue.get(1);
                if (next.getPriority() >= toast.getPriority()) {
                    toastQueue.remove(toast);
                    showNextToast();
                } else {
                    displayToast(toast);
                }
            } else {
                displayToast(toast);
            }
        }
    }

    private void sendRemoveMsgDelay(CustomToast toast) {
        removeMessages(REMOVE);
        Message message = obtainMessage(REMOVE);
        message.obj = toast;
        sendMessageDelayed(message, toast.getDuration());
    }

    private void sendRemoveMsg(CustomToast toast) {
        removeMessages(REMOVE);
        Message message = obtainMessage(REMOVE);
        message.obj = toast;
        sendMessage(message);
    }

    private void displayToast(@NonNull CustomToast toast) {
        WindowManager windowManager = toast.getWindowManager();
        if (windowManager == null) {
            return;
        }
        View toastView = toast.getViewInternal();
        if (toastView == null) {
            //没有ContentView时直接移除
            toastQueue.remove(toast);
            //移除一个未在展示的Toast任务后，主动唤起下一个Toast任务
            showNextToast();
            return;
        }
        //从父容器中移除contentView
        ViewParent parent = toastView.getParent();
        if (parent instanceof ViewManager) {
            ((ViewManager) parent).removeView(toastView);
        }
        //再将contentView添加到WindowManager
        try {
            RomUtils.log("displayToast: addView");
            windowManager.addView(toastView, toast.getWindowManagerParams());

            //确定展示成功后
            toast.toastShowing = true;
            //展示到时间后移除
            sendRemoveMsgDelay(toast);
        } catch (Exception e) {
            if (e instanceof WindowManager.BadTokenException
                && e.getMessage() != null
                && (e.getMessage().contains("token null is not valid") || e.getMessage().contains("is your activity running"))) {
                if (toast instanceof ActivityToast) {
                    //如果ActivityToast也无法展示的话，暂时只能选择放弃治疗了，难受...
                    CustomToast.Count4BadTokenException = 0;
                } else {
                    CustomToast.Count4BadTokenException++;
                    //尝试使用ActivityToast
                    if (toast.getContext() instanceof Activity) {
                        //因为DovaToast未展示成功，需要主动移除,然后再尝试使用ActivityToast
                        toastQueue.remove(toast);//从队列移除
                        removeMessages(REMOVE);//清除已发送的延时消息
                        toast.toastShowing = false;//更新toast状态
                        try {
                            //尝试从窗口移除toastView，虽然windowManager.addView()抛出异常，但toastView仍然可能已经被添加到窗口父容器中(具体看ROM实现)，所以需要主动移除
                            //因为toastView也可能没有被添加到窗口父容器，所以需要增加try-catch
                            windowManager.removeViewImmediate(toastView);
                        } catch (Exception me) {
                            RomUtils.log("windowManager removeViewImmediate error.Do not care this!");
                        }
                        new ActivityToast(toast.getContext())
                            .setTimestamp(toast.getTimestamp())
                            .setView(toastView)
                            .setDuration(toast.getDuration())
                            .setGravity(toast.getGravity(), toast.getXOffset(), toast.getYOffset())
                            .show();
                        return;
                    }
                }
            }
            e.printStackTrace();
        }
    }

    @Override
    public void handleMessage(Message message) {
        if (message == null) {
            return;
        }
        if (message.what == REMOVE) {
            //移除当前
            remove((CustomToast) message.obj);
            // 展示下一个Toast
            showNextToast();
        }
    }

    private static class SingletonHolder {
        private static final CustomToastHandler TOAST_HANDLER = new CustomToastHandler();
    }
}
