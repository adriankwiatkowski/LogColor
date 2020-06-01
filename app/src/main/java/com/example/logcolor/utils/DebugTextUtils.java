package com.example.logcolor.utils;

import com.example.color.models.AnsiColor;
import com.example.color.models.TextAlignment;
import com.example.color.models.TextAttribute;
import com.example.colorbuilder.builders.AlignedColorBuilder;
import com.example.color.utils.TextUtils;
import com.example.log.Log;
import com.example.printers.PrintableManager;
import com.example.printers.Printer;

import java.util.ArrayList;
import java.util.List;

public class DebugTextUtils {

    public static void printSampleText() {
        AnsiColor fg = AnsiColor.ANSI_BRIGHT_BLUE;
        AnsiColor bg = AnsiColor.ANSI_BRIGHT_BG_WHITE;
        TextAlignment center = TextAlignment.CENTER;
        int space = 10;
        String msg = "Message.";
        Printer.println(msg);
        Printer.println(fg, msg);
        Printer.println(fg, bg, msg);
        Printer.println(center, space, msg);
        Printer.println(fg, center, space, msg);
        Printer.println(fg, bg, center, space, msg);
        Printer.print(msg);
        Printer.print(fg, msg);
        Printer.print(fg, bg, msg);
        Printer.print(center, space, msg);
        Printer.print(fg, center, space, msg);
        Printer.print(fg, bg, center, space, msg);

        Printer.println();
        Printer.println();
    }

    public static void printAllLevelLogsTest() {
        Log.v("Verbose tag", "Useless verbose message.");
        Log.i("Info tag", "Informative message.");
        Log.d("Debug tag", "Something not working here.");
        Log.w("Warning tag", "Warning, take care!");
        Log.e("Error tag", "Behold almighty error...");
        Log.w("Warning tag that will exceed max character limit.",
                "Friendly warning message.");
    }

    public static void printColorDebugInfo(TextAlignment textAlignment, int extraSpace) {
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
                new TextAttribute(textAlignment, maxTextLength + extraSpace);
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

        PrintableManager.getInstance().logThread(() ->
                System.out.println(alignedColorBuilder.getText_Flush()));
    }

    public static void printAllColorsIndexed(TextAlignment textAlignment, int extraSpace) {
        int maxTextLength = Math.max(
                TextUtils.getTextLength(AnsiColor.FOREGROUNDS),
                TextUtils.getTextLength(AnsiColor.BACKGROUNDS));

        TextAttribute textAttribute =
                new TextAttribute(textAlignment, maxTextLength + extraSpace);

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

        PrintableManager.getInstance().logThread(() ->
                System.out.println(alignedColorBuilder.getText_Flush()));
    }

    public static void printIncrementalNumbers(TextAlignment textAlignment,
                                               int extraSpace,
                                               int maxBoundIncremental) {
        int randomNumber = RandomUtils.generateRandomNumber(maxBoundIncremental);
        int maxNumberLength = TextUtils.getTextLength(randomNumber);

        TextAttribute textAttribute =
                new TextAttribute(textAlignment, maxNumberLength + extraSpace);
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

        PrintableManager.getInstance().logThread(() ->
                System.out.println(alignedColorBuilder.getText_Flush()));
    }

    public static void printAllColorsText(TextAlignment textAlignment,
                                          int extraSpace,
                                          int maxBoundTextLength,
                                          int textCountInColumn) {
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
                        RandomUtils.generateRandomNumber(1, maxBoundTextLength);
                TextAttribute textAttribute1 =
                        new TextAttribute(textAlignment, maxTextLength + extraSpace);
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

        PrintableManager.getInstance().logThread(() ->
                System.out.println(alignedColorBuilder.getText_Flush()));
    }
}
