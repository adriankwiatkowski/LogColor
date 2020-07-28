package com.example.logcolor.printers;

import com.example.logcolor.color.models.AnsiColor;
import com.example.logcolor.color.models.TextAlignment;
import com.example.logcolor.color.utils.TextUtils;
import com.example.logcolor.colorbuilder.builders.SimpleColorBuilder;
import com.example.logcolor.printers.printables.Printable;

public class Printer {

    private static final boolean NEW_LINE = true;
    private static final boolean NO_LINE = false;
    private static final String EMPTY_MSG = "Message is empty.";

    private static final TextAlignment DEFAULT_TEXT_ALIGNMENT = TextAlignment.NONE;
    private static final int DEFAULT_EXTRA_SPACE = 0;

    private Printer() {
    }

    public static void printForceOnNewLine(String msg) {
        addPrintToQueue(null, null, DEFAULT_TEXT_ALIGNMENT, DEFAULT_EXTRA_SPACE, msg, false, true);
    }

    public static void println() {
        println("");
    }

    public static void println(String msg) {
        print(msg, NEW_LINE);
    }

    public static void println(TextAlignment textAlignment, int extraSpace, String msg) {
        print(textAlignment, extraSpace, msg, NEW_LINE);
    }

    public static void println(AnsiColor color, String msg) {
        print(color, msg, NEW_LINE);
    }

    public static void println(AnsiColor color,
                               TextAlignment textAlignment,
                               int extraSpace,
                               String msg) {
        print(color, textAlignment, extraSpace, msg, NEW_LINE);
    }

    public static void println(AnsiColor fg, AnsiColor bg, String msg) {
        print(fg, bg, msg, NEW_LINE);
    }

    public static void println(AnsiColor fg,
                               AnsiColor bg,
                               TextAlignment textAlignment,
                               int extraSpace,
                               String msg) {
        print(fg, bg, textAlignment, extraSpace, msg, NEW_LINE);
    }

    public static void print(String msg) {
        print(msg, NO_LINE);
    }

    public static void print(TextAlignment textAlignment, int extraSpace, String msg) {
        print(textAlignment, extraSpace, msg, NO_LINE);
    }

    public static void print(AnsiColor color, String msg) {
        print(color, msg, NO_LINE);
    }

    public static void print(AnsiColor color,
                             TextAlignment textAlignment,
                             int extraSpace,
                             String msg) {
        print(color, textAlignment, extraSpace, msg, NO_LINE);
    }

    public static void print(AnsiColor fg, AnsiColor bg, String msg) {
        print(fg, bg, msg, NO_LINE);
    }

    public static void print(AnsiColor fg,
                             AnsiColor bg,
                             TextAlignment textAlignment,
                             int extraSpace,
                             String msg) {
        print(fg, bg, textAlignment, extraSpace, msg, NO_LINE);
    }

    private static void print(String msg, boolean newLine) {
        print(null, msg, newLine);
    }

    private static void print(TextAlignment textAlignment,
                              int extraSpace,
                              String msg,
                              boolean newLine) {
        print(null, null, textAlignment, extraSpace, msg, newLine);
    }

    private static void print(AnsiColor color, String msg, boolean newLine) {
        print(null, color, msg, newLine);
    }

    private static void print(AnsiColor color,
                              TextAlignment textAlignment,
                              int extraSpace,
                              String msg,
                              boolean newLine) {
        print(null, color, textAlignment, extraSpace, msg, newLine);
    }

    private static void print(AnsiColor fg, AnsiColor bg, String msg, boolean newLine) {
        print(fg, bg, DEFAULT_TEXT_ALIGNMENT, DEFAULT_EXTRA_SPACE, msg, newLine);
    }

    private static void print(AnsiColor fg,
                              AnsiColor bg,
                              TextAlignment textAlignment,
                              int extraSpace,
                              String msg,
                              boolean newLine) {
        addPrintToQueue(fg, bg, textAlignment, extraSpace, msg, newLine, false);
    }

    private static void addPrintToQueue(AnsiColor fg,
                                        AnsiColor bg,
                                        TextAlignment textAlignment,
                                        int extraSpace,
                                        String msg,
                                        boolean newLine,
                                        boolean forceOnNewLine) {
        if (msg == null) {
            throw new IllegalArgumentException("Message cannot be null.");
        }

        PrintableManager.getInstance()
                        .logThread(() -> printScheduled(fg,
                                                        bg,
                                                        textAlignment,
                                                        extraSpace,
                                                        msg,
                                                        newLine,
                                                        forceOnNewLine));
    }

    private static void printScheduled(AnsiColor fg,
                                       AnsiColor bg,
                                       TextAlignment textAlignment,
                                       int extraSpace,
                                       String text,
                                       boolean newLine,
                                       boolean forceOnNewLine) {
        Printable printable = PrintableManager.getInstance().getPrintable();
        if (forceOnNewLine) {
            printable.printForceOnNewLine(text);
            return;
        }

        SimpleColorBuilder colorBuilder = new SimpleColorBuilder.Builder().build();

        if (fg != null) {
            colorBuilder.appendColor(fg);
        }
        if (bg != null) {
            colorBuilder.appendColor(bg);
        }

        if (text.isEmpty() && !newLine) {
            text = EMPTY_MSG;
        }

        StringBuilder sb = new StringBuilder();
        int totalSpace = TextUtils.getTextLength(text) + extraSpace;
        textAlignment.appendAligned(sb, text, totalSpace);

        colorBuilder.append(sb.toString());
        colorBuilder.appendColorReset();

        if (newLine) {
            colorBuilder.appendNewLine();
        }

        printable.print_flush(colorBuilder);
    }
}
