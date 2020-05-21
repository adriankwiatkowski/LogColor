package com.company.consolecolors.builders;

import com.company.consolecolors.models.AnsiColor;
import com.company.consolecolors.models.TextAlignment;
import com.company.consolecolors.models.TextAttribute;
import com.company.consolecolors.utils.TextLengthUtils;
import com.company.consolecolors.utils.log.Log;

import static com.company.consolecolors.utils.TextLengthUtils.*;

public class ColorBuilderImpl extends AbstractColorBuilder {

    private static final String TAG = ColorBuilderImpl.class.getSimpleName();
    private static final String ERROR_TEXT_LENGTH_GRATER_THAN_MAX_LENGTH =
            "Text length was grater than specified max text length. Increasing max text length.";

    private static final TextAlignment DEFAULT_TEXT_ALIGNMENT = TextAlignment.NONE;
    private static final int DEFAULT_SPACE_LENGTH = 0;

    public ColorBuilderImpl(Builder builder) {
        super(builder);
    }

    public static class Builder extends AbstractColorBuilder.Builder {

        public Builder() {
        }

        @Override
        public ColorBuilderImpl build() {
            return new ColorBuilderImpl(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    @Override
    public void appendColor(AnsiColor color) {
        append(color.getValue());
    }

    @Override
    public void appendColor(AnsiColor fg, AnsiColor bg) {
        append(fg.getValue());
        append(bg.getValue());
    }

    /**
     * Encodes colors and append text to that color.
     * Does not encode reset.
     *
     * @param color foreground or background color.
     * @param text  text to be colored.
     */
    @Override
    public void appendTextColor(AnsiColor color, String text) {
        appendColor(color);
        appendTextAlign(text);
    }

    @Override
    public void appendTextColor(AnsiColor color, String text, TextAlignment textAlignment) {
        appendColor(color);
        appendTextAlign(text, getTextLength(text), getDefaultSpaceLength(), textAlignment);
    }

    /**
     * Encodes colors and append text to that color.
     * Does not encode reset.
     *
     * @param fg   foreground color.
     * @param bg   background color.
     * @param text text to be colored.
     */
    @Override
    public void appendTextColor(AnsiColor fg, AnsiColor bg, String text) {
        appendColor(fg);
        appendColor(bg);
        appendTextAlign(text);
    }

    @Override
    public void appendTextColor(AnsiColor fg, AnsiColor bg, String text, TextAlignment textAlignment) {
        appendColor(fg);
        appendColor(bg);
        appendTextAlign(text, getTextLength(text), getDefaultSpaceLength(), textAlignment);
    }

    @Override
    public void appendTextAlign(String text) {
        appendTextAlign(text, getTextLength(text), getDefaultSpaceLength(), getDefaultTextAlignment());
    }

    @Override
    public void appendTextAlign(String text, TextAttribute textAttribute) {
        appendTextAlign(text, textAttribute.getTextLength(),
                textAttribute.getSpaceLength(), textAttribute.getTextAlignment());
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
    @Override
    public void appendTextAlign(String text, int maxTextLength, int spaceLength, TextAlignment textAlignment) {
        int textLength = TextLengthUtils.getTextLength(text);
        if (maxTextLength < textLength) {
            maxTextLength = textLength;
            Log.v(TAG, ERROR_TEXT_LENGTH_GRATER_THAN_MAX_LENGTH);
        }

        textAlignment.appendAligned(getStringBuilder(), text, maxTextLength + spaceLength);
    }

    protected int getDefaultSpaceLength() {
        return DEFAULT_SPACE_LENGTH;
    }

    protected TextAlignment getDefaultTextAlignment() {
        return DEFAULT_TEXT_ALIGNMENT;
    }
}
