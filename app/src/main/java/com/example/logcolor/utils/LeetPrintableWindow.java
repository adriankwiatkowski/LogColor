package com.example.logcolor.utils;

import com.example.logcolor.colorbuilder.converters.HtmlTextConverter;
import com.example.logcolor.printers.printables.PrintableWindow;

public class LeetPrintableWindow extends PrintableWindow {

    private static final LeetHtmlTextConverter LEET_TEXT_CONVERTER = new LeetHtmlTextConverter();

    public LeetPrintableWindow(boolean nightTheme) {
        super(nightTheme);
    }

    @Override
    public HtmlTextConverter getTextConverter() {
        return LEET_TEXT_CONVERTER;
    }
}
