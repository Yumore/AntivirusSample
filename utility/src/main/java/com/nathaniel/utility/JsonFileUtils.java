package com.nathaniel.utility;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.nathaniel.utility.entity.BaseEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.utility
 * @datetime 2021/10/16 - 16:41
 */
public class JsonFileUtils {
    public static String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static <T> BaseEntity<T> getObject(Context context, String fileName, Type objectType) {
        String jsonString = getJson(context, fileName);
        return new Gson().fromJson(jsonString, objectType);
    }
} 