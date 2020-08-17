package com.example.logcolor;

import com.example.logcolor.colorbuilder.utils.AnsiColor;
import com.example.logcolor.colorbuilder.text.TextAlignment;
import com.example.logcolor.colorbuilder.text.TextAttribute;
import com.example.logcolor.colorbuilder.text.TextStyle;
import com.example.logcolor.log.LogManager;
import com.example.logcolor.printers.PrintableManager;
import com.example.logcolor.printers.Printer;
import com.example.logcolor.printers.models.PrintableType;
import com.example.logcolor.utils.AppExecutors;
import com.example.logcolor.utils.DebugTextUtils;
import com.example.logcolor.utils.WindowLookUtils;

import java.util.EnumSet;

public class Main {

    public static void main(String[] args) {
        new Main().start();
    }

    private void start() {
        AppExecutors.getInstance().mainThread().execute(() -> {
            WindowLookUtils.setLookAndFeel();

            PrintableManager.getInstance().setDayTheme();
            PrintableManager.getInstance().setPrintable(PrintableType.WINDOW);
            PrintableManager.getInstance()
                            .setDefaultFormat(AnsiColor.ANSI_BRIGHT_GREEN.getColor(),
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
//            PrintableManager.getInstance().setDefaultFormat(textAttribute);
            LogManager.getInstance().setShowLogLevel(false);
            LogManager.getInstance().setShowDate(false);
            //LogManager.getInstance().setShowTag(false);
            System.setOut(PrintableManager.getInstance().getPrintable());

            System.out.println();
            System.out.print(String.format("Random %s\nqwe", "Text"));
            System.out.printf("Random %s\nqwe", "Text");

            Printer.print("Some text.\n", textAttribute);

            DebugTextUtils.printAllLevelLogsTest();
            DebugTextUtils.printRandomDebugInfo();
            DebugTextUtils.printSampleText();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(10035600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            PrintableManager.getInstance().shutdownThreads();
            AppExecutors.getInstance().shutdownExecutors();
        });
    }
}