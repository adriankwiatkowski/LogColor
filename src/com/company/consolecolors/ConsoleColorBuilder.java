package com.company.consolecolors;

/**
 * Simple class that uses <c>StringBuilder</c> to encode colored ansi text.
 *
 * @see StringBuilder
 */
public class ConsoleColorBuilder {

    private int maxTextLeft;
    private int maxTextRight;

    // String builder used throughout class.
    private StringBuilder stringBuilder;

    /**
     * Default constructor
     * with given max length on left and right text.
     * @param maxTextLeft max digits or letters in left text.
     * @param maxTextRight max digits or letters in right text.
     */
    public ConsoleColorBuilder(int maxTextLeft, int maxTextRight) {
        this.maxTextLeft = maxTextLeft;
        this.maxTextRight = maxTextRight;

        this.stringBuilder = new StringBuilder();
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
    private static int getLength(int num) {
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
    private static int getLength(String text) {
        return text.length();
    }

    /**
     * Appends colored text to <c>StringBuilder</c>.
     * Does not reset color.
     * @param fg foregroundString ANSI.
     * @param bg backgroundString ANSI.
     * @param textLeft foreground text on left.
     * @param textRight foreground text on right.
     */
    public void appendFgBg(AnsiColor fg, AnsiColor bg, int textLeft, int textRight) {
        appendFgBg(fg, bg, String.valueOf(textLeft), String.valueOf(textRight));
    }

    /**
     * Appends colored text to <c>StringBuilder</c>.
     * Does not reset color.
     * @param fg foregroundString ANSI.
     * @param bg backgroundString ANSI.
     * @param fgTextLeft foregroundTextLeft length must not be greater than max.
     * @param fgTextRight foregroundTextRight length must not be grater than max.
     */
    public void appendFgBg(AnsiColor fg, AnsiColor bg, String fgTextLeft, String fgTextRight) {
        // Encode foreground and background colors.
        stringBuilder.append(fg.getValue()).append(bg.getValue());

        // Append white spaces to left to align text right.
        appendWhiteSpace_numToTextLength(getLength(fgTextLeft), maxTextLeft);
        // Append text on left.
        stringBuilder.append(fgTextLeft);

        // Append tabulator so we have some space between texts.
        stringBuilder.append('\t');

        // Append white spaces to left to align text on right.
        appendWhiteSpace_numToTextLength(getLength(fgTextRight), maxTextRight);
        // Append text on right.
        stringBuilder.append(fgTextRight);

        // Append tabulator so we have some space between next text.
        stringBuilder.append('\t');

        //System.out.print(fg + bg + fgIndex + " " + bgIndex);
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
     * Appends new line on <c>StringBuilder</c>.
     */
    public void appendNewLine() {
        stringBuilder.append('\n');
    }

    /**
     * Appends white spaces on <c>StringBuilder</c> to align text .
     * @param length number od digits or letters.
     * @param desiredLength number of available digits or letters.
     */
    private void appendWhiteSpace_numToTextLength(int length, int desiredLength) {
        int diff = desiredLength - length;
        if (diff < 0)
            throw new IllegalArgumentException("Length cannot be longer then max length.");

        while (diff-- > 0) {
            stringBuilder.append(' ');
        }
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
}