package com.nathaniel.utility.helper;

import java.util.List;

/**
 * 因为要对外暴露可调用的方法
 * 所以用接口，而不用抽象方法
 *
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.utility.helper
 * @datetime 2021/10/17 - 11:12
 */
interface BaseDaoHelper<T> {
    String TAG = PackageDaoHelper.class.getSimpleName();

    T queryById(final long id);

    List<T> queryAll();

    void inertOrUpdate(final T t);

    void inertOrUpdate(final List<T> tList);

    void deleteById(final long id);

    boolean exitEntity(final T t);
}
