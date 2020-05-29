package com.example.logcolor.builders;

import com.example.logcolor.models.TextAlignment;
import com.example.logcolor.utils.TextUtils;

public class SimpleColorBuilder extends ColorBuilderImpl {

    private static final String TAG = SimpleColorBuilder.class.getSimpleName();
    private static final String ERROR_TRUNCATING_STRING = "String too long, truncating.";

    private static final TextAlignment DEFAULT_TEXT_ALIGNMENT = TextAlignment.NONE;
    private static final int INVALID_TEXT_LENGTH = -1;

    private TextAlignment mTextAlignment;
    private int mTextLength;

    private SimpleColorBuilder(Builder builder) {
        super(builder);
        this.mTextAlignment = builder.textAlignment;
        this.mTextLength = builder.textLength;
    }

    public static class Builder extends ColorBuilderImpl.Builder {
        private TextAlignment textAlignment = DEFAULT_TEXT_ALIGNMENT;
        private int textLength = INVALID_TEXT_LENGTH;

        public Builder() {
        }

        public Builder addTextAlignment(TextAlignment textAlignment) {
            if (textAlignment == null)
                throw new IllegalArgumentException("TextAlignment cannot be null.");

            this.textAlignment = textAlignment;

            return this;
        }

        public Builder setTextLength(int textLength) {
            if (textLength <= 0) {
                throw new IllegalArgumentException("Text length must be grater than 0.");
            }

            this.textLength = textLength;

            return this;
        }

        @Override
        public SimpleColorBuilder build() {
            return new SimpleColorBuilder(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    @Override
    public void appendTextAlign(String text, int maxTextLength, TextAlignment textAlignment) {
        if (mTextLength != INVALID_TEXT_LENGTH) {
            int textLength = TextUtils.getTextLength(text);
            if (mTextLength < textLength) {
                onInvalidTextLength(TAG, ERROR_TRUNCATING_STRING);
                text = text.substring(0, mTextLength);
            }

            getTextAlignment().appendAligned(getStringBuilder(), text, mTextLength);
        } else {
            super.appendTextAlign(text, maxTextLength, getTextAlignment());
        }
    }

    public void resetTextLength() {
        this.mTextLength = INVALID_TEXT_LENGTH;
    }

    public void setTextLength(int textLength) {
        if (textLength <= 0) {
            throw new IllegalArgumentException("Text length must be grater than 0.");
        }

        this.mTextLength = textLength;
    }

    public void setTextAlignment(TextAlignment textAlignment) {
        if (textAlignment == null)
            throw new IllegalArgumentException("TextAlignment cannot be null.");

        this.mTextAlignment = textAlignment;
    }

    public TextAlignment getTextAlignment() {
        return mTextAlignment;
    }

    @Override
    protected TextAlignment getDefaultTextAlignment() {
        return mTextAlignment;
    }
}
