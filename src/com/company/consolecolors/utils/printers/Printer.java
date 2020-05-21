package com.company.consolecolors.utils.printers;

import com.company.consolecolors.builders.SimpleColorBuilder;
import com.company.consolecolors.models.AnsiColor;
import com.company.consolecolors.utils.AppExecutors;

public class Printer {

    private static final boolean NEW_LINE = true;
    private static final boolean NO_LINE = false;
    private static final String EMPTY_MSG = "Message is empty.";

    private Printer() {
    }

    public static void println() {
        println("");
    }

    public static void println(String msg) {
        print(msg, NEW_LINE);
    }

    public static void println(AnsiColor color, String msg) {
        print(color, msg, NEW_LINE);
    }

    public static void println(AnsiColor fg, AnsiColor bg, String msg) {
        print(fg, bg, msg, NEW_LINE);
    }

    public static void print(String msg) {
        print(msg, NO_LINE);
    }

    public static void print(AnsiColor color, String msg) {
        print(color, msg, NO_LINE);
    }

    public static void print(AnsiColor fg, AnsiColor bg, String msg) {
        print(fg, bg, msg, NO_LINE);
    }

    private static void print(String msg, boolean newLine) {
        print(null, msg, newLine);
    }

    private static void print(AnsiColor color, String msg, boolean newLine) {
        print(null, color, msg, newLine);
    }

    private static void print(AnsiColor fg, AnsiColor bg, String msg, boolean newLine) {
        addPrintToQueue(fg, bg, msg, newLine);
    }

    private static void addPrintToQueue(AnsiColor fg, AnsiColor bg,
                                        String msg, boolean newLine) {
        if (msg == null)
            throw new IllegalArgumentException("Message cannot be null.");

        AppExecutors appExecutors = AppExecutors.getInstance();
        if (!appExecutors.isConsoleExecutorShutdown()) {
            AppExecutors.getInstance().consoleThread().execute(() ->
                    printScheduled(fg, bg, msg, newLine));
        }
    }

    private static void printScheduled(AnsiColor fg, AnsiColor bg,
                                       String text, boolean newLine) {
        SimpleColorBuilder colorBuilder = new SimpleColorBuilder.Builder().build();

        if (fg != null)
            colorBuilder.appendColor(fg);
        if (bg != null)
            colorBuilder.appendColor(bg);

        if (text.isEmpty() && !newLine)
            text = EMPTY_MSG;

        colorBuilder.append(text);
        colorBuilder.appendColorReset();

        if (newLine)
            colorBuilder.appendNewLine();

        System.out.print(colorBuilder.getText_Flush());
    }
}
