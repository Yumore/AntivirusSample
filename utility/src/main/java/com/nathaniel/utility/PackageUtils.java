package com.nathaniel.utility;

import android.content.pm.Signature;

import java.security.MessageDigest;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.utility
 * @datetime 2021/10/16 - 13:28
 */
public class PackageUtils {
    public static String getSignatureMd5(Signature signature) {
        byte[] byteStr = signature.toByteArray();
        MessageDigest messageDigest;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(byteStr);
            byte[] byteArray = messageDigest.digest();
            for (byte aByteArray : byteArray) {
                if (Integer.toHexString(0xFF & aByteArray).length() == 1) {
                    stringBuilder.append("0").append(Integer.toHexString(0xFF & aByteArray));
                } else {
                    stringBuilder.append(Integer.toHexString(0xFF & aByteArray));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
} 