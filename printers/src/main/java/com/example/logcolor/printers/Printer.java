package com.example.logcolor.printers;

import com.example.logcolor.colorbuilder.text.TextAlignment;
import com.example.logcolor.colorbuilder.text.TextAttribute;
import com.example.logcolor.colorbuilder.text.TextStyle;
import com.example.logcolor.colorbuilder.builders.SimpleColorBuilder;
import com.example.logcolor.colorbuilder.interfaces.ColorBuilder;
import com.example.logcolor.printers.printables.Printable;

import java.awt.*;
import java.util.EnumSet;

public final class Printer {

    private static final boolean NEW_LINE = true;
    private static final boolean NO_LINE = false;
    private static final String EMPTY_MSG = "Message is empty.";

    private static final TextAlignment DEFAULT_TEXT_ALIGNMENT = TextAlignment.NONE;
    private static final int DEFAULT_EXTRA_SPACE = 0;

    private Printer() {
    }

    public static void printForceOnNewLine(String msg) {
        addPrintToQueue(msg, null, null, DEFAULT_TEXT_ALIGNMENT, DEFAULT_EXTRA_SPACE, false, true);
    }

    public static void printForceOnNewLine(ColorBuilder colorBuilder) {
        addPrintToQueue(colorBuilder, true);
    }

    public static void println() {
        println("");
    }

    public static void println(String msg) {
        print(msg, NEW_LINE);
    }

    public static void println(String msg, TextAttribute textAttribute) {
        print(msg, textAttribute, NEW_LINE);
    }

    public static void println(String msg, TextAlignment textAlignment, int extraSpace) {
        print(msg, textAlignment, extraSpace, NEW_LINE);
    }

    public static void println(String msg, Color fg, Color bg) {
        print(msg, fg, bg, NEW_LINE);
    }

    public static void println(String msg,
                               Color fg,
                               Color bg,
                               TextAlignment textAlignment,
                               int extraSpace) {
        print(msg, fg, bg, textAlignment, extraSpace, NEW_LINE);
    }

    public static void print(String msg) {
        print(msg, NO_LINE);
    }

    public static void print(String msg, TextAttribute textAttribute) {
        print(msg, textAttribute, NO_LINE);
    }

    public static void print(String msg, TextAlignment textAlignment, int extraSpace) {
        print(msg, textAlignment, extraSpace, NO_LINE);
    }

    public static void print(String msg, Color foreground, Color background) {
        print(msg, foreground, background, NO_LINE);
    }

    public static void print(String msg,
                             Color foreground,
                             Color background,
                             TextAlignment textAlignment,
                             int extraSpace) {
        print(msg, foreground, background, textAlignment, extraSpace, NO_LINE);
    }

    private static void print(String msg, boolean newLine) {
        print(msg, null, null, newLine);
    }

    private static void print(String msg, TextAttribute textAttribute, boolean newLine) {
        ColorBuilder colorBuilder = new SimpleColorBuilder.Builder().build();
        colorBuilder.append(msg, textAttribute);
        addPrintToQueue(colorBuilder, newLine);
    }

    private static void print(String msg,
                              TextAlignment textAlignment,
                              int extraSpace,
                              boolean newLine) {
        print(msg, null, null, textAlignment, extraSpace, newLine);
    }

    private static void print(String msg, Color foreground, Color background, boolean newLine) {
        print(msg, foreground, background, DEFAULT_TEXT_ALIGNMENT, DEFAULT_EXTRA_SPACE, newLine);
    }

    private static void print(String msg,
                              Color foreground,
                              Color background,
                              TextAlignment textAlignment,
                              int extraSpace,
                              boolean newLine) {
        addPrintToQueue(msg, foreground, background, textAlignment, extraSpace, newLine, false);
    }

    private static void addPrintToQueue(ColorBuilder colorBuilder, boolean forceOnNewLine) {
        PrintableManager.getInstance()
                        .logThread(() -> printScheduled(colorBuilder, forceOnNewLine));
    }

    private static void addPrintToQueue(String msg,
                                        Color foreground,
                                        Color background,
                                        TextAlignment textAlignment,
                                        int extraSpace,
                                        boolean newLine,
                                        boolean forceOnNewLine) {
        if (msg == null) {
            throw new IllegalArgumentException("Message cannot be null.");
        }

        PrintableManager.getInstance()
                        .logThread(() -> printScheduled(msg,
                                                        foreground,
                                                        background,
                                                        textAlignment,
                                                        extraSpace,
                                                        newLine,
                                                        forceOnNewLine));
    }

    private static void printScheduled(String text,
                                       Color foreground,
                                       Color background,
                                       TextAlignment textAlignment,
                                       int extraSpace,
                                       boolean newLine,
                                       boolean forceOnNewLine) {
        Printable printable = PrintableManager.getInstance().getPrintable();

        if (text.isEmpty() && !newLine) {
            text = EMPTY_MSG;
        }

        if (textAlignment == null) {
            textAlignment = TextAlignment.NONE;
        }

        SimpleColorBuilder colorBuilder = new SimpleColorBuilder.Builder().build();

        TextAttribute textAttribute = new TextAttribute.Builder().setTextAlignment(textAlignment)
                                                                 .setTextStyle(EnumSet.of(TextStyle.NORMAL))
                                                                 .setExtraSpace(extraSpace)
                                                                 .build();

        if (foreground != null) {
            textAttribute.setForeground(foreground);
        }
        if (background != null) {
            textAttribute.setBackground(background);
        }

        colorBuilder.append(text, textAttribute);

        if (newLine) {
            colorBuilder.appendNewLine();
        }

        if (forceOnNewLine) {
            printable.printForceOnNewLine(colorBuilder);
        } else {
            printable.print_flush(colorBuilder);
        }
    }

    private static void printScheduled(ColorBuilder colorBuilder, boolean forceOnNewLine) {
        Printable printable = PrintableManager.getInstance().getPrintable();
        if (forceOnNewLine) {
            printable.printForceOnNewLine(colorBuilder);
        } else {
            printable.print_flush(colorBuilder);
        }
    }
}
