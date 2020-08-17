package com.example.logcolor.colorbuilder.builders;

import com.example.logcolor.colorbuilder.text.TextAlignment;
import com.example.logcolor.colorbuilder.text.TextAttribute;

public class SimpleColorBuilder extends ColorBuilderImpl {

    private static final TextAlignment DEFAULT_TEXT_ALIGNMENT = TextAlignment.NONE;

    private TextAlignment mTextAlignment;
    private int mExtraSpace;

    private SimpleColorBuilder(Builder builder) {
        super(builder);
        this.mTextAlignment = builder.textAlignment;
        this.mExtraSpace = builder.extraSpace;
    }

    @Override
    public void append(String s) {
        TextAttribute textAttribute = new TextAttribute.Builder().setTextAlignment(mTextAlignment)
                                                                 .setExtraSpace(mExtraSpace)
                                                                 .build();
        super.append(s, textAttribute);
    }

    @Override
    public void append(String text, TextAttribute textAttribute) {
        if (textAttribute == null) {
            textAttribute = new TextAttribute.Builder().setTextAlignment(mTextAlignment)
                                                       .setExtraSpace(mExtraSpace)
                                                       .build();
        } else {
            if (textAttribute.getTextAlignment() == null) {
                textAttribute.setTextAlignment(mTextAlignment);
            }
            if (textAttribute.getExtraSpace() < 0) {
                textAttribute.setExtraSpace(mExtraSpace);
            }
        }

        super.append(text, textAttribute);
    }

    public void resetExtraSpace() {
        this.mExtraSpace = 0;
    }

    public void setExtraSpace(int extraSpace) {
        if (extraSpace < 0) {
            throw new IllegalArgumentException("Extra space length cannot be lesser than 0.");
        }

        this.mExtraSpace = extraSpace;
    }

    public void setTextAlignment(TextAlignment textAlignment) {
        if (textAlignment == null) {
            throw new IllegalArgumentException("TextAlignment cannot be null.");
        }

        this.mTextAlignment = textAlignment;
    }

    public TextAlignment getTextAlignment() {
        return mTextAlignment;
    }

    public static class Builder extends ColorBuilderImpl.Builder {
        private TextAlignment textAlignment = DEFAULT_TEXT_ALIGNMENT;
        private int extraSpace;

        public Builder() {
        }

        public Builder addTextAlignment(TextAlignment textAlignment) {
            if (textAlignment == null) {
                throw new IllegalArgumentException("TextAlignment cannot be null.");
            }

            this.textAlignment = textAlignment;

            return this;
        }

        public Builder setExtraSpace(int extraSpace) {
            if (extraSpace < 0) {
                throw new IllegalArgumentException("Extra space length cannot be lesser than 0.");
            }

            this.extraSpace = extraSpace;

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
}
