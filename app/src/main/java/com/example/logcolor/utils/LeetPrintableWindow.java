package com.example.logcolor.utils;

import com.example.logcolor.colorbuilder.converters.TextConverter;
import com.example.logcolor.printers.printables.PrintableWindow;

public class LeetPrintableWindow extends PrintableWindow {

    private static final TextConverter LEET_TEXT_CONVERTER = new LeetHtmlTextConverter();

    public LeetPrintableWindow(boolean nightTheme) {
        super(nightTheme);
    }

    @Override
    public TextConverter getTextConverter() {
        return LEET_TEXT_CONVERTER;
    }
}
