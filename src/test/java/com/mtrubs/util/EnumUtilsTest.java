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
 * Date: 8/5/13
 * Time: 8:25 PM
 */
@Category(UnitTest.class)
@RunWith(JUnitParamsRunner.class)
public class EnumUtilsTest {

    @SuppressWarnings("UnusedDeclaration")
    private static Object[][] valueOfValues() {
        return new Object[][] {
                {Type1.Cat, Type1.class, "Cat", null},
                {Type1.Dog, Type1.class, "Dog", null},
                {Type2.Apple, Type2.class, "Apple", null},
                {Type2.Orange, Type2.class, "Orange", null},
                // null input without defaults
                {null, Type1.class, "dog", null}, // case is off
                {null, Type2.class, "Dog", null}, // wrong class
                {null, Type1.class, null, null}, // null name
                {null, null, "Dog", null}, // null class
                // null input with defaults
                {Type1.Cat, Type1.class, "dog", Type1.Cat}, // case is off
                {Type1.Cat, Type2.class, "Dog", Type1.Cat}, // wrong class
                {Type1.Cat, Type1.class, null, Type1.Cat}, // null name
                {Type1.Cat, null, "Dog", Type1.Cat}, // null class
        };
    }

    @Test
    @Parameters(method = "valueOfValues")
    public <E extends Enum<E>> void valueOf(Enum<E> expected, Class<E> type, String name, E defaultValue) {
        assertEquals(expected, EnumUtils.valueOf(type, name, defaultValue));
    }

    private static enum Type1 {
        Dog,
        Cat
    }

    private static enum Type2 {
        Apple,
        Orange
    }
}
