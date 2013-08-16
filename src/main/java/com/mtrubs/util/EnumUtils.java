package com.mtrubs.util;

/**
 * User: Matthew
 * Date: 8/5/13
 * Time: 8:19 PM
 */
public class EnumUtils {

    private EnumUtils() {
    }

    public static <E extends Enum<E>> E valueOf(Class<E> type, String name) {
        return valueOf(type, name, null);
    }

    public static <E extends Enum<E>> E valueOf(Class<E> type, String name, E defaultValue) {
        E result = defaultValue;
        if (name != null && type != null) {
            try {
                result = Enum.valueOf(type, name);
            } catch (IllegalArgumentException ex) {
                // nothing
            }
        }
        return result;
    }
}
