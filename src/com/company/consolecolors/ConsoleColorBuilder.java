package com.company.consolecolors;

/**
 * Simple class that uses <c>StringBuilder</c> to encode colored ansi text.
 *
 * @see StringBuilder
 */
public class ConsoleColorBuilder extends AbstractColorBuilder {

    private int maxTextLeft;
    private int maxTextRight;

    /**
     * Default constructor
     * with given max length on left and right text.
     * @param maxTextLeft max digits or letters in left text.
     * @param maxTextRight max digits or letters in right text.
     */
    public ConsoleColorBuilder(int maxTextLeft, int maxTextRight) {
        super();
        this.maxTextLeft = maxTextLeft;
        this.maxTextRight = maxTextRight;
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
        append(fg.getValue());
        append(bg.getValue());

        // Append white spaces to left to align text right.
        appendWhiteSpace_numToTextLength(getLength(fgTextLeft), maxTextLeft);
        // Append text on left.
        append(fgTextLeft);

        // Append tabulator so we have some space between left and right texts.
        append('\t');

        // Append white spaces to left to align text on right.
        appendWhiteSpace_numToTextLength(getLength(fgTextRight), maxTextRight);
        // Append text on right.
        append(fgTextRight);

        // Append tabulator so we have some space between next text.
        append('\t');

        //System.out.print(fg + bg + fgIndex + " " + bgIndex);
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
            append(' ');
        }
    }
}