package com.company.consolecolors;

/**
 * Simple class that uses <c>StringBuilder</c> to encode colored ansi text.
 *
 * @see StringBuilder
 */
public class ConsoleColorBuilder extends AbstractColorBuilder {

    private int mMaxTextLeft;
    private int mMaxTextRight;
    private int mSpaceLeft;
    private int mSpaceRight;

    /**
     * Default constructor
     * with given max length on left and right text.
     * @param maxTextLeft max digits or letters in left text.
     * @param maxTextRight max digits or letters in right text.
     */
    public ConsoleColorBuilder(int maxTextLeft, int maxTextRight) {
        this(maxTextLeft, maxTextRight, 0, 0);
    }

    public ConsoleColorBuilder(int maxTextLeft, int maxTextRight, int spaceLeft, int spaceRight) {
        super();
        this.mMaxTextLeft = maxTextLeft;
        this.mMaxTextRight = maxTextRight;
        this.mSpaceLeft = spaceLeft;
        this.mSpaceRight = spaceRight;
    }

    @Override
    public void appendAlignedText(String text, int maxTextLength, int spaceLength, TextAlignment textAlignment) {
        textAlignment.appendAligned(getStringBuilder(), text, maxTextLength + spaceLength);
    }

    /**
     * Appends aligned text to <c>StringBuilder</c>.
     * Should be used after encoding color.
     * Does not reset color.
     * @param leftTextAlignment alignment used to align text on left side.
     * @param rightTextAlignment alignment used to align text on right side.
     * @param textLeft textLeft length must not be greater than max text length.
     * @param textRight textRight length must not be grater than max text length.
     */
    public void appendAlignedText(TextAlignment leftTextAlignment,
                                  TextAlignment rightTextAlignment,
                                  String textLeft, String textRight) {
        appendAlignedText(textLeft, mMaxTextLeft, mSpaceLeft, leftTextAlignment);
        appendAlignedText(textRight, mMaxTextRight, mSpaceRight, rightTextAlignment);
        //System.out.print(fg + bg + fgIndex + " " + bgIndex);
    }

    /**
     * Appends colored text to <c>StringBuilder</c>.
     * Does not reset color.
     * @param color background or foreground ANSI.
     * @param leftTextAlignment alignment used to align text on left side.
     * @param rightTextAlignment alignment used to align text on right side.
     * @param textLeft textLeft length must not be greater than max text length.
     * @param textRight textRight length must not be grater than max text length.
     */
    public void appendColor(AnsiColor color,
                            TextAlignment leftTextAlignment,
                            TextAlignment rightTextAlignment,
                            int textLeft, int textRight) {
        appendColor(color, leftTextAlignment, rightTextAlignment,
                String.valueOf(textLeft), String.valueOf(textRight));
    }

    /**
     * Appends colored text to <c>StringBuilder</c>.
     * Does not reset color.
     * @param color background or foreground ANSI.
     * @param leftTextAlignment alignment used to align text on left side.
     * @param rightTextAlignment alignment used to align text on right side.
     * @param textLeft textLeft length must not be greater than max text length.
     * @param textRight textRight length must not be grater than max text length.
     */
    public void appendColor(AnsiColor color,
                            TextAlignment leftTextAlignment,
                            TextAlignment rightTextAlignment,
                            String textLeft, String textRight) {
        // Encode color (bg or fg).
        append(color.getValue());
        // Append aligned text, so there is some space between texts.
        appendAlignedText(leftTextAlignment, rightTextAlignment, textLeft, textRight);
    }

    /**
     * Appends colored text to <c>StringBuilder</c>.
     * Does not reset color.
     * @param fg foregroundString ANSI.
     * @param bg backgroundString ANSI.
     * @param leftTextAlignment alignment used to align text on left side.
     * @param rightTextAlignment alignment used to align text on right side.
     * @param textLeft foreground text on left.
     * @param textRight foreground text on right.
     */
    public void appendFgBg(AnsiColor fg, AnsiColor bg,
                           TextAlignment leftTextAlignment,
                           TextAlignment rightTextAlignment,
                           int textLeft, int textRight) {
        appendFgBg(fg, bg,
                leftTextAlignment, rightTextAlignment,
                String.valueOf(textLeft), String.valueOf(textRight));
    }

    /**
     * Appends colored text to <c>StringBuilder</c>.
     * Does not reset color.
     * @param fg foreground ANSI.
     * @param bg background ANSI.
     * @param leftTextAlignment alignment used to align text on left side.
     * @param rightTextAlignment alignment used to align text on right side.
     * @param textLeft textLeft length must not be greater than max text length.
     * @param textRight textRight length must not be grater than max text length.
     */
    public void appendFgBg(AnsiColor fg, AnsiColor bg,
                           TextAlignment leftTextAlignment,
                           TextAlignment rightTextAlignment,
                           String textLeft, String textRight) {
        // Encode foreground and background colors.
        append(fg.getValue());
        append(bg.getValue());
        // Append aligned text, so there is some space between texts.
        appendAlignedText(leftTextAlignment, rightTextAlignment, textLeft, textRight);
    }
}