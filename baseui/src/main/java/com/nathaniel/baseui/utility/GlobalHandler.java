package com.nathaniel.baseui.utility;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.nathaniel.baseui.callback.HandlerCallback;
import com.nathaniel.utility.EmptyUtils;
import com.nathaniel.utility.LoggerUtils;


/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.baseui.utility
 * @datetime 2021/11/14 - 19:42
 */
public final class GlobalHandler extends Handler {
    private static GlobalHandler globalHandler;
    private HandlerCallback handlerCallback;

    private GlobalHandler() {
        super(Looper.getMainLooper());
    }

    public static GlobalHandler getInstance() {
        if (EmptyUtils.isEmpty(globalHandler)) {
            globalHandler = new GlobalHandler();
        }
        return globalHandler;
    }

    public void setHandlerCallback(HandlerCallback handlerCallback) {
        this.handlerCallback = handlerCallback;
    }

    public void postDelayedMessage(int messageId, long delayTime) {
        getInstance().postDelayed(() -> {
            getInstance().sendEmptyMessage(messageId);
        }, delayTime);
    }

    public void postDelayedMessage(Message message, long delayTime) {
        getInstance().postDelayed(() -> {
            getInstance().sendMessage(message);
        }, delayTime);
    }

    @Override
    public void handleMessage(@NonNull Message message) {
        super.handleMessage(message);
        LoggerUtils.logger("GlobalHandler-handleMessage-55", message);
        if (EmptyUtils.isEmpty(handlerCallback)) {
            LoggerUtils.logger(LoggerUtils.Level.ERROR, "请传入`HandlerCallback`对象");
        } else {
            handlerCallback.handleMessage(message);
        }
    }
}
