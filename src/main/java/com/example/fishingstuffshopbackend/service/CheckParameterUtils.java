package com.example.fishingstuffshopbackend.service;

import com.example.fishingstuffshopbackend.exception.BadParameterException;

public class CheckParameterUtils {
    public static  <T> T requireNonNull(String argName, T value) {
        if (value == null)
            throw new BadParameterException(argName, value);
        return value;
    }

    public static String requireNonNullAndNonBlank(String argName, String str) {
        if (str == null || str.isBlank())
            throw new BadParameterException(argName, str);
        return str;
    }

    public static  Long requireNonNullAndGreaterThanZero(String argName, Long value) {
        if (value == null || value.longValue() <= 0 )
            throw new BadParameterException(argName, value);
        return value;
    }
}
