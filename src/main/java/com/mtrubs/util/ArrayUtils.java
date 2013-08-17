package com.mtrubs.util;

/**
 * User: Matthew
 * Date: 8/17/13
 * Time: 10:35 AM
 */
public class ArrayUtils {

    private ArrayUtils() {
    }

    /**
     * Find the first index of the given object in the array.
     * <p/>
     * This method returns <code>-1</code> if <code>null</code> array input.
     *
     * @param array        the array to search through for the object, may be <code>null</code>.
     * @param objectToFind the object to find, may be <code>null</code>.
     * @return the first index of the object within the array, <code>-1</code> if not found or <code>null</code> array input.
     */
    public static int indexOf(Object[] array, Object objectToFind) {
        if (array != null) {
            if (objectToFind == null) {
                for (int i = 0; i < array.length; i++) {
                    if (array[i] == null) {
                        return i;
                    }
                }
            } else {
                for (int i = 0; i < array.length; i++) {
                    if (objectToFind.equals(array[i])) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
}
