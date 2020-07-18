package com.example.logcolor;

import com.example.logcolor.color.models.TextAlignment;
import com.example.logcolor.colorbuilder.interfaces.ColorBuilder;
import com.example.logcolor.log.Log;
import com.example.logcolor.log.LogManager;
import com.example.logcolor.log.models.LogLevel;
import com.example.logcolor.printers.interfaces.Printable;
import com.example.logcolor.utils.AppExecutors;
import com.example.logcolor.utils.DebugTextUtils;
import com.example.logcolor.utils.RandomUtils;
import com.example.logcolor.utils.WindowLookUtils;
import com.example.logcolor.printers.PrintableManager;
import com.example.logcolor.printers.models.PrintableType;

public class Main {

    public static void main(String[] args) {
        new Main().start();
    }

    private void start() {
        /*
        AppExecutors.getInstance().mainThread().execute(() -> {
            WindowLookUtils.setLookAndFeel();

            PrintableManager printableManager = PrintableManager.getInstance();
            printableManager.setNightTheme();
            printableManager.setPrintable(PrintableType.WINDOW);
            DebugTextUtils.printSampleText();
            DebugTextUtils.printAllLevelLogsTest();
            DebugTextUtils.printAllColorsIndexed(TextAlignment.CENTER, 4);
            RandomUtils.printRandomDebugInfo();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            PrintableManager.getInstance().shutdownThreads();
            AppExecutors.getInstance().shutdownExecutors();
        });
         */
    }
}