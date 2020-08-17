package com.example.logcolor.printers;

import com.example.logcolor.color.models.TextAlignment;
import com.example.logcolor.color.models.TextAttribute;
import com.example.logcolor.printers.models.PrintableType;
import com.example.logcolor.printers.printables.Printable;

import java.awt.*;

public final class PrintableManager {

    private static PrintableManager sInstance;

    private static final Object LOCK = new Object();

    private Printable mPrintable;

    private boolean mIsNightTheme = false;
    private TextAttribute defaultTextAttribute = null;

    private PrintableManager() {
        mPrintable = PrintableFactory.createPrintable(PrintableType.CONSOLE, mIsNightTheme);

        PrintableThreads.addCloseTask(() -> {
            if (mPrintable != null) {
                mPrintable.close();
                mPrintable = null;
            }
        });
    }

    public static PrintableManager getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new PrintableManager();
                }
            }
        }

        return sInstance;
    }

    public void logThread(Runnable runnable) {
        PrintableThreads.printableInvoke(runnable);
    }

    public void shutdownThreads() {
        PrintableThreads.shutdownThreads();
    }

    public synchronized void setPrintable(Printable printable) {
        if (printable == null) {
            throw new IllegalArgumentException("Printable cannot be null.");
        }

        if (mPrintable != null && mPrintable != printable) {
            mPrintable.close();
        }

        printable.setDefaultFormat(this.defaultTextAttribute);

        mPrintable = printable;
    }

    public synchronized void setPrintable(PrintableType printableType) {
        Printable printable =
                PrintableFactory.setPrintable(printableType, mPrintable, mIsNightTheme);

        if (mPrintable != null && mPrintable != printable) {
            mPrintable.close();
        }

        printable.setDefaultFormat(this.defaultTextAttribute);

        mPrintable = printable;
    }

    public Printable getPrintable() {
        return mPrintable;
    }

    public boolean isNightTheme() {
        return mIsNightTheme;
    }

    public boolean isDayTheme() {
        return !mIsNightTheme;
    }

    public synchronized void setNightTheme() {
        if (isNightTheme()) {
            return;
        }

        mIsNightTheme = true;
        mPrintable.setNightTheme();
    }

    public synchronized void setDayTheme() {
        if (isDayTheme()) {
            return;
        }

        mIsNightTheme = false;
        mPrintable.setDayTheme();
    }

    public synchronized void setDefaultFormat(Color foreground, Color background) {
        if (this.defaultTextAttribute == null) {
            this.defaultTextAttribute = new TextAttribute.Builder().build();
        }
        this.defaultTextAttribute.setForeground(foreground);
        this.defaultTextAttribute.setBackground(background);
        if (mPrintable != null) {
            mPrintable.setDefaultFormat(this.defaultTextAttribute.getForeground(),
                                        this.defaultTextAttribute.getBackground());
        }
    }

    public synchronized void setDefaultFormat(Color foreground,
                                              Color background,
                                              TextAlignment textAlignment) {
        if (this.defaultTextAttribute == null) {
            this.defaultTextAttribute = new TextAttribute.Builder().build();
        }
        this.defaultTextAttribute.setForeground(foreground);
        this.defaultTextAttribute.setBackground(background);
        this.defaultTextAttribute.setTextAlignment(textAlignment);
        if (mPrintable != null) {
            mPrintable.setDefaultFormat(this.defaultTextAttribute.getForeground(),
                                        this.defaultTextAttribute.getBackground(),
                                        this.defaultTextAttribute.getTextAlignment());
        }
    }

    public synchronized void setDefaultFormat(TextAttribute textAttribute) {
        this.defaultTextAttribute = textAttribute;
        if (mPrintable != null) {
            mPrintable.setDefaultFormat(this.defaultTextAttribute);
        }
    }
}
