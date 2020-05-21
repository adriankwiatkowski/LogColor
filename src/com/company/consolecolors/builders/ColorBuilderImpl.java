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
        appendTextAlign(text, getTextLength(text), textAlignment);
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
    public void appendTextColor(AnsiColor fg, AnsiColor bg,
                                String text, TextAlignment textAlignment) {
        appendColor(fg);
        appendColor(bg);
        appendTextAlign(text, getTextLength(text), textAlignment);
    }

    @Override
    public void appendTextAlign(String text) {
        appendTextAlign(text, getTextLength(text), getDefaultTextAlignment());
    }

    @Override
    public void appendTextAlign(String text, TextAttribute textAttribute) {
        appendTextAlign(text, textAttribute.getTotalLength(), textAttribute.getTextAlignment());
    }

    /**
     * Appends aligned text specified by <c>TextAlignment</c>.
     *
     * @param text          appended text.
     * @param maxTextLength max length of text.
     * @param textAlignment constrains how text is aligned.
     * @see TextAlignment
     */
    @Override
    public void appendTextAlign(String text, int maxTextLength, TextAlignment textAlignment) {
        int textLength = TextLengthUtils.getTextLength(text);
        if (maxTextLength < textLength) {
            onInvalidTextLength(TAG, ERROR_TEXT_LENGTH_GRATER_THAN_MAX_LENGTH);
            maxTextLength = textLength;
        }

        textAlignment.appendAligned(getStringBuilder(), text, maxTextLength);
    }

    protected void onInvalidTextLength(String tag, String msg) {
        Log.v(tag, msg);
    }

    protected TextAlignment getDefaultTextAlignment() {
        return DEFAULT_TEXT_ALIGNMENT;
    }
}
