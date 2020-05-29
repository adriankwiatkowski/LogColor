package com.example.color.utils;

public class TextUtils {

    private TextUtils() {
    }

    /**
     * Calculates number of digits from array length.
     *
     * @param array array.
     * @return Returns number of digits from array length.
     */
    public static int getTextLength(Object[] array) {
        return (array == null) ? (0) : (getTextLength(array.length));
    }

    /**
     * Calculate number of digits in number.
     *
     * @param num number.
     * @return Returns number of digits in number.
     */
    public static int getTextLength(int num) {
        int length = 1;
        while ((num /= 10) > 0) {
            ++length;
        }
        return length;
    }

    public static int getTextLength(String text) {
        return text.length();
    }
}
