package com.nathaniel.utility;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Arrays;
import java.util.List;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.utility
 * @datetime 2021/10/16 -15:39
 */
public class GreenConverterStringList implements PropertyConverter<List<String>, String> {

    private static final String SPLIT_REGEX = "#!&";

    @Override
    public List<String> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        }

        return Arrays.asList(databaseValue.split(SPLIT_REGEX));
    }

    @Override
    public String convertToDatabaseValue(List<String> entityProperty) {
        if (entityProperty == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : entityProperty) {
            stringBuilder.append(string).append(SPLIT_REGEX);
        }
        return stringBuilder.toString();
    }
} 