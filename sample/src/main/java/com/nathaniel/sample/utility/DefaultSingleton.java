package com.nathaniel.sample.utility;

import com.nathaniel.utility.LoggerUtils;
import com.nathaniel.utility.Singleton;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample.utility
 * @datetime 2021/10/24 - 20:16
 */
public class DefaultSingleton {
    private static final Singleton<DefaultSingleton> DEFAULT_SINGLETON = new Singleton<DefaultSingleton>() {
        @Override
        protected DefaultSingleton createInstance() {
            return new DefaultSingleton();
        }
    };

    private DefaultSingleton() {
        LoggerUtils.logger("DefaultSingleton-DefaultSingleton-15-", "初始化了");
    }

    public static DefaultSingleton getInstance() {
        return DEFAULT_SINGLETON.getInstance();
    }

    public void initialized() {
        LoggerUtils.logger("DefaultSingleton-initialized-30-", "被调用了");
    }
}
