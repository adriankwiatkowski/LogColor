package com.company.consolecolors.models;

import java.util.Objects;

public class TextAttribute {

    private TextAlignment textAlignment;
    private int mTextLength;
    private int mSpaceLength;

    public TextAttribute(TextAlignment textAlignment, int maxTextLength, int spaceLength) {
        if (textAlignment == null)
            throw new IllegalArgumentException("Text alignment cannot be null.");
        if (maxTextLength <= 0)
            throw new IllegalArgumentException("Text length must be at least 1.");
        if (spaceLength < 0)
            throw new IllegalArgumentException("Space length cannot be negative.");

        this.textAlignment = textAlignment;
        this.mTextLength = maxTextLength;
        this.mSpaceLength = spaceLength;
    }

    public TextAttribute(TextAttribute textAttribute) {
        if (textAttribute == null)
            throw new IllegalArgumentException("TextAttribute cannot be null.");

        this.textAlignment = textAttribute.getTextAlignment();
        this.mTextLength = textAttribute.getTextLength();
        this.mSpaceLength = textAttribute.getSpaceLength();
    }

    @Override
    public String toString() {
        return "TextAttribute{" +
                "textAlignment=" + textAlignment +
                ", mTextLength=" + mTextLength +
                ", mSpaceLength=" + mSpaceLength +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextAttribute that = (TextAttribute) o;
        return mTextLength == that.mTextLength &&
                mSpaceLength == that.mSpaceLength &&
                textAlignment == that.textAlignment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(textAlignment, mTextLength, mSpaceLength);
    }

    public TextAlignment getTextAlignment() {
        return textAlignment;
    }

    public void setTextAlignment(TextAlignment textAlignment) {
        if (textAlignment == null)
            throw new IllegalArgumentException("Text alignment cannot be null.");
        this.textAlignment = textAlignment;
    }

    public int getTextLength() {
        return mTextLength;
    }

    public void setTextLength(int textLength) {
        if (textLength <= 0)
            throw new IllegalArgumentException("Text length must be at least 1.");
        this.mTextLength = textLength;
    }

    public int getSpaceLength() {
        return mSpaceLength;
    }

    public void setSpaceLength(int spaceLength) {
        if (spaceLength < 0)
            throw new IllegalArgumentException("Space length cannot be negative.");
        this.mSpaceLength = spaceLength;
    }
}
