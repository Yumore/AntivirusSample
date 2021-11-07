package com.nathaniel.utility.helper

import android.content.Context

/**
 * InitializeHelper ：初始化帮助类
 *
 * @author Nathaniel
 * @version 1.0.0
 * @date 2017/8/5 - 11:26
 */
interface InitializeHelper {
    /**
     * 因为SharedPreferences是存储少量的数据，
     * 所以尽量把部分数据拆分成多个文件存储。
     * 而每个文件根据文件名区分，
     * 所以需要传文件名进来。
     * 如果不传文件名，则用默认的
     *
     * @param context 上下文对象
     */
    fun initialize(context: Context)
}