package com.example.logcolor.utils;

import com.example.logcolor.colorbuilder.converters.TextConverter;
import com.example.logcolor.printers.printables.PrintableConsole;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class LeetPrintableConsole extends PrintableConsole {

    private static final LeetAnsiTextConverter LEET_TEXT_CONVERTER = new LeetAnsiTextConverter();

    public LeetPrintableConsole(OutputStream out) {
        super(out);
    }

    public LeetPrintableConsole(OutputStream out, boolean autoFlush) {
        super(out, autoFlush);
    }

    public LeetPrintableConsole(OutputStream out,
                                boolean autoFlush,
                                String encoding) throws UnsupportedEncodingException {
        super(out, autoFlush, encoding);
    }

    public LeetPrintableConsole(String fileName) throws FileNotFoundException {
        super(fileName);
    }

    public LeetPrintableConsole(String fileName,
                                String csn) throws FileNotFoundException, UnsupportedEncodingException {
        super(fileName, csn);
    }

    public LeetPrintableConsole(File file) throws FileNotFoundException {
        super(file);
    }

    public LeetPrintableConsole(File file,
                                String csn) throws FileNotFoundException, UnsupportedEncodingException {
        super(file, csn);
    }

    @Override
    public TextConverter getTextConverter() {
        return LEET_TEXT_CONVERTER;
    }
}
