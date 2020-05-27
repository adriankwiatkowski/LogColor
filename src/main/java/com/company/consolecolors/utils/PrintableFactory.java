package com.company.consolecolors.utils;

import com.company.consolecolors.interfaces.Printable;
import com.company.consolecolors.utils.printers.PrintableConsole;
import com.company.consolecolors.utils.printers.PrintableWindow;

public class PrintableFactory {

    public enum PrintableType {
        CONSOLE, WINDOW
    }

    public static Printable createPrintable(PrintableType printableType,
                                            boolean nightTheme) {
        switch (printableType) {
            case CONSOLE:
                return createPrintableConsole();
            case WINDOW:
                return createPrintableWindow(nightTheme);
            default:
                throw new IllegalArgumentException("Unknown printable type: " + printableType);
        }
    }

    public static Printable setPrintable(PrintableType printableType,
                                         Printable printable,
                                         boolean nightTheme) {
        switch (printableType) {
            case CONSOLE:
                if (printable instanceof PrintableConsole) {
                    if (nightTheme) printable.setNightTheme();
                    else printable.setDayTheme();
                    return printable;
                }
                return createPrintable(printableType, nightTheme);
            case WINDOW:
                if (printable instanceof PrintableWindow) {
                    if (nightTheme) printable.setNightTheme();
                    else printable.setDayTheme();
                    return printable;
                }
                return createPrintable(printableType, nightTheme);
            default:
                throw new IllegalArgumentException("Unknown printable type: " + printableType);
        }
    }

    private static Printable createPrintableConsole() {
        return new PrintableConsole();
    }

    private static Printable createPrintableWindow(boolean nightTheme) {
        return new PrintableWindow.Builder()
                .setNightTheme(nightTheme)
                .build();
    }
}
