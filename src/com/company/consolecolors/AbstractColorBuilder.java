package com.company.consolecolors;

public abstract class AbstractColorBuilder {

    // String builder used throughout class.
    private StringBuilder stringBuilder;

    public AbstractColorBuilder() {
    }

    /**
     * Calculates number of digits from array length.
     * @param array Array must be nonnull.
     * @return Returns number of digits from array length.
     */
    public static int getTextLength(Object[] array) {
        if (array == null)
            throw new IllegalArgumentException("Array cannot be null");

        return getLength(array.length);
    }

    /**
     * Calculates number of digits in number.
     * @param num number
     * @return Returns number of digits in number.
     */
    public static int getTextLength(int num) {
        return getLength(num);
    }

    /**
     * Calculate number of digits in number.
     * @param num number.
     * @return Returns number of digits in number.
     */
    protected static int getLength(int num) {
        int length = 1;
        while ((num /= 10) > 0) {
            ++length;
        }
        return length;
    }

    /**
     * Uses String built in method to return length.
     * @param text String text.
     * @return Returns length of text.
     */
    protected static int getLength(String text) {
        return text.length();
    }

    /**
     * Appends encoded ansi reset and new line on <c>StringBuilder</c>.
     */
    public void appendAnsiReset_newLine() {
        appendAnsiReset();
        appendNewLine();
    }

    /**
     * Appends encoded ansi reset on <c>StringBuilder</c>.
     * Use it only after encoding color and only if you are done using this color.
     */
    public void appendAnsiReset() {
        stringBuilder.append(AnsiColor.ANSI_RESET.getValue());
    }

    /**
     * Appends new line on <c>StringBuilder</c>
     */
    public void appendNewLine() {
        stringBuilder.append('\n');
    }

    /**
     * Build String from <c>StringBuilder</c> and create new <c>StringBuilder</c>.
     * @return String from <c>StringBuilder</c>.
     */
    public String getStringText_clear() {
        String stringText = getStringText();
        flushStringBuilder();
        return stringText;
    }

    /**
     * Builds String from <c>StringBuilder</c>.
     * @return String from <c>StringBuilder</c>.
     */
    public String getStringText() {
        return stringBuilder.toString();
    }

    /**
     * Creates new <c>StringBuilder</c>.
     */
    public void flushStringBuilder() {
        stringBuilder = new StringBuilder();
    }

    public void append(String str) {
        stringBuilder.append(str);
    }

    public void append(int i) {
        stringBuilder.append(i);
    }

    public void append(char c) {
        stringBuilder.append(c);
    }
}
