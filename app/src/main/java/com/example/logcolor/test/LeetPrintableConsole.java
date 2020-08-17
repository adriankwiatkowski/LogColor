package com.example.logcolor.test;

import com.example.logcolor.colorbuilder.converters.TextConverter;
import com.example.logcolor.printers.printables.PrintableConsole;

import java.io.OutputStream;

public class LeetPrintableConsole extends PrintableConsole {

    private static final LeetTextConverter LEET_TEXT_CONVERTER = new LeetTextConverter();

    public LeetPrintableConsole(OutputStream out, boolean autoFlush) {
        super(out, autoFlush);
    }

    @Override
    public TextConverter getTextConverter() {
        return LEET_TEXT_CONVERTER;
    }
}
