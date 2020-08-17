package com.example.logcolor.color.models;

import java.awt.*;
import java.util.EnumSet;
import java.util.Objects;

public class TextAttribute {

    private static final int INVALID_EXTRA_SPACE = -1;

    private EnumSet<TextStyle> mTextStyleEnumSet;
    private TextAlignment mTextAlignment;
    private int mExtraSpace;
    private Color mBackground;
    private Color mForeground;

    private TextAttribute(Builder builder) {
        this.mTextStyleEnumSet = builder.textStyleEnumSet;
        this.mTextAlignment = builder.textAlignment;
        this.mExtraSpace = builder.extraSpace;
        this.mBackground = builder.background;
        this.mForeground = builder.foreground;
    }

    public static class Builder {
        private EnumSet<TextStyle> textStyleEnumSet;
        private TextAlignment textAlignment;
        private int extraSpace = INVALID_EXTRA_SPACE;
        private Color background;
        private Color foreground;

        public Builder setTextStyle(EnumSet<TextStyle> textStyleEnumSet) {
            this.textStyleEnumSet = textStyleEnumSet;
            return this;
        }

        public Builder setTextAlignment(TextAlignment textAlignment) {
            this.textAlignment = textAlignment;
            return this;
        }

        public Builder setExtraSpace(int extraSpace) {
            this.extraSpace = extraSpace;
            return this;
        }

        public Builder setBackground(Color background) {
            this.background = background;
            return this;
        }

        public Builder setForeground(Color foreground) {
            this.foreground = foreground;
            return this;
        }

        public TextAttribute build() {
            return new TextAttribute(this);
        }
    }

    public EnumSet<TextStyle> getTextStyleEnumSet() {
        return mTextStyleEnumSet;
    }

    public void setTextStyleEnumSet(EnumSet<TextStyle> textStyleEnumSet) {
        this.mTextStyleEnumSet = textStyleEnumSet;
    }

    public TextAlignment getTextAlignment() {
        return mTextAlignment;
    }

    public void setTextAlignment(TextAlignment textAlignment) {
        this.mTextAlignment = textAlignment;
    }

    public int getExtraSpace() {
        return mExtraSpace;
    }

    public void setExtraSpace(int extraSpace) {
        this.mExtraSpace = extraSpace;
    }

    public Color getBackground() {
        return mBackground;
    }

    public void setBackground(Color background) {
        this.mBackground = background;
    }

    public Color getForeground() {
        return mForeground;
    }

    public void setForeground(Color foreground) {
        this.mForeground = foreground;
    }

    @Override
    public String toString() {
        return "TextAttribute{" + "mTextStyleEnumSet=" + mTextStyleEnumSet + ", mTextAlignment=" +
               mTextAlignment + ", mBackground=" + mBackground + ", mForeground=" + mForeground +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextAttribute that = (TextAttribute) o;
        return Objects.equals(mTextStyleEnumSet, that.mTextStyleEnumSet) &&
               mTextAlignment == that.mTextAlignment &&
               Objects.equals(mBackground, that.mBackground) &&
               Objects.equals(mForeground, that.mForeground);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mTextStyleEnumSet, mTextAlignment, mBackground, mForeground);
    }
}
