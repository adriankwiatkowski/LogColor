package com.company.consolecolors.utils.printers;

import com.company.consolecolors.builders.AlignedColorBuilder;
import com.company.consolecolors.models.AnsiColor;
import com.company.consolecolors.models.TextAlignment;
import com.company.consolecolors.models.TextAttribute;
import com.company.consolecolors.utils.AppExecutors;
import com.company.consolecolors.utils.RandomUtils;
import com.company.consolecolors.utils.TextLengthUtils;

import java.util.ArrayList;
import java.util.List;

public class ConsoleColorPrinter {

    private static final int MIN_TEXT_LENGTH = 1;

    private TextAlignment mTextAlignment;
    private int mExtraSpace;

    public ConsoleColorPrinter(TextAlignment textAlignment, int extraSpace) {
        if (textAlignment == null)
            throw new IllegalArgumentException("TextAlignment cannot be null.");

        this.mTextAlignment = textAlignment;
        this.mExtraSpace = extraSpace;
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

        TextAttribute textAttribute =
                new TextAttribute(mTextAlignment, maxTextLength + mExtraSpace);
        int textAttributeDuplicateCount = 2;

        AlignedColorBuilder alignedColorBuilder = new AlignedColorBuilder
                .Builder(textAttribute, textAttributeDuplicateCount)
                .build();

        for (AnsiColor fg : AnsiColor.FOREGROUNDS) {
            for (AnsiColor bg : AnsiColor.BACKGROUNDS) {
                alignedColorBuilder.appendTextColor(fg, bg, fg.toString(), bg.toString());
            }
            alignedColorBuilder.appendColorReset_NewLine();
        }

        AppExecutors.getInstance().consoleThread().execute(() ->
                System.out.println(alignedColorBuilder.getText_Flush()));
    }

    /**
     * Prints all backgrounds and foregrounds with text indexes.
     */
    public void printAllColorsIndexed() {
        int maxTextLength = Math.max(
                TextLengthUtils.getTextLength(AnsiColor.FOREGROUNDS),
                TextLengthUtils.getTextLength(AnsiColor.BACKGROUNDS));

        TextAttribute textAttribute =
                new TextAttribute(mTextAlignment, maxTextLength + mExtraSpace);

        int textAttributeDuplicateCount = 2;

        AlignedColorBuilder alignedColorBuilder = new AlignedColorBuilder
                .Builder(textAttribute, textAttributeDuplicateCount)
                .build();

        for (int i = 0; i < AnsiColor.FOREGROUNDS.length; ++i) {
            AnsiColor fg = AnsiColor.FOREGROUNDS[i];
            for (int j = 0; j < AnsiColor.BACKGROUNDS.length; ++j) {
                AnsiColor bg = AnsiColor.BACKGROUNDS[j];
                alignedColorBuilder.appendTextColor(fg, bg, String.valueOf(i), String.valueOf(j));
            }
            alignedColorBuilder.appendColorReset_NewLine();
        }

        AppExecutors.getInstance().consoleThread().execute(() ->
                System.out.println(alignedColorBuilder.getText_Flush()));
    }

    /**
     * Prints colored numbers from 0 to random generated numbers.
     *
     * @param maxBoundIncremental max random number to print - 1.
     */
    public void printIncrementalNumbers(int maxBoundIncremental) {
        int randomNumber = RandomUtils.generateRandomNumber(maxBoundIncremental);
        int maxNumberLength = TextLengthUtils.getTextLength(randomNumber);

        TextAttribute textAttribute =
                new TextAttribute(mTextAlignment, maxNumberLength + mExtraSpace);
        AlignedColorBuilder alignedColorBuilder = new AlignedColorBuilder
                .Builder(textAttribute)
                .build();

        final int maxFgIndex = AnsiColor.FOREGROUNDS.length;
        final int maxBgIndex = AnsiColor.BACKGROUNDS.length;
        int fgIndex = 0;
        int bgIndex = 0;
        for (int i = 0; i <= randomNumber; ++i, ++bgIndex) {
            if (bgIndex >= maxBgIndex) {
                bgIndex = 0;
                if (++fgIndex >= maxFgIndex)
                    fgIndex = 0;
                alignedColorBuilder.appendColorReset_NewLine();
            }
            AnsiColor fg = AnsiColor.FOREGROUNDS[fgIndex];
            AnsiColor bg = AnsiColor.BACKGROUNDS[bgIndex];
            alignedColorBuilder.appendTextColor(fg, bg, String.valueOf(i));
        }

        if (bgIndex <= maxBgIndex) {
            alignedColorBuilder.appendColorReset_NewLine();
        }

        AppExecutors.getInstance().consoleThread().execute(() ->
                System.out.println(alignedColorBuilder.getText_Flush()));
    }

    /**
     * Prints random generated colored strings.
     *
     * @param maxBoundTextLength max letters in text. Has to be at least 1.
     * @param textCountInColumn  describes how many random strings will be printed on one color.
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
                int maxTextLength =
                        RandomUtils.generateRandomNumber(MIN_TEXT_LENGTH, maxBoundTextLength);
                TextAttribute textAttribute1 =
                        new TextAttribute(mTextAlignment, maxTextLength + mExtraSpace);
                textAttributeList.add(textAttribute1);
            }
            randomTextAttributesList.add(textAttributeList);
        }

        AlignedColorBuilder alignedColorBuilder = new AlignedColorBuilder
                .Builder(randomTextAttributesList.get(0))
                .build();

        randomTextAttributesList = new ArrayList<>(randomTextAttributesList);

        int i = 0;
        for (AnsiColor fg : AnsiColor.FOREGROUNDS) {

            for (AnsiColor bg : AnsiColor.BACKGROUNDS) {

                List<String> textList = new ArrayList<>();
                for (TextAttribute textAttribute : randomTextAttributesList.get(i)) {
                    textList.add(RandomUtils
                            .generateRandomAsciiString(textAttribute.getTotalLength()));
                }

                alignedColorBuilder.appendTextColor(fg, bg, textList);

                i = (i + 1) % AnsiColor.BACKGROUNDS.length;
                List<TextAttribute> textAttributeList = randomTextAttributesList.get(i);
                for (int j = 0; j < textAttributeList.size(); ++j) {
                    alignedColorBuilder.setTextAttribute(j, textAttributeList.get(j));
                }
            }

            alignedColorBuilder.appendColorReset_NewLine();
        }

        AppExecutors.getInstance().consoleThread().execute(() ->
                System.out.println(alignedColorBuilder.getText_Flush()));
    }

}
