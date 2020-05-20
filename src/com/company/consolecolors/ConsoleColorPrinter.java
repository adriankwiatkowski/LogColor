package com.company.consolecolors;

import java.util.concurrent.ThreadLocalRandom;

public class ConsoleColorPrinter {

    private TextAlignment mTextAlignment;
    private int mSpaceLength;

    public ConsoleColorPrinter(TextAlignment textAlignment, int spaceLength) {
        this.mTextAlignment = textAlignment;
        this.mSpaceLength = spaceLength;
    }

    /**
     * Prints all backgrounds and foregrounds with text indexes.
     */
    public void printAllColorsIndexed() {
        int fgTextLength = ConsoleColorBuilder.getTextLength(AnsiColor.FOREGROUNDS);
        int bgTextLength = ConsoleColorBuilder.getTextLength(AnsiColor.BACKGROUNDS);
        ConsoleColorBuilder consoleColorBuilder =
                new ConsoleColorBuilder(
                        fgTextLength, bgTextLength, mSpaceLength, mSpaceLength);

        for (int i = 0; i < AnsiColor.FOREGROUNDS.length; ++i) {
            AnsiColor fg = AnsiColor.FOREGROUNDS[i];
            for (int j = 0; j < AnsiColor.BACKGROUNDS.length; ++j) {
                AnsiColor bg = AnsiColor.BACKGROUNDS[j];
                consoleColorBuilder.appendFgBg(fg, bg, mTextAlignment, mTextAlignment, i, j);
            }
            consoleColorBuilder.appendAnsiReset_newLine();
        }

        System.out.println(consoleColorBuilder.getStringText_clear());
    }

    /**
     * Prints colored numbers from 0 to random generated numbers.
     */
    public void printIncrementalNumbers(int maxBoundIncremental) {
        int randomNumber = ThreadLocalRandom.current().nextInt(maxBoundIncremental);
        int maxNumberLength = ConsoleColorBuilder.getTextLength(randomNumber);
        ConsoleColorBuilder consoleColorBuilder =
                new ConsoleColorBuilder(
                        maxNumberLength, maxNumberLength, mSpaceLength, mSpaceLength);

        final int maxFgIndex = AnsiColor.FOREGROUNDS.length;
        final int maxBgIndex = AnsiColor.BACKGROUNDS.length;
        int fgIndex = 0;
        int bgIndex = 0;
        for (int i = 0; i <= randomNumber; ++i, ++bgIndex) {
            if (bgIndex >= maxBgIndex) {
                bgIndex = 0;
                if (++fgIndex >= maxFgIndex)
                    fgIndex = 0;
                consoleColorBuilder.appendAnsiReset_newLine();
            }
            AnsiColor fg = AnsiColor.FOREGROUNDS[fgIndex];
            AnsiColor bg = AnsiColor.BACKGROUNDS[bgIndex];
            consoleColorBuilder.appendFgBg(fg, bg, mTextAlignment, mTextAlignment, i, i);
        }

        if (bgIndex < maxBgIndex) {
            consoleColorBuilder.appendAnsiReset_newLine();
        }

        System.out.println(consoleColorBuilder.getStringText_clear());
    }

    /**
     * Prints random generated colored strings.
     * @param maxBoundTextLength max letters in text. Has to be at lease 1.
     * @throws IllegalArgumentException if maxBoundTextLength is < 1.
     */
    public void printAllColorsText(int maxBoundTextLength) {
        if (maxBoundTextLength < 1)
            throw new IllegalArgumentException("max bound text length has to be at least 1.");

        int maxTextLeft = ThreadLocalRandom.current().nextInt(1, maxBoundTextLength);
        int maxTextRight = ThreadLocalRandom.current().nextInt(1, maxBoundTextLength);
        ConsoleColorBuilder consoleColorBuilder =
                new ConsoleColorBuilder(
                        maxTextLeft, maxTextRight, mSpaceLength, mSpaceLength);

        for (AnsiColor fg : AnsiColor.FOREGROUNDS) {
            for (AnsiColor bg : AnsiColor.BACKGROUNDS) {
                String left = generateRandomString(maxTextLeft);
                String right = generateRandomString(maxTextRight);
                consoleColorBuilder.appendFgBg(fg, bg, mTextAlignment, mTextAlignment, left, right);
            }
            consoleColorBuilder.appendAnsiReset_newLine();
        }

        System.out.println(consoleColorBuilder.getStringText_clear());
    }

    /**
     * Generate random string.
     * @return Returns random string.
     */
    public String generateRandomString(int length) {
        return generateRandomString(0x41, 0x5A + 1, length);
    }

    /**
     * Generate random string.
     * @param minAscii inclusive int min 0.
     * @param maxAscii exclusive int max 127.
     * @return Returns random string.
     * @throws IllegalArgumentException if min or max is < 0 || > 127.
     */
    public String generateRandomString(int minAscii, int maxAscii, int length) {
        if (minAscii < 0 || minAscii > 127)
            throw new IllegalArgumentException(
                    "min ascii must greater than 0 and lesser than 127 and was: "
                            + minAscii
                            + ".");
        if (maxAscii < 0 || maxAscii > 127)
            throw new IllegalArgumentException(
                    "max Ascii must greater than 0 and lesser than 127 and was: "
                            + minAscii
                            + ".");

        StringBuilder sb = new StringBuilder(length);

        ThreadLocalRandom.current()
                .ints(minAscii, maxAscii)
                .limit(length)
                .forEach(i -> sb.append((char) i));

        return sb.toString();
    }
}
