package com.example.printers;

import com.example.color.models.AnsiColor;
import com.example.color.models.TextAlignment;
import com.example.colorbuilder.builders.SimpleColorBuilder;
import com.example.color.utils.TextUtils;
import com.example.printers.utils.PrinterAppExecutors;

public class Printer {

    private static final boolean NEW_LINE = true;
    private static final boolean NO_LINE = false;
    private static final String EMPTY_MSG = "Message is empty.";

    private static final TextAlignment DEFAULT_TEXT_ALIGNMENT = TextAlignment.NONE;
    private static final int DEFAULT_EXTRA_SPACE = 0;

    private Printer() {
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
                               TextAlignment textAlignment, int extraSpace,
                               String msg) {
        print(color, textAlignment, extraSpace, msg, NEW_LINE);
    }

    public static void println(AnsiColor fg, AnsiColor bg, String msg) {
        print(fg, bg, msg, NEW_LINE);
    }

    public static void println(AnsiColor fg, AnsiColor bg,
                               TextAlignment textAlignment, int extraSpace,
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
                             TextAlignment textAlignment, int extraSpace,
                             String msg) {
        print(color, textAlignment, extraSpace, msg, NO_LINE);
    }

    public static void print(AnsiColor fg, AnsiColor bg, String msg) {
        print(fg, bg, msg, NO_LINE);
    }

    public static void print(AnsiColor fg, AnsiColor bg,
                             TextAlignment textAlignment, int extraSpace,
                             String msg) {
        print(fg, bg, textAlignment, extraSpace, msg, NO_LINE);
    }

    private static void print(String msg, boolean newLine) {
        print(null, msg, newLine);
    }

    private static void print(TextAlignment textAlignment, int extraSpace,
                              String msg, boolean newLine) {
        print(null, null, textAlignment, extraSpace, msg, newLine);
    }

    private static void print(AnsiColor color, String msg, boolean newLine) {
        print(null, color, msg, newLine);
    }

    private static void print(AnsiColor color,
                              TextAlignment textAlignment, int extraSpace,
                              String msg, boolean newLine) {
        print(null, color, textAlignment, extraSpace, msg, newLine);
    }

    private static void print(AnsiColor fg, AnsiColor bg, String msg, boolean newLine) {
        print(fg, bg, DEFAULT_TEXT_ALIGNMENT, DEFAULT_EXTRA_SPACE, msg, newLine);
    }

    private static void print(AnsiColor fg, AnsiColor bg,
                              TextAlignment textAlignment, int extraSpace,
                              String msg, boolean newLine) {
        addPrintToQueue(fg, bg, textAlignment, extraSpace, msg, newLine);
    }

    private static void addPrintToQueue(AnsiColor fg, AnsiColor bg,
                                        TextAlignment textAlignment, int extraSpace,
                                        String msg, boolean newLine) {
        if (msg == null)
            throw new IllegalArgumentException("Message cannot be null.");

        PrinterAppExecutors.getInstance().logThread().execute(() ->
                printScheduled(fg, bg, textAlignment, extraSpace, msg, newLine));
    }

    private static void printScheduled(AnsiColor fg, AnsiColor bg,
                                       TextAlignment textAlignment, int extraSpace,
                                       String text, boolean newLine) {
        SimpleColorBuilder colorBuilder = new SimpleColorBuilder.Builder().build();

        if (fg != null)
            colorBuilder.appendColor(fg);
        if (bg != null)
            colorBuilder.appendColor(bg);

        if (text.isEmpty() && !newLine)
            text = EMPTY_MSG;

        StringBuilder sb = new StringBuilder();
        int totalSpace = TextUtils.getTextLength(text) + extraSpace;
        textAlignment.appendAligned(sb, text, totalSpace);

        colorBuilder.append(sb.toString());
        colorBuilder.appendColorReset();

        if (newLine)
            colorBuilder.appendNewLine();

        PrintableManager.getInstance().getPrintable().print_flush(colorBuilder);
    }
}
