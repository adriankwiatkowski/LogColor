package com.example.colorbuilder.builders;

import com.example.color.models.AnsiColor;
import com.example.color.models.TextAlignment;
import com.example.color.models.TextAttribute;
import com.example.color.utils.TextUtils;

import static com.example.color.utils.TextUtils.getTextLength;

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
        append(color.getAnsi());
    }

    @Override
    public void appendColor(AnsiColor fg, AnsiColor bg) {
        append(bg.getAnsi());
        append(fg.getAnsi());
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
        appendColor(fg, bg);
        appendTextAlign(text);
    }

    @Override
    public void appendTextColor(AnsiColor fg, AnsiColor bg,
                                String text, TextAlignment textAlignment) {
        appendColor(fg, bg);
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
        int textLength = TextUtils.getTextLength(text);
        if (maxTextLength < textLength) {
            onInvalidTextLength(TAG, ERROR_TEXT_LENGTH_GRATER_THAN_MAX_LENGTH);
            maxTextLength = textLength;
        }

        textAlignment.appendAligned(getStringBuilder(), text, maxTextLength);
    }

    protected void onInvalidTextLength(String tag, String msg) {
    }

    protected TextAlignment getDefaultTextAlignment() {
        return DEFAULT_TEXT_ALIGNMENT;
    }
}
