package com.mtrubs.util;

import com.mtrubs.test.UnitTest;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * User: Matthew
 * Date: 8/4/13
 * Time: 8:30 PM
 */
@Category(UnitTest.class)
@RunWith(JUnitParamsRunner.class)
public class ReflectionUtilsTest {

    @SuppressWarnings("UnusedDeclaration")
    private Object[][] getPropertyValues() {
        return new Object[][]{
                {new ClassA(), "a", Integer.class, 1, null}, // class access
                {new ClassB(), "a", Integer.class, 1, null}, // super class access
                {new ClassA(), "b", Integer.class, 1, "java.lang.NoSuchFieldException: b"}, // non-present field
                {new ClassB(), "b", Integer.class, 1, null}, // class access
                {null, "b", Integer.class, 0, "Object is null"}, // null object
                {new ClassB(), "x", Integer.class, 1, null}, // static field
                {new ClassB(), "y", Integer.class, 1, null}, // final field
                {new ClassB(), "z", Integer.class, 1, null}, // static final field
                {new ClassA(), "a", String.class, 0, "Cannot cast java.lang.Integer to java.lang.String"}, // wrong field type
                {new ClassA(), null, Integer.class, 0, "Field name is null"}, // null field name
        };
    }

    @Test
    @Parameters(method = "getPropertyValues")
    public void getProperty(ClassA object, String field, Class<?> type, Object expected, String exception) {
        try {
            assertEquals(expected, ReflectionUtils.getProperty(object, field, type));
            if (exception != null) {
                fail("Expected exception: " + exception);
            }
        } catch (RuntimeException e) {
            if (exception == null) {
                fail("Unexpected exception: " + e.getMessage());
            }
            assertEquals(exception, e.getMessage());
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    private Object[][] setPropertyValues() {
        return new Object[][]{
                {new ClassA(), "a", 2, null}, // class access
                {new ClassB(), "a", 2, null}, // super class access
                {new ClassA(), "b", 0, "java.lang.NoSuchFieldException: b"}, // non-present field
                {new ClassB(), "b", 2, null}, // class access
                {null, "b", 2, null}, // null object
                {new ClassB(), "x", 2, null}, // static field
                {new ClassB(), "y", 2, null}, // final field
                {new ClassB(), "z", 0, "java.lang.IllegalAccessException: Can not set static final java.lang.Integer field com.mtrubs.util.ReflectionUtilsTest$ClassB.z to java.lang.Integer"}, // static final field
                {new ClassA(), null, 0, "Field name is null"}, // null field name
        };
    }

    @Test
    @Parameters(method = "setPropertyValues")
    public void setProperty(ClassA object, String field, Object value, String exception) {
        if (object != null) {
            object.reset();
        }
        try {
            ReflectionUtils.setProperty(object, field, value);
            if (exception != null) {
                fail("Expected exception: " + exception);
            }
        } catch (RuntimeException e) {
            if (exception == null) {
                fail("Unexpected exception: " + e.getMessage());
            }
            assertEquals(exception, e.getMessage());
        }
        if (object != null) {
            assertProperty(object, "a", exception == null && "a".equals(field) ? 2 : 1);
        }
        if (object instanceof ClassB) {
            assertProperty(object, "b", exception == null && "b".equals(field) ? 2 : 1);
            assertProperty(object, "x", exception == null && "x".equals(field) ? 2 : 1);
            assertProperty(object, "y", exception == null && "y".equals(field) ? 2 : 1);
            assertProperty(object, "z", exception == null && "z".equals(field) ? 2 : 1);
        }
    }

    private static void assertProperty(Object object, String field, int expected) {
        assertEquals(expected, (int) ReflectionUtils.getProperty(object, field, Integer.class));
    }

    private static class ClassA {

        private Integer a = 1;

        public void reset() {
        }
    }

    private static class ClassB extends ClassA {

        private Integer b = 1;
        private static Integer x = 1;
        private final Integer y = 1;
        private static final Integer z = 1;

        @Override
        public void reset() {
            super.reset();
            x = 1;
        }
    }
}
