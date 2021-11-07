package com.nathaniel.utility.provider;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author Nathaniel
 * @date 19-3-1 - 下午4:49
 */
public interface IMasterProvider extends IProvider {
    /**
     * 标记是否展示过图片引导
     *
     * @param tractionEnable true|false
     */
    void setTractionEnable(boolean tractionEnable);

    /**
     * 标记是否展示过视频引导
     *
     * @param introduceEnable true|false
     */
    void setIntroduceEnable(boolean introduceEnable);
}
