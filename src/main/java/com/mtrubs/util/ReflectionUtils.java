package com.mtrubs.util;

import java.lang.reflect.Field;

/**
 * User: Matthew
 * Date: 8/4/13
 * Time: 5:15 PM
 */
public class ReflectionUtils {

    private ReflectionUtils() {
    }

    public static void setProperty(Object object, String fieldName, Object value) {
        if (object != null) {
            setProperty(object, object.getClass(), fieldName, value);
        }
    }

    public static <T> T getProperty(Object object, String fieldName, Class<T> type) {
        if (object == null) {
            throw new NullPointerException("Object is null");
        }
        try {
            Field field = getField(object.getClass(), fieldName);
            field.setAccessible(true);
            return type.cast(field.get(object));
        } catch (IllegalAccessException e) {
            // TODO: Better handle?
            throw new RuntimeException(e);
        }
    }

    private static <T> void setProperty(T object, Class<?> theClass, String fieldName, Object value) {
        try {
            Field field = getField(theClass, fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException e) {
            // TODO: Better handle?
            throw new RuntimeException(e);
        }
    }

    private static Field getField(Class<?> theClass, String fieldName) {
        if (fieldName == null) {
            throw new NullPointerException("Field name is null");
        }
        try {
            return theClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class superClass = theClass.getSuperclass();
            if (superClass == null) {
                // TODO: Better handle?
                throw new RuntimeException(e);
            } else {
                return getField(superClass, fieldName);
            }
        }
    }
}
