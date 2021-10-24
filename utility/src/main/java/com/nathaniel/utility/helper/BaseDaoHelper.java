package com.nathaniel.utility.helper;

import java.util.List;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.utility.helper
 * @datetime 2021/10/17 - 11:12
 */
abstract class BaseDaoHelper<T> {
    protected static final String TAG = PackageDaoHelper.class.getSimpleName();

    protected abstract T queryById(long id);

    protected abstract List<T> queryAll();

    protected abstract long inertOrUpdate(T t);

    protected abstract void deleteById(long id);

    protected abstract boolean exitEntity(T t);
}
