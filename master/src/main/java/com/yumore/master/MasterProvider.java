package com.yumore.master;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.nathaniel.utility.PreferencesUtils;
import com.nathaniel.utility.RouterConstants;
import com.nathaniel.utility.provider.IMasterProvider;

/**
 * @author Nathaniel
 * @date 19-3-1 - 下午5:23
 */
@Route(path = RouterConstants.SAMPLE_PROVIDER)
public class MasterProvider implements IMasterProvider {
    private Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public void setTractionEnable(boolean tractionEnable) {
        PreferencesUtils.getInstance(context).setTractionEnable(tractionEnable);
    }

    @Override
    public void setIntroduceEnable(boolean introduceEnable) {
        PreferencesUtils.getInstance(context).setIntroduceEnable(introduceEnable);
    }
}
