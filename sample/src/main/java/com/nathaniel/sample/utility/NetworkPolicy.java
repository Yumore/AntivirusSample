package com.nathaniel.sample.utility;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample.utility
 * @datetime 2021/5/7 - 20:07
 */
public class NetworkPolicy {

    // NetworkPolicyManager.RULE_REJECT_METERED = 1 << 2
    private static final int RULE_REJECT_METERED = 1 << 2;

    private Object mPolicyMgr;

    public NetworkPolicy(Context context) {
        try {
            mPolicyMgr = Class.forName("android.net.NetworkPolicyManager")
                .getDeclaredMethod("from", Context.class)
                .invoke(null, context);
        } catch (ClassNotFoundException | NoSuchMethodException |
            InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void disableMobileTraffic(int uid) {
        try {
            mPolicyMgr.getClass().getDeclaredMethod("setUidPolicy", int.class, int.class)
                .invoke(mPolicyMgr, uid, RULE_REJECT_METERED);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void enableMobileTraffic(int uid) {
        try {
            mPolicyMgr.getClass().getDeclaredMethod("removeUidPolicy", int.class, int.class)
                .invoke(mPolicyMgr, uid, RULE_REJECT_METERED);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public boolean isMobileTrafficDisabled(int uid) {
        try {
            Object policy = mPolicyMgr.getClass().getDeclaredMethod("getUidPolicy", int.class)
                .invoke(mPolicyMgr, uid);
            if (((int) policy) == RULE_REJECT_METERED) {
                return true;
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }
}

