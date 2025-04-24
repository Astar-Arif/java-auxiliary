package com.astar.common.library.utils;

import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;

//TODO ADD MORE
public abstract class ClassUtility extends ClassUtils {

    //    TODO RECHECK THIS
    public static Class<?> getFieldType(Class<?> clazz, String field) {
        if (clazz == null || field == null || field.isEmpty()) {
            throw new IllegalArgumentException("Class and field name must not be null or empty.");
        }

        while (clazz != null) {
            try {
                Field declaredField = clazz.getDeclaredField(field);
                return declaredField.getType();
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (SecurityException e) {
                return null;
            }
        }
        return null;
    }
}
