package com.company.consolecolors.builders;

import com.company.consolecolors.models.TextAlignment;

public class SimpleColorBuilder extends ColorBuilderImpl {

    private static final TextAlignment DEFAULT_TEXT_ALIGNMENT = TextAlignment.NONE;
    private static final int INVALID_TEXT_LENGTH = -1;
    private static final int DEFAULT_SPACE_LENGTH = 0;

    private TextAlignment mTextAlignment;
    private int mTextLength;
    private int mSpaceLength;

    private SimpleColorBuilder(Builder builder) {
        super(builder);
        this.mTextAlignment = builder.textAlignment;
        this.mTextLength = builder.textLength;
        this.mSpaceLength = builder.spaceLength;
    }

    public static class Builder extends ColorBuilderImpl.Builder {
        private TextAlignment textAlignment = DEFAULT_TEXT_ALIGNMENT;
        private int textLength = INVALID_TEXT_LENGTH;
        private int spaceLength = DEFAULT_SPACE_LENGTH;

        public Builder() {
        }

        public Builder addTextAlignment(TextAlignment textAlignment) {
            if (this.textAlignment == null)
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

        public Builder setSpaceLength(int spaceLength) {
            if (spaceLength < 0) {
                throw new IllegalArgumentException("Space length must be non negative number.");
            }

            this.spaceLength = spaceLength;

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
    protected int getDefaultSpaceLength() {
        return mSpaceLength;
    }

    @Override
    protected TextAlignment getDefaultTextAlignment() {
        return mTextAlignment;
    }

    public void setTextLength(int textLength) {
        if (textLength <= 0) {
            throw new IllegalArgumentException("Text length must be grater than 0.");
        }

        this.mTextLength = textLength;
    }

    public void setSpaceLength(int spaceLength) {
        if (spaceLength < 0) {
            throw new IllegalArgumentException("Space length must be non negative number.");
        }

        this.mSpaceLength = spaceLength;
    }
}
