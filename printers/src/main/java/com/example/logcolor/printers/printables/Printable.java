package com.example.logcolor.printers.printables;

import com.example.logcolor.color.models.TextAlignment;
import com.example.logcolor.color.models.TextAttribute;
import com.example.logcolor.colorbuilder.TextConverter;
import com.example.logcolor.colorbuilder.builders.SimpleColorBuilder;
import com.example.logcolor.colorbuilder.interfaces.ColorBuilder;

import java.awt.*;
import java.io.*;
import java.util.Formatter;
import java.util.Locale;

public abstract class Printable extends PrintStream {

    protected TextAttribute defaultTextAttribute = null;

    public Printable(OutputStream out) {
        super(out);
    }

    public Printable(OutputStream out, boolean autoFlush) {
        super(out, autoFlush);
    }

    public Printable(OutputStream out,
                     boolean autoFlush,
                     String encoding) throws UnsupportedEncodingException {
        super(out, autoFlush, encoding);
    }

    public Printable(String fileName) throws FileNotFoundException {
        super(fileName);
    }

    public Printable(String fileName,
                     String csn) throws FileNotFoundException, UnsupportedEncodingException {
        super(fileName, csn);
    }

    public Printable(File file) throws FileNotFoundException {
        super(file);
    }

    public Printable(File file,
                     String csn) throws FileNotFoundException, UnsupportedEncodingException {
        super(file, csn);
    }

    public void print(ColorBuilder colorBuilder) {
        write(colorBuilder);
    }

    public void print_flush(ColorBuilder colorBuilder) {
        print(colorBuilder);
        colorBuilder.flush();
    }

    @Override
    public void write(int b) {
        write(String.valueOf(b));
    }

    @Override
    public void write(byte[] buf, int off, int len) {
        for (int i = off; i < len; ++i) {
            write(buf[i]);
        }
    }

    @Override
    public void print(boolean b) {
        write(String.valueOf(b));
    }

    @Override
    public void print(char c) {
        write(String.valueOf(c));
    }

    @Override
    public void print(int i) {
        write(String.valueOf(i));
    }

    @Override
    public void print(long l) {
        write(String.valueOf(l));
    }

    @Override
    public void print(float f) {
        write(String.valueOf(f));
    }

    @Override
    public void print(double d) {
        write(String.valueOf(d));
    }

    @Override
    public void print(char[] s) {
        write(String.valueOf(s));
    }

    @Override
    public void print(String s) {
        write(s);
    }

    @Override
    public void print(Object obj) {
        write(obj.toString());
    }

    @Override
    public void println() {
        write("\n");
    }

    @Override
    public void println(boolean b) {
        write(b + "\n");
    }

    @Override
    public void println(char c) {
        write(c + "\n");
    }

    @Override
    public void println(int i) {
        write(i + "\n");
    }

    @Override
    public void println(long l) {
        write(l + "\n");
    }

    @Override
    public void println(float f) {
        write(f + "\n");
    }

    @Override
    public void println(double d) {
        write(d + "\n");
    }

    @Override
    public void println(char[] s) {
        write(String.valueOf(s) + "\n");
    }

    @Override
    public void println(String s) {
        write(s + "\n");
    }

    @Override
    public void println(Object obj) {
        write(obj.toString() + "\n");
    }

    @Override
    public PrintStream printf(String format, Object... args) {
        write(new Formatter().format(format, args).toString());
        return this;
    }

    @Override
    public PrintStream printf(Locale l, String format, Object... args) {
        write(new Formatter().format(l, format, args).toString());
        return this;
    }

    @Override
    public PrintStream format(String format, Object... args) {
        write(new Formatter().format(format, args).toString());
        return this;
    }

    @Override
    public PrintStream format(Locale l, String format, Object... args) {
        write(new Formatter().format(l, format, args).toString());
        return this;
    }

    @Override
    public PrintStream append(CharSequence csq) {
        print(String.valueOf(csq));
        return this;
    }

    @Override
    public PrintStream append(CharSequence csq, int start, int end) {
        if (csq == null) csq = "null";
        return append(csq.subSequence(start, end));
    }

    @Override
    public PrintStream append(char c) {
        print(c);
        return this;
    }

    @Override
    public void write(byte[] buf) throws IOException {
        write(buf, 0, buf.length);
    }

    public void setDefaultFormat(Color foreground, Color background) {
        if (this.defaultTextAttribute == null) {
            this.defaultTextAttribute = new TextAttribute.Builder().build();
        }
        this.defaultTextAttribute.setForeground(foreground);
        this.defaultTextAttribute.setBackground(background);
    }

    public void setDefaultFormat(Color foreground, Color background, TextAlignment textAlignment) {
        if (this.defaultTextAttribute == null) {
            this.defaultTextAttribute = new TextAttribute.Builder().build();
        }
        this.defaultTextAttribute.setForeground(foreground);
        this.defaultTextAttribute.setBackground(background);
        this.defaultTextAttribute.setTextAlignment(textAlignment);
    }

    public void setDefaultFormat(TextAttribute textAttribute) {
        this.defaultTextAttribute = textAttribute;
    }

    protected ColorBuilder convertTextToDefaultFormat(String s) {
        ColorBuilder colorBuilder = new SimpleColorBuilder.Builder().build();
        colorBuilder.append(s, this.defaultTextAttribute);
        return colorBuilder;
    }

    protected void write(String s) {
        ColorBuilder colorBuilder = convertTextToDefaultFormat(s);
        write(colorBuilder);
    }

    protected abstract void write(ColorBuilder colorBuilder);

    public abstract TextConverter getTextConverter();

    public abstract void printForceOnNewLine(ColorBuilder colorBuilder);

    public abstract void setDayTheme();

    public abstract void setNightTheme();
}
