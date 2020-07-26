package com.example.logcolor.color.models;

import java.util.Objects;

public class TextAttribute {

    private TextAlignment textAlignment;
    private int mTotalLength;

    public TextAttribute(TextAlignment textAlignment, int maxTextLength) {
        if (textAlignment == null) {
            throw new IllegalArgumentException("Text alignment cannot be null.");
        }
        if (maxTextLength <= 0) {
            throw new IllegalArgumentException("Text length must be at least 1.");
        }

        this.textAlignment = textAlignment;
        this.mTotalLength = maxTextLength;
    }

    public TextAttribute(TextAttribute textAttribute) {
        if (textAttribute == null) {
            throw new IllegalArgumentException("TextAttribute cannot be null.");
        }

        this.textAlignment = textAttribute.getTextAlignment();
        this.mTotalLength = textAttribute.getTotalLength();
    }

    @Override
    public String toString() {
        return "TextAttribute{" +
               "textAlignment=" + textAlignment +
               ", mTotalLength=" + mTotalLength +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextAttribute that = (TextAttribute) o;
        return mTotalLength == that.mTotalLength &&
               textAlignment == that.textAlignment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(textAlignment, mTotalLength);
    }

    public TextAlignment getTextAlignment() {
        return textAlignment;
    }

    public void setTextAlignment(TextAlignment textAlignment) {
        if (textAlignment == null) {
            throw new IllegalArgumentException("Text alignment cannot be null.");
        }
        this.textAlignment = textAlignment;
    }

    public int getTotalLength() {
        return mTotalLength;
    }

    public void setTotalLength(int textLength) {
        if (textLength <= 0) {
            throw new IllegalArgumentException("Text length must be at least 1.");
        }
        this.mTotalLength = textLength;
    }
}
