package com.company.consolecolors.models;

public enum TextAlignment {
    NONE {
        @Override
        public void appendAligned(StringBuilder sb, String text, int totalSpace) {
            invalidate(text, totalSpace);
            LEFT.appendAligned(sb, text, totalSpace);
        }
    },
    LEFT {
        @Override
        public void appendAligned(StringBuilder sb, String text, int totalSpace) {
            invalidate(text, totalSpace);
            // Append text on left side.
            sb.append(text);
            // Append white spaces to right to align text to left.
            appendWhiteSpace_numToTextLength(sb, getTextLength(text), totalSpace);
        }
    },
    RIGHT {
        @Override
        public void appendAligned(StringBuilder sb, String text, int totalSpace) {
            invalidate(text, totalSpace);
            // Append white spaces to left to align text to right.
            appendWhiteSpace_numToTextLength(sb, getTextLength(text), totalSpace);
            // Append text on right side.
            sb.append(text);
        }
    },
    CENTER {
        @Override
        public void appendAligned(StringBuilder sb, String text, int totalSpace) {
            invalidate(text, totalSpace);

            // Calculate count on left and right side
            // simply by dividing by 2.
            int diff = totalSpace - getTextLength(text);
            int leftSpaceCount = diff / 2;
            int rightSpaceCount = leftSpaceCount;

            // If number is not even add extra space to left side.
            if (diff % 2 == 1)
                ++leftSpaceCount;

            // Append white spaces to left side.
            appendWhiteSpace_count(sb, leftSpaceCount);
            // Append text to center.
            sb.append(text);
            // Append white spaces to right side.
            appendWhiteSpace_count(sb, rightSpaceCount);
        }
    };

    /**
     * Appends aligned text on <c>StringBuilder</c>.
     *
     * @param sb         StringBuilder used to append text.
     * @param text       text.
     * @param totalSpace max text length.
     */
    public abstract void appendAligned(StringBuilder sb, String text, int totalSpace);

    /**
     * Checks if text length does not exceed max length.
     *
     * @param text      text to append.
     * @param maxLength max text length.
     */
    public void invalidate(String text, int maxLength) {
        if (text.length() > maxLength)
            throw new IllegalArgumentException("Text length grater than max length.");
    }

    /**
     * Appends white spaces on <c>StringBuilder</c>.
     * Used to align text.
     *
     * @param length        number od digits or letters.
     * @param maxTextLength number of max digits or letters in text.
     * @throws IllegalArgumentException if length is grater than max length.
     */
    public void appendWhiteSpace_numToTextLength(StringBuilder sb, int length, int maxTextLength) {
        int diff = maxTextLength - length;
        if (diff < 0)
            throw new IllegalArgumentException("Length cannot be grater than max length.");

        appendWhiteSpace_count(sb, diff);
    }

    public void appendWhiteSpace_count(StringBuilder sb, int count) {
        while (count-- > 0) {
            sb.append(' ');
        }
    }

    /**
     * Uses String built in method to return length.
     *
     * @param text String text.
     * @return Returns length of text.
     */
    public int getTextLength(String text) {
        return text.length();
    }
}
