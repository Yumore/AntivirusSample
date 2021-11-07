package com.nathaniel.utility.provider;

import android.app.Application;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author nathaniel
 * @version V1.0.0
 * @package com.yumore.provider
 * @datetime 12/15/20 - 11:08 AM
 */
public interface IExampleProvider extends IProvider {
    void initPlugins(Application application);
}
