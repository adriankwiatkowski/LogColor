package com.adriankwiatkowski.logcolor.utils.printables;

import com.adriankwiatkowski.logcolor.colorbuilder.converters.HtmlTextConverter;
import com.adriankwiatkowski.logcolor.printers.printables.PrintableWindow;

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
