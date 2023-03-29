package com.example.fishingstuffshopbackend;

import java.lang.reflect.Field;

public class TestUtils {
    public static void setId(Object entity, Long id) {
        try {
            Field fieldId = entity.getClass().getSuperclass().getDeclaredField("id");
            fieldId.setAccessible(true);
            fieldId.set(entity, id);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
