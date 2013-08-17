package com.mtrubs.util;

/**
 * User: Matthew
 * Date: 8/11/13
 * Time: 7:44 AM
 */
public class StringUtils {

    private StringUtils() {
    }

    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean isBlank(String value) {
        return isEmpty(value) || isWhitespace(value);
    }

    public static boolean isWhitespace(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public static String replace(String text, String replace, String with) {
        if (isEmpty(text) || isEmpty(replace) || with == null) {
            return text;
        }
        int start = 0;
        int end = text.indexOf(replace, start);
        if (end == -1) {
            return text;
        }
        int replaceLength = replace.length();
        int increase = with.length() - replaceLength;
        increase = (increase < 0 ? 0 : increase);
        increase *= 16;
        StringBuilder buf = new StringBuilder(text.length() + increase);
        while (end != -1) {
            buf.append(text.substring(start, end)).append(with);
            start = end + replaceLength;
            end = text.indexOf(replace, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }
}
