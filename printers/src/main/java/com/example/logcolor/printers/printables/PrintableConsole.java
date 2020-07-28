package com.example.logcolor.printers.printables;

import java.io.*;

public class PrintableConsole extends Printable {

    private OutputStream outputStream;
    private boolean mIsNextPrintNewLine = false;
    private boolean mIsForceOnNewLine = false;

    public PrintableConsole(OutputStream out) {
        super(out);
        this.outputStream = out;
    }

    public PrintableConsole(OutputStream out, boolean autoFlush) {
        super(out, autoFlush);
        this.outputStream = out;
    }

    public PrintableConsole(OutputStream out,
                            boolean autoFlush,
                            String encoding) throws UnsupportedEncodingException {
        super(out, autoFlush, encoding);
        this.outputStream = out;
    }

    public PrintableConsole(String fileName) throws FileNotFoundException {
        super(fileName);
        this.outputStream = out;
    }

    public PrintableConsole(String fileName,
                            String csn) throws FileNotFoundException, UnsupportedEncodingException {
        super(fileName, csn);
        this.outputStream = out;
    }

    public PrintableConsole(File file) throws FileNotFoundException {
        super(file);
        this.outputStream = out;
    }

    public PrintableConsole(File file,
                            String csn) throws FileNotFoundException, UnsupportedEncodingException {
        super(file, csn);
        this.outputStream = out;
    }

    @Override
    protected void write(String s) {
        if (mIsForceOnNewLine && !mIsNextPrintNewLine) {
            mIsForceOnNewLine = false;
            mIsNextPrintNewLine = true;
            byte[] bytesNewLine = "\n".getBytes();
            try {
                outputStream.write(bytesNewLine, 0, bytesNewLine.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        s = setDefaultColorIfNotOverridden(s);

        mIsNextPrintNewLine = s.endsWith("\n");
        mIsForceOnNewLine = false;

        byte[] bytes = s.getBytes();
        try {
            outputStream.write(bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printForceOnNewLine(String msg) {
        mIsNextPrintNewLine = true;
        write(msg);
    }

    @Override
    public void close() {
        //super.close();
        // Do nothing, as we are using default output to 'terminal'.
    }

    @Override
    public void setDayTheme() {
        // Not implemented.
    }

    @Override
    public void setNightTheme() {
        // Not implemented.
    }
}
