package com.adriankwiatkowski.logcolor;

import com.adriankwiatkowski.logcolor.colorbuilder.text.TextStyle;
import com.adriankwiatkowski.logcolor.printers.models.PrintableType;
import com.adriankwiatkowski.logcolor.utils.WindowLookUtils;
import com.adriankwiatkowski.logcolor.colorbuilder.text.TextAlignment;
import com.adriankwiatkowski.logcolor.colorbuilder.text.TextAttribute;
import com.adriankwiatkowski.logcolor.colorbuilder.utils.AnsiColor;
import com.adriankwiatkowski.logcolor.log.Log;
import com.adriankwiatkowski.logcolor.log.LogManager;
import com.adriankwiatkowski.logcolor.printers.PrintableManager;
import com.adriankwiatkowski.logcolor.printers.Printer;
import com.adriankwiatkowski.logcolor.utils.DebugTextUtils;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        new Main().start();
    }

    private void start() {
        WindowLookUtils.setLookAndFeel();

        int streamSize = 10000000;
        System.out.println(ThreadLocalRandom.current()
                                            .ints(streamSize)
                                            .sorted()
                                            .average()
                                            .getAsDouble());

        PrintableManager.getInstance().setNightTheme();
        PrintableManager.getInstance().setPrintable(PrintableType.WINDOW);
        PrintableManager.getInstance().setDefaultFormat(AnsiColor.ANSI_BRIGHT_GREEN.getColor(),
                                                        AnsiColor.ANSI_BG_BLACK.getColor());
        TextAttribute textAttribute =
                new TextAttribute.Builder().setForeground(AnsiColor.ANSI_BRIGHT_GREEN.getColor())
                                           .setBackground(AnsiColor.ANSI_BG_BLACK.getColor())
                                           .setTextAlignment(TextAlignment.CENTER)
                                           .setExtraSpace(26)
                                           .setTextStyle(EnumSet.of(TextStyle.BOLD,
                                                                    TextStyle.ITALIC,
                                                                    TextStyle.UNDERLINE))
                                           .build();
        PrintableManager.getInstance().setDefaultFormat(textAttribute);

//            PrintableManager.getInstance().setPrintable(new LeetPrintableConsole(System.out, true));
//            PrintableManager.getInstance().setPrintable(new LeetPrintableWindow(PrintableManager.getInstance().isNightTheme()));

        LogManager.getInstance().setDatePattern("yyyy.ss.MM mm::dd::ss");

//            LogManager.getInstance().setShowLogLevel(false);
//            LogManager.getInstance().setShowDate(false);
//            LogManager.getInstance().setShowTag(false);
        LogManager.getInstance().setColorTagBackgroundDay(AnsiColor.ANSI_BG_BLACK.getColor());
        LogManager.getInstance().setColorTagForegroundDay(AnsiColor.ANSI_BRIGHT_GREEN.getColor());
        Log.d("a'gwo4");
        Log.d("a'gwo4");
        System.setOut(PrintableManager.getInstance().getPrintable());

        System.out.println();
        System.out.print(String.format("Random %s\nqwe", "Text"));
        System.out.printf("Random %s\nqwe", "Text");

        Printer.print("Some text.\n", textAttribute);

        DebugTextUtils.printAllLevelLogsTest();
        DebugTextUtils.printRandomDebugInfo();
        DebugTextUtils.printSampleText();

        PrintableManager.getInstance().getPrintable().close();
    }
}