package com.company;

import com.company.consolecolors.ConsoleColorPrinter;

public class Main {

    private static final int MAX_BOUND_INCREMENTAL = 0xFF;
    private static final int MAX_BOUND_TEXT_LENGTH = 0xC;

    public static void main(String[] args) {
        new Main().start();
    }

    private void start() {
        ConsoleColorPrinter consoleColorPrinter = new ConsoleColorPrinter();
        consoleColorPrinter.printAllColorsIndexed();
        consoleColorPrinter.printIncrementalNumbers(MAX_BOUND_INCREMENTAL);
        consoleColorPrinter.printAllColorsText(MAX_BOUND_TEXT_LENGTH);
    }
}