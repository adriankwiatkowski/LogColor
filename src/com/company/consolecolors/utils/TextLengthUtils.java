package com.company.consolecolors.utils;

public class TextLengthUtils {

    private TextLengthUtils() {
    }

    /**
     * Calculates number of digits from array length.
     *
     * @param array array.
     * @return Returns number of digits from array length.
     */
    public static int getTextLength(Object[] array) {
        return (array == null) ? (0) : (getLength(array.length));
    }

    /**
     * Calculates number of digits in number.
     *
     * @param num number.
     * @return Returns number of digits in number.
     */
    public static int getTextLength(int num) {
        return getLength(num);
    }

    /**
     * Calculate number of digits in number.
     *
     * @param num number.
     * @return Returns number of digits in number.
     */
    public static int getLength(int num) {
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
