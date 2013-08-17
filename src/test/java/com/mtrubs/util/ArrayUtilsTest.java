package com.mtrubs.util;

import com.mtrubs.test.UnitTest;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * User: Matthew
 * Date: 8/17/13
 * Time: 10:46 AM
 */
@Category(UnitTest.class)
@RunWith(JUnitParamsRunner.class)
public class ArrayUtilsTest {

    @SuppressWarnings("UnusedDeclaration")
    private static Object[][] indexOfValues() {
        return new Object[][]{
                {null, null, -1}, // null array
                {new Object[]{1, 2, 4}, null, -1}, // null object
                {new Object[]{1, 2, 4, null}, null, 3}, // null object
                {new Object[]{}, 1, -1}, // empty array
                {new Object[]{1}, "a", -1}, // different object
                {new Object[]{1, 1, 1}, 1, 0}, // repeats
                {new Object[]{1, 2, 3}, 1, 0},
                {new Object[]{1, 2, 3}, 2, 1},
                {new Object[]{1, 2, 3}, 3, 2},
                {new Object[]{"aa", "a", "b"}, "a", 1},
        };
    }

    @Test
    @Parameters(method = "indexOfValues")
    public void indexOf(Object[] array, Object object, int expected) {
        assertEquals(expected, ArrayUtils.indexOf(array, object));
    }
}
