package com.example.logcolor.printers;

import com.example.logcolor.printers.models.PrintableType;
import com.example.logcolor.printers.printables.Printable;
import com.example.logcolor.printers.printables.PrintableConsole;
import com.example.logcolor.printers.printables.PrintableWindow;

import java.io.OutputStream;

class PrintableFactory {

    private static final OutputStream CONSOLE_OUTPUT_STREAM = System.out;

    static Printable createPrintable(PrintableType printableType, boolean nightTheme) {
        switch (printableType) {
            case CONSOLE:
                return createPrintableConsole();
            case WINDOW:
                return createPrintableWindow(nightTheme);
            default:
                throw new IllegalArgumentException("Unknown printable type: " + printableType);
        }
    }

    static Printable setPrintable(PrintableType printableType,
                                  Printable printable,
                                  boolean nightTheme) {
        switch (printableType) {
            case CONSOLE:
                if (printable instanceof PrintableConsole) {
                    if (nightTheme) {
                        printable.setNightTheme();
                    } else {
                        printable.setDayTheme();
                    }
                    return printable;
                }
                return createPrintable(printableType, nightTheme);
            case WINDOW:
                if (printable instanceof PrintableWindow) {
                    if (nightTheme) {
                        printable.setNightTheme();
                    } else {
                        printable.setDayTheme();
                    }
                    return printable;
                }
                return createPrintable(printableType, nightTheme);
            default:
                throw new IllegalArgumentException("Unknown printable type: " + printableType);
        }
    }

    private static Printable createPrintableConsole() {
        return new PrintableConsole(CONSOLE_OUTPUT_STREAM, true);
    }

    private static Printable createPrintableWindow(boolean nightTheme) {
        return new PrintableWindow(nightTheme);
    }
}
