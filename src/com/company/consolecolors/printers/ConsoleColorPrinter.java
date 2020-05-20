package com.company.consolecolors.printers;

import com.company.consolecolors.builders.ColorBuilder;
import com.company.consolecolors.models.AnsiColor;
import com.company.consolecolors.models.TextAlignment;
import com.company.consolecolors.models.TextAttribute;
import com.company.consolecolors.utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class ConsoleColorPrinter {

    private static final int MIN_TEXT_LENGTH = 1;

    private TextAlignment mTextAlignment;
    private int mSpaceLength;

    public ConsoleColorPrinter(TextAlignment textAlignment, int spaceLength) {
        if (textAlignment == null)
            throw new IllegalArgumentException("TextAlignment cannot be null.");

        this.mTextAlignment = textAlignment;
        this.mSpaceLength = spaceLength;
    }

    public void printColorDebugInfo() {
        int maxTextLength = 0;
        for (AnsiColor fg : AnsiColor.FOREGROUNDS) {
            for (AnsiColor bg : AnsiColor.BACKGROUNDS) {
                maxTextLength = Math.max(
                        Math.max(
                                maxTextLength,
                                fg.toString().length()),
                        bg.toString().length());
            }
        }

        TextAttribute textAttribute = new TextAttribute(mTextAlignment, maxTextLength, 0);
        ColorBuilder colorBuilder = new ColorBuilder.Builder(textAttribute, 2).build();
        for (AnsiColor fg : AnsiColor.FOREGROUNDS) {
            for (AnsiColor bg : AnsiColor.BACKGROUNDS) {
                colorBuilder.appendColoredText(fg, bg, fg.toString(), bg.toString());
            }
            colorBuilder.appendAnsiReset_newLine();
        }

        System.out.println(colorBuilder.getStringText_clear());
    }

    /**
     * Prints all backgrounds and foregrounds with text indexes.
     */
    public void printAllColorsIndexed() {
        int fgTextLength = ColorBuilder.getTextLength(AnsiColor.FOREGROUNDS);
        int bgTextLength = ColorBuilder.getTextLength(AnsiColor.BACKGROUNDS);

        int maxTextLength =
                Math.max(
                        ColorBuilder.getTextLength(fgTextLength),
                        ColorBuilder.getTextLength(bgTextLength));
        TextAttribute textAttribute = new TextAttribute(mTextAlignment, maxTextLength, mSpaceLength);
        int textAttributeDuplicateCount = 2;
        ColorBuilder colorBuilder =
                new ColorBuilder.Builder(textAttribute, textAttributeDuplicateCount)
                        .build();

        for (int i = 0; i < AnsiColor.FOREGROUNDS.length; ++i) {
            AnsiColor fg = AnsiColor.FOREGROUNDS[i];
            for (int j = 0; j < AnsiColor.BACKGROUNDS.length; ++j) {
                AnsiColor bg = AnsiColor.BACKGROUNDS[j];
                colorBuilder.appendColoredText(fg, bg, String.valueOf(i), String.valueOf(j));
            }
            colorBuilder.appendAnsiReset_newLine();
        }

        System.out.println(colorBuilder.getStringText_clear());
    }

    /**
     * Prints colored numbers from 0 to random generated numbers.
     *
     * @param maxBoundIncremental max random number to print - 1.
     */
    public void printIncrementalNumbers(int maxBoundIncremental) {
        int randomNumber = RandomUtils.generateRandomNumber(maxBoundIncremental);
        int maxNumberLength = ColorBuilder.getTextLength(randomNumber);

        TextAttribute textAttribute = new TextAttribute(mTextAlignment, maxNumberLength, mSpaceLength);
        ColorBuilder colorBuilder = new ColorBuilder.Builder(textAttribute).build();

        final int maxFgIndex = AnsiColor.FOREGROUNDS.length;
        final int maxBgIndex = AnsiColor.BACKGROUNDS.length;
        int fgIndex = 0;
        int bgIndex = 0;
        for (int i = 0; i <= randomNumber; ++i, ++bgIndex) {
            if (bgIndex >= maxBgIndex) {
                bgIndex = 0;
                if (++fgIndex >= maxFgIndex)
                    fgIndex = 0;
                colorBuilder.appendAnsiReset_newLine();
            }
            AnsiColor fg = AnsiColor.FOREGROUNDS[fgIndex];
            AnsiColor bg = AnsiColor.BACKGROUNDS[bgIndex];
            colorBuilder.appendColoredText(fg, bg, String.valueOf(i));
        }

        if (bgIndex <= maxBgIndex) {
            colorBuilder.appendAnsiReset_newLine();
        }

        System.out.println(colorBuilder.getStringText_clear());
    }

    /**
     * Prints random generated colored strings.
     *
     * @param maxBoundTextLength max letters in text. Has to be at least 1.
     * @param textCountInColumn          describes how many random strings will be printed on one color.
     * @throws IllegalArgumentException if maxBoundTextLength is < 1.
     */
    public void printAllColorsText(int maxBoundTextLength, int textCountInColumn) {
        if (maxBoundTextLength < 1)
            throw new IllegalArgumentException("Max bound text length has to be at least 1.");
        if (textCountInColumn <= 0)
            throw new IllegalArgumentException("Text count mu be grater than 0");

        int columnCount = AnsiColor.FOREGROUNDS.length;

        List<List<TextAttribute>> randomTextAttributesList = new ArrayList<>();
        while (columnCount-- > 0) {
            List<TextAttribute> textAttributeList = new ArrayList<>();
            for (int i = 0; i < textCountInColumn; ++i) {
                int maxTextLength = RandomUtils.generateRandomNumber(MIN_TEXT_LENGTH, maxBoundTextLength);
                TextAttribute textAttribute1 = new TextAttribute(mTextAlignment, maxTextLength, mSpaceLength);
                textAttributeList.add(textAttribute1);
            }
            randomTextAttributesList.add(textAttributeList);
        }

        ColorBuilder colorBuilder = new ColorBuilder.Builder(randomTextAttributesList.get(0)).build();

        randomTextAttributesList = new ArrayList<>(randomTextAttributesList);

        int i = 0;
        for (AnsiColor fg : AnsiColor.FOREGROUNDS) {

            for (AnsiColor bg : AnsiColor.BACKGROUNDS) {

                List<String> textList = new ArrayList<>();
                for (TextAttribute textAttribute : randomTextAttributesList.get(i)) {
                    textList.add(RandomUtils.generateRandomAsciiString(textAttribute.getTextLength()));
                }

                colorBuilder.appendColoredText(fg, bg, textList);

                i = (i + 1) % AnsiColor.BACKGROUNDS.length;
                List<TextAttribute> textAttributeList = randomTextAttributesList.get(i);
                for (int j = 0; j < textAttributeList.size(); ++j) {
                    colorBuilder.setTextAttribute(j, textAttributeList.get(j));
                }
            }

            colorBuilder.appendAnsiReset_newLine();
        }

        System.out.println(colorBuilder.getStringText_clear());
    }

}
