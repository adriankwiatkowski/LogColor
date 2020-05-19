package com.company.consolecolors;

import java.util.concurrent.ThreadLocalRandom;

import static com.company.consolecolors.ConsoleColors.BACKGROUNDS;
import static com.company.consolecolors.ConsoleColors.FOREGROUNDS;

public class ConsoleColorPrinter {

    /**
     * Prints all backgrounds and foregrounds with text indexes.
     */
    public void printAllColorsIndexed() {
        int fgTextLength = ConsoleColorBuilder.getTextLength(FOREGROUNDS);
        int bgTextLength = ConsoleColorBuilder.getTextLength(BACKGROUNDS);
        ConsoleColorBuilder consoleColorBuilder = new ConsoleColorBuilder(fgTextLength, bgTextLength);

        for (int i = 0; i < FOREGROUNDS.length; ++i) {
            String fg = FOREGROUNDS[i];
            for (int j = 0; j < BACKGROUNDS.length; ++j) {
                String bg = BACKGROUNDS[j];
                consoleColorBuilder.appendFgBg(fg, bg, i, j);
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
        ConsoleColorBuilder consoleColorBuilder = new ConsoleColorBuilder(maxNumberLength, maxNumberLength);

        final int maxFgIndex = FOREGROUNDS.length;
        final int maxBgIndex = BACKGROUNDS.length;
        int fgIndex = 0;
        int bgIndex = 0;
        for (int i = 0; i <= randomNumber; ++i, ++bgIndex) {
            if (bgIndex >= maxBgIndex) {
                bgIndex = 0;
                if (++fgIndex >= maxFgIndex)
                    fgIndex = 0;
                consoleColorBuilder.appendAnsiReset_newLine();
            }
            String fg = FOREGROUNDS[fgIndex];
            String bg = BACKGROUNDS[bgIndex];
            consoleColorBuilder.appendFgBg(fg, bg, i, i);
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
                new ConsoleColorBuilder(maxTextLeft + 1, maxTextRight + 1);

        for (String fg : FOREGROUNDS) {
            for (String bg : BACKGROUNDS) {
                String left = generateRandomString(maxTextLeft);
                String right = generateRandomString(maxTextRight);
                consoleColorBuilder.appendFgBg(fg, bg, left, right);
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
