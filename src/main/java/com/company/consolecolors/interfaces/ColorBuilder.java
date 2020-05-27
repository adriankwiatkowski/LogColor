package com.company.consolecolors.interfaces;

import com.company.consolecolors.models.AnsiColor;
import com.company.consolecolors.models.TextAlignment;
import com.company.consolecolors.models.TextAttribute;

public interface ColorBuilder {

    void append(String text);

    void append(int i);

    void append(char c);

    void appendColor(AnsiColor color);

    void appendColor(AnsiColor fg, AnsiColor bg);

    void appendTextColor(AnsiColor color, String text);

    void appendTextColor(AnsiColor color, String text,
                         TextAlignment textAlignment);

    void appendTextColor(AnsiColor fg, AnsiColor bg, String text);

    void appendTextColor(AnsiColor fg, AnsiColor bg,
                         String text, TextAlignment textAlignment);

    void appendTextAlign(String text);

    void appendTextAlign(String text, TextAttribute textAttribute);

    void appendTextAlign(String text, int maxTextLength,
                         TextAlignment textAlignment);

    void appendColorReset();

    void appendNewLine();

    void flush();

    String getText();

    default String getText_Flush() {
        String text = getText();
        flush();
        return text;
    }

    default void appendColorReset_NewLine() {
        appendColorReset();
        appendNewLine();
    }

    default void appendTextColor_Reset(AnsiColor color, String text) {
        appendTextColor(color, text);
        appendColorReset();
    }

    default void appendTextColor_Reset(AnsiColor color, String text,
                                       TextAlignment textAlignment) {
        appendTextColor(color, text, textAlignment);
    }

    default void appendTextColor_Reset(AnsiColor fg, AnsiColor bg, String text) {
        appendTextColor(fg, bg, text);
        appendColorReset();
    }

    default void appendTextColor_Reset(AnsiColor fg, AnsiColor bg,
                                       String text, TextAlignment textAlignment) {
        appendTextColor(fg, bg, text, textAlignment);
        appendColorReset();
    }

    default void appendTextColor_Reset_NewLine(AnsiColor color, String text) {
        appendTextColor(color, text);
        appendColorReset_NewLine();
    }

    default void appendTextColor_Reset_NewLine(AnsiColor color, String text,
                                               TextAlignment textAlignment) {
        appendTextColor(color, text, textAlignment);
        appendColorReset_NewLine();
    }

    default void appendTextColor_Reset_NewLine(AnsiColor fg, AnsiColor bg, String text) {
        appendTextColor(fg, bg, text);
        appendColorReset_NewLine();
    }

    default void appendTextColor_Reset_NewLine(AnsiColor fg, AnsiColor bg,
                                               String text, TextAlignment textAlignment) {
        appendTextColor(fg, bg, text, textAlignment);
        appendColorReset_NewLine();
    }
}