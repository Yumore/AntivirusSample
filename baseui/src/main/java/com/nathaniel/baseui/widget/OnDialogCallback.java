package com.nathaniel.baseui.widget;

import android.view.View;

/**
 * @author nathaniel
 * @version V1.0.0
 * @package com.valuelink.common.callback
 * @datetime 1/21/21 - 10:16 AM
 */
interface OnDialogCallback {
    /**
     * dialog
     *
     * @param view         view
     * @param customDialog CustomDialog
     */
    void onDialog(View view, CustomDialog customDialog);
}