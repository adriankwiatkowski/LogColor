package com.company.consolecolors.builders;

import com.company.consolecolors.models.AnsiColor;
import com.company.consolecolors.models.TextAlignment;

import java.util.Objects;

public abstract class AbstractColorBuilder {

    // String builder used throughout class.
    private StringBuilder mStringBuilder;

    AbstractColorBuilder(Builder<?> builder) {
        mStringBuilder = Objects.requireNonNullElseGet(builder.stringBuilder, StringBuilder::new);
    }

    public abstract static class Builder<T extends Builder<T>> {

        StringBuilder stringBuilder;

        public T addStringBuilder(StringBuilder stringBuilder) {
            this.stringBuilder = stringBuilder;
            return self();
        }

        public abstract AbstractColorBuilder build();

        protected abstract T self();
    }

    /**
     * Appends aligned text specified by <c>TextAlignment</c>.
     *
     * @param text          appended text.
     * @param maxTextLength max length of text.
     * @param spaceLength   space between texts.
     * @param textAlignment constrains how text is aligned.
     * @see TextAlignment
     */
    public abstract void appendAlignedText(
            String text, int maxTextLength, int spaceLength, TextAlignment textAlignment);

    /**
     * Calculates number of digits from array length.
     *
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
    protected static int getLength(int num) {
        int length = 1;
        while ((num /= 10) > 0) {
            ++length;
        }
        return length;
    }

    /**
     * Uses String built in method to return length.
     *
     * @param text String text.
     * @return Returns length of text.
     */
    protected static int getLength(String text) {
        return text.length();
    }

    /**
     * Encodes colors and append text to that color.
     * Does not encode reset.
     *
     * @param color       foreground or background color.
     * @param coloredText text to be colored.
     */
    public void appendColoredText(AnsiColor color, String coloredText) {
        append(color.getValue());
        append(coloredText);
    }

    /**
     * Encodes colors and append text to that color.
     * Does not encode reset.
     *
     * @param fg          foreground color.
     * @param bg          background color.
     * @param coloredText text to be colored.
     */
    public void appendColoredText(AnsiColor fg, AnsiColor bg, String coloredText) {
        append(fg.getValue());
        append(bg.getValue());
        append(coloredText);
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
        mStringBuilder.append(AnsiColor.ANSI_RESET.getValue());
    }

    /**
     * Appends new line on <c>StringBuilder</c>
     */
    public void appendNewLine() {
        mStringBuilder.append('\n');
    }

    /**
     * Build String from <c>StringBuilder</c> and create new <c>StringBuilder</c>.
     *
     * @return String from <c>StringBuilder</c>.
     */
    public String getStringText_clear() {
        String stringText = getStringText();
        flushStringBuilder();
        return stringText;
    }

    /**
     * Builds String from <c>StringBuilder</c>.
     *
     * @return String from <c>StringBuilder</c>.
     */
    public String getStringText() {
        return mStringBuilder.toString();
    }

    /**
     * Creates new <c>StringBuilder</c>.
     */
    public void flushStringBuilder() {
        mStringBuilder = new StringBuilder();
    }

    protected StringBuilder getStringBuilder() {
        return mStringBuilder;
    }

    public void append(String str) {
        mStringBuilder.append(str);
    }

    public void append(int i) {
        mStringBuilder.append(i);
    }

    public void append(char c) {
        mStringBuilder.append(c);
    }
}
