package com.company.consolecolors.builders;

import com.company.consolecolors.models.AnsiColor;
import com.company.consolecolors.models.TextAlignment;
import com.company.consolecolors.models.TextAttribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple class that uses <c>StringBuilder</c> to encode colored ansi text.
 * You must pass same count of <c>String</c> text as <c>TextAttribute</c>.
 *
 * @see StringBuilder
 */
public class ColorBuilder extends AbstractColorBuilder {

    private List<TextAttribute> mTextAttributeList;

    public ColorBuilder(Builder builder) {
        super(builder);
        mTextAttributeList = builder.textAttributeList;
    }

    public static class Builder extends AbstractColorBuilder.Builder<Builder> {
        private List<TextAttribute> textAttributeList;

        public Builder(TextAttribute textAttribute, int textAttributeDuplicateCount) {
            if (textAttribute == null)
                throw new IllegalArgumentException("TextAttribute cannot be null.");
            if (textAttributeDuplicateCount <= 0)
                throw new IllegalArgumentException("Text attribute count must be grater than 0.");

            textAttributeList = new ArrayList<>();
            while (textAttributeDuplicateCount-- > 0) {
                textAttributeList.add(new TextAttribute(textAttribute));
            }
        }

        public Builder(TextAttribute textAttribute) {
            if (textAttribute == null)
                throw new IllegalArgumentException("TextAttribute cannot be null.");

            textAttributeList = new ArrayList<>();
            textAttributeList.add(textAttribute);
        }

        public Builder(List<TextAttribute> textAttributes) {
            if (textAttributes == null || textAttributes.isEmpty())
                throw new IllegalArgumentException("List cannot be null or empty.");

            textAttributeList = new ArrayList<>(textAttributes);
        }

        public Builder addTextAttribute(TextAttribute textAttribute) {
            if (textAttribute == null)
                throw new IllegalArgumentException("TextAttribute cannot be null.");

            textAttributeList.add(textAttribute);

            return this;
        }

        public Builder addTextAttributeList(List<TextAttribute> textAttributes) {
            if (textAttributes == null || textAttributes.isEmpty())
                throw new IllegalArgumentException("List cannot be null or empty.");

            textAttributeList.addAll(textAttributes);

            return this;
        }

        @Override
        public ColorBuilder build() {
            return new ColorBuilder(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    @Override
    public void appendAlignedText(String text, int maxTextLength, int spaceLength, TextAlignment textAlignment) {
        textAlignment.appendAligned(getStringBuilder(), text, maxTextLength + spaceLength);
        //System.out.print(fg + bg + fgIndex + " " + bgIndex);
    }

    /**
     * Appends aligned text to <c>StringBuilder</c>.
     * Should be used after encoding color.
     * Does not reset color.
     *
     * @param textList list of texts.
     * @throws IllegalArgumentException if text count isn't equal attribute count.
     */
    public void appendAlignedText(List<String> textList) {
        if (textList.size() != mTextAttributeList.size())
            throw new IllegalArgumentException("There must be same texts as attributes count.");

        for (int i = 0; i < textList.size(); ++i) {
            String text = textList.get(i);
            TextAttribute textAttribute = mTextAttributeList.get(i);

            appendAlignedText(text,
                    textAttribute.getTextLength(),
                    textAttribute.getSpaceLength(),
                    textAttribute.getTextAlignment());
        }
    }

    /**
     * Appends aligned text to <c>StringBuilder</c>.
     * Should be used after encoding color.
     * Does not reset color.
     *
     * @param text1 text.
     * @throws IllegalArgumentException if text count isn't equal attribute count.
     */
    public void appendAlignedText(String text1) {
        if (1 != mTextAttributeList.size())
            throw new IllegalArgumentException("There must be same texts as attributes count.");

        TextAttribute textAttribute = mTextAttributeList.get(0);

        appendAlignedText(text1,
                textAttribute.getTextLength(),
                textAttribute.getSpaceLength(),
                textAttribute.getTextAlignment());
    }

    /**
     * Appends aligned text to <c>StringBuilder</c>.
     * Should be used after encoding color.
     * Does not reset color.
     *
     * @param text1 text 1.
     * @param text2 text 2.
     * @throws IllegalArgumentException if text count isn't equal attribute count.
     */
    public void appendAlignedText(String text1, String text2) {
        if (2 != mTextAttributeList.size())
            throw new IllegalArgumentException("There must be same texts as attributes count.");

        TextAttribute textAttribute = mTextAttributeList.get(0);

        appendAlignedText(text1,
                textAttribute.getTextLength(),
                textAttribute.getSpaceLength(),
                textAttribute.getTextAlignment());

        textAttribute = mTextAttributeList.get(1);

        appendAlignedText(text2,
                textAttribute.getTextLength(),
                textAttribute.getSpaceLength(),
                textAttribute.getTextAlignment());
    }

    /**
     * Appends aligned text to <c>StringBuilder</c>.
     * Should be used after encoding color.
     * Does not reset color.
     *
     * @param text1 text 1.
     * @param text2 text 2.
     * @param text3 text 3.
     * @throws IllegalArgumentException if text count isn't equal attribute count.
     */
    public void appendAlignedText(String text1, String text2, String text3) {
        if (3 != mTextAttributeList.size())
            throw new IllegalArgumentException("There must be same texts as attributes count.");

        TextAttribute textAttribute = mTextAttributeList.get(0);

        appendAlignedText(text1,
                textAttribute.getTextLength(),
                textAttribute.getSpaceLength(),
                textAttribute.getTextAlignment());

        textAttribute = mTextAttributeList.get(1);

        appendAlignedText(text2,
                textAttribute.getTextLength(),
                textAttribute.getSpaceLength(),
                textAttribute.getTextAlignment());

        textAttribute = mTextAttributeList.get(2);

        appendAlignedText(text3,
                textAttribute.getTextLength(),
                textAttribute.getSpaceLength(),
                textAttribute.getTextAlignment());
    }

    /**
     * Appends colored text to <c>StringBuilder</c>.
     * Does not reset color.
     *
     * @param color    background or foreground ANSI.
     * @param textList list of texts.
     */
    public void appendColoredText(AnsiColor color, List<String> textList) {
        // Encode background or foreground color.
        append(color.getValue());
        // Append aligned text.
        appendAlignedText(textList);
    }

    /**
     * Appends colored text to <c>StringBuilder</c>.
     * Does not reset color.
     *
     * @param color background or foreground ANSI.
     * @param text1 text 1.
     */
    @Override
    public void appendColoredText(AnsiColor color, String text1) {
        // Encode background or foreground color.
        append(color.getValue());
        // Append aligned text.
        appendAlignedText(text1);
    }

    /**
     * Appends colored text to <c>StringBuilder</c>.
     * Does not reset color.
     *
     * @param color background or foreground ANSI.
     * @param text1 text 1.
     * @param text2 text 2.
     */
    public void appendColoredText(AnsiColor color, String text1, String text2) {
        // Encode background or foreground color.
        append(color.getValue());
        // Append aligned text.
        appendAlignedText(text1, text2);
    }

    /**
     * Appends colored text to <c>StringBuilder</c>.
     * Does not reset color.
     *
     * @param color background or foreground ANSI.
     * @param text1 text 1.
     * @param text2 text 2.
     * @param text3 text 3.
     */
    public void appendColoredText(AnsiColor color, String text1, String text2, String text3) {
        // Encode background or foreground color.
        append(color.getValue());
        // Append aligned text.
        appendAlignedText(text1, text2, text3);
    }

    /**
     * Appends colored text to <c>StringBuilder</c>.
     * Does not reset color.
     *
     * @param fg       foreground ANSI.
     * @param bg       background ANSI.
     * @param textList
     */
    public void appendColoredText(AnsiColor fg, AnsiColor bg, List<String> textList) {
        // Encode foreground and background colors.
        append(fg.getValue());
        append(bg.getValue());
        // Append aligned text.
        appendAlignedText(textList);
    }

    /**
     * Appends colored text to <c>StringBuilder</c>.
     * Does not reset color.
     *
     * @param fg    foreground ANSI.
     * @param bg    background ANSI.
     * @param text1 text 1.
     */
    @Override
    public void appendColoredText(AnsiColor fg, AnsiColor bg, String text1) {
        // Encode foreground and background colors.
        append(fg.getValue());
        append(bg.getValue());
        // Append aligned text.
        appendAlignedText(text1);
    }

    /**
     * Appends colored text to <c>StringBuilder</c>.
     * Does not reset color.
     *
     * @param fg    foreground ANSI.
     * @param bg    background ANSI.
     * @param text1 text 1.
     * @param text2 text 2.
     */
    public void appendColoredText(AnsiColor fg, AnsiColor bg, String text1, String text2) {
        // Encode foreground and background colors.
        append(fg.getValue());
        append(bg.getValue());
        // Append aligned text.
        appendAlignedText(text1, text2);
    }

    /**
     * Appends colored text to <c>StringBuilder</c>.
     * Does not reset color.
     *
     * @param fg    foreground ANSI.
     * @param bg    background ANSI.
     * @param text1 text 1.
     * @param text2 text 2.
     * @param text3 text 3.
     */
    public void appendColoredText(AnsiColor fg, AnsiColor bg, String text1, String text2, String text3) {
        // Encode foreground and background colors.
        append(fg.getValue());
        append(bg.getValue());
        // Append aligned text.
        appendAlignedText(text1, text2, text3);
    }

    public void setTextAttribute(int index, TextAttribute textAttribute) {
        if (index < 0 || index >= mTextAttributeList.size())
            throw new IndexOutOfBoundsException("Wrong index.");
        if (textAttribute == null)
            throw new IllegalArgumentException("TextAttribute cannot be null.");

        mTextAttributeList.set(index, textAttribute);
    }

    public void setTextAttributeMaxTextLength(int index, int maxTextLength) {
        if (index < 0 || index >= mTextAttributeList.size())
            throw new IndexOutOfBoundsException("Wrong index.");

        mTextAttributeList.get(index).setTextLength(maxTextLength);
    }

    public void setTextAttributeSpaceLength(int index, int spaceLength) {
        if (index < 0 || index >= mTextAttributeList.size())
            throw new IndexOutOfBoundsException("Wrong index.");

        mTextAttributeList.get(index).setSpaceLength(spaceLength);
    }

    public void setTextAttributeTextAlignment(int index, TextAlignment textAlignment) {
        if (index < 0 || index >= mTextAttributeList.size())
            throw new IndexOutOfBoundsException("Wrong index.");
        if (textAlignment == null)
            throw new IllegalArgumentException("TextAlignment cannot be null.");

        mTextAttributeList.get(index).setTextAlignment(textAlignment);
    }
}