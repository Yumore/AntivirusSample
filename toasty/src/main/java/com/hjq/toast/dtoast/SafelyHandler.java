package com.hjq.toast.dtoast;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

class SafelyHandler extends Handler {
    private final Handler handler;

    public SafelyHandler(Handler handler) {
        super(Looper.myLooper());
        this.handler = handler;
    }

    @Override
    public void dispatchMessage(Message msg) {
        try {
            handler.dispatchMessage(msg);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void handleMessage(Message msg) {
        //需要委托给原Handler执行
        handler.handleMessage(msg);
    }
}
