package com.example.logcolor;

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