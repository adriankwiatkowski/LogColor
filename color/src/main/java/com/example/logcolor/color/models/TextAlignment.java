package com.example.logcolor.color.models;

public enum TextAlignment {
    NONE {
        @Override
        public String writeAligned(String text, int extraSpace) {
            return LEFT.writeAligned(text, extraSpace);
        }
    },
    LEFT {
        @Override
        public String writeAligned(String text, int extraSpace) {
            invalidate(extraSpace);
            StringBuilder sb = new StringBuilder();
            // Append text on left side.
            sb.append(text);
            // Append white spaces to right to align text to left.
            appendWhiteSpace(sb, extraSpace);
            return sb.toString();
        }
    },
    RIGHT {
        @Override
        public String writeAligned(String text, int extraSpace) {
            invalidate(extraSpace);
            StringBuilder sb = new StringBuilder();
            // Append white spaces to left to align text to right.
            appendWhiteSpace(sb, extraSpace);
            // Append text on right side.
            sb.append(text);
            return sb.toString();
        }
    },
    CENTER {
        @Override
        public String writeAligned(String text, int extraSpace) {
            invalidate(extraSpace);
            StringBuilder sb = new StringBuilder();
            // Calculate count on left and right side
            // simply by dividing by 2.
            int leftSpaceCount = extraSpace / 2;
            int rightSpaceCount = leftSpaceCount;

            // If number is not even add extra space to left side.
            if (extraSpace % 2 == 1) {
                ++leftSpaceCount;
            }

            // Append white spaces to left side.
            appendWhiteSpace(sb, leftSpaceCount);
            // Append text to center.
            sb.append(text);
            // Append white spaces to right side.
            appendWhiteSpace(sb, rightSpaceCount);
            return sb.toString();
        }
    };

    public abstract String writeAligned(String text, int extraSpace);

    private static void invalidate(int extraSpace) {
        if (extraSpace < 0) {
            throw new IllegalArgumentException("Extra space length cannot be lesser than 0.");
        }
    }

    /**
     * Appends white spaces on <c>StringBuilder</c>.
     * Used to align text.
     *
     * @param extraSpace extra length filled with spaces.
     * @throws IllegalArgumentException if length is lesser than 0.
     */
    private static void appendWhiteSpace(StringBuilder sb, int extraSpace) {
        if (extraSpace < 0) {
            throw new IllegalArgumentException("Extra space length cannot be lesser than 0.");
        }

        while (extraSpace-- > 0) {
            sb.append(' ');
        }
    }
}
