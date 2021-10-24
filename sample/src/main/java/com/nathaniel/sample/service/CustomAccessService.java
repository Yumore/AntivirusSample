package com.nathaniel.sample.service;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

import com.nathaniel.utility.LoggerUtils;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample
 * @datetime 5/12/21 - 10:11 AM
 */
public class CustomAccessService extends AccessibilityService {
    /**
     * 辅助服务名称（包名+"/"+完整类名）
     */
    public static final String SERVICE_NAME = "com.yumore.rxmvp.sample/com.nathaniel.sample.service.MAccessibilityService";
    private static final String TAG = CustomAccessService.class.getSimpleName();


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event == null) {
            return;
        }
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if (event.getPackageName() != null && event.getClassName() != null) {
                LoggerUtils.logger(TAG, event.getPackageName() + "/" + event.getClassName());
            }
        }
    }

    @Override
    public void onInterrupt() {
        LoggerUtils.logger(TAG, "onInterrupt()");
    }
}
