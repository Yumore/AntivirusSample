package com.nathaniel.utility;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nathaniel.utility.entity.AntivirusEntity;
import com.nathaniel.utility.entity.BaseEntity;
import com.nathaniel.utility.entity.PermissionEntity;
import com.nathaniel.utility.entity.SpecimenEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

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

    private static Gson getGson() {
        return new Gson();
    }

    public static List<AntivirusEntity> getAntivirusList(Context context) {
        BaseEntity<AntivirusEntity> baseEntity = getGson().fromJson(getJson(context, "antivirus.json"), new TypeToken<BaseEntity<AntivirusEntity>>() {
        }.getType());
        return baseEntity.getDataList();
    }

    public static List<SpecimenEntity> getSpecimensList(Context context) {
        BaseEntity<SpecimenEntity> baseEntity = getGson().fromJson(getJson(context, "specimens.json"), new TypeToken<BaseEntity<SpecimenEntity>>() {
        }.getType());
        return baseEntity.getDataList();
    }

    public static List<PermissionEntity> getPermissionList(Context context) {
        BaseEntity<PermissionEntity> baseEntity = getGson().fromJson(getJson(context, "permissions.json"), new TypeToken<BaseEntity<PermissionEntity>>() {
        }.getType());
        return baseEntity.getDataList();
    }

    public static <T> BaseEntity<T> getObject(Context context, String fileName, Type objectType) {
        String jsonString = getJson(context, fileName);
        return new Gson().fromJson(jsonString, objectType);
    }
} 