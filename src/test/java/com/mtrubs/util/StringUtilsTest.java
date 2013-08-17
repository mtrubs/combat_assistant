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
 * Date: 8/11/13
 * Time: 7:45 AM
 */
@Category(UnitTest.class)
@RunWith(JUnitParamsRunner.class)
public class StringUtilsTest {

    @SuppressWarnings("UnusedDeclaration")
    private static Object[][] emptyValues() {
        return new Object[][]{
                {null, true},
                {"", true},
                {"  ", false},
                {"water", false},
        };
    }

    @Test
    @Parameters(method = "emptyValues")
    public void isEmpty(String value, boolean expected) {
        assertEquals(expected, StringUtils.isEmpty(value));
    }

    @SuppressWarnings("UnusedDeclaration")
    private static Object[][] blankValues() {
        return new Object[][]{
                {null, true},
                {"", true},
                {"  ", true},
                {"water", false},
        };
    }

    @Test
    @Parameters(method = "blankValues")
    public void isBlank(String value, boolean expected) {
        assertEquals(expected, StringUtils.isBlank(value));
    }

    @SuppressWarnings("UnusedDeclaration")
    private static Object[][] whitespaceValues() {
        return new Object[][]{
                {null, false},
                {"", true},
                {"  ", true},
                {"water", false},
        };
    }

    @Test
    @Parameters(method = "whitespaceValues")
    public void isWhitespace(String value, boolean expected) {
        assertEquals(expected, StringUtils.isWhitespace(value));
    }

/*
            StringUtils.replace("abaa", "a", null) = "abaa"
            StringUtils.replace("abaa", "a", "")   = "b"
            StringUtils.replace("abaa", "a", "z")  = "zbzz"
            */

    @SuppressWarnings("UnusedDeclaration")
    private static Object[][] replaceValues() {
        return new Object[][]{
                {null, "x", "y", null},
                {"", "x", "y", ""},
                {"any", null, "a", "any"},
                {"any", "a", null, "any"},
                {"any", "", "y", "any"},
                {"any", "x", "y", "any"},
                {"abaa", "a", null, "abaa"},
                {"abaa", "a", "", "b"},
                {"abaa", "a", "z", "zbzz"},
        };
    }

    @Test
    @Parameters(method = "replaceValues")
    public void replace(String text, String replace, String with, String expected) {
        assertEquals(expected, StringUtils.replace(text, replace, with));
    }
}
