package com.company.consolecolors;

import com.company.consolecolors.models.TextAlignment;
import com.company.consolecolors.printers.ConsoleColorPrinter;

public class Main {

    private static final int SPACE_BETWEEN_TEXTS = 0b101;
    private static final int MAX_BOUND_INCREMENTAL = 0xFFF;
    private static final int MAX_BOUND_TEXT_LENGTH = 0xC;
    private static final int TEXT_COUNT = 0b100;

    public static void main(String[] args) {
        new Main().start();
    }

    private void start() {
        ConsoleColorPrinter consoleColorPrinter =
                new ConsoleColorPrinter(TextAlignment.CENTER, SPACE_BETWEEN_TEXTS);
        consoleColorPrinter.printAllColorsIndexed();
        consoleColorPrinter.printIncrementalNumbers(MAX_BOUND_INCREMENTAL);
        consoleColorPrinter.printAllColorsText(MAX_BOUND_TEXT_LENGTH, TEXT_COUNT);
    }
}