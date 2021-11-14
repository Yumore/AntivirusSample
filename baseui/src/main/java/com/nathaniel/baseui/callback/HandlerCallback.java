package com.nathaniel.baseui.callback;

import android.os.Message;

import androidx.annotation.NonNull;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.baseui.callback
 * @datetime 2021/11/14 - 19:55
 */
public interface HandlerCallback {
    /**
     * 处理消息
     *
     * @param message Message
     */
    void handleMessage(@NonNull Message message);
}
