package com.example.fishingstuffshopbackend.utils;

import com.example.fishingstuffshopbackend.exception.BadParameterException;

import java.util.function.Consumer;

public class CheckParameterUtils {
    public static  <T> T requireNonNull(String argName, T value) {
        if (value == null)
            throw new BadParameterException(String.format("%s should be not null", argName));
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

    public static  Long requireGreaterThanZero(String argName, Long value) {
        if (value.longValue() <= 0 )
            throw new BadParameterException(argName, value);
        return value;
    }

    public static <T> void setIfNotNull(Consumer<T> fieldSetter, T newValue) {
        if (newValue != null)
            fieldSetter.accept(newValue);
        else {
            throw new BadParameterException("NewValue can't be null");
        }
    }

    public static void setIfNotNullOrBlank(Consumer<String> fieldSetter, String newValue) {
        if (newValue != null && !newValue.isBlank())
            fieldSetter.accept(newValue);
        else {
            throw new BadParameterException("NewValue can't be null or blank");
        }
    }
}
