package com.example.logcolor;

import com.example.logcolor.utils.AppExecutors;
import com.example.logcolor.utils.DebugTextUtils;
import com.example.logcolor.utils.RandomUtils;
import com.example.logcolor.utils.WindowLookUtils;
import com.example.printers.PrintableManager;
import com.example.printers.models.PrintableType;

public class Main {

    public static void main(String[] args) {
        new Main().start();
    }

    private void start() {
        AppExecutors.getInstance().mainThread().execute(() -> {
            WindowLookUtils.setLookAndFeel();

            PrintableManager printableManager = PrintableManager.getInstance();
            printableManager.setDayTheme();
            printableManager.setPrintableConsole(PrintableType.WINDOW);
            DebugTextUtils.printSampleText();
            DebugTextUtils.printAllLevelLogsTest();
            RandomUtils.printRandomDebugInfo();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            AppExecutors.getInstance().shutdownExecutors();
        });
    }
}