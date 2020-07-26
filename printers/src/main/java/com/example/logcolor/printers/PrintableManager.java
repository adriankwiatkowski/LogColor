package com.example.logcolor.printers;

import com.example.logcolor.printers.interfaces.Printable;
import com.example.logcolor.printers.models.PrintableType;

public class PrintableManager {

    private static PrintableManager sInstance;

    private static final Object LOCK = new Object();

    private Printable mPrintable;

    private boolean mIsNightTheme = false;

    private PrintableManager() {
        mPrintable = PrintableFactory.createPrintable(
                PrintableType.CONSOLE,
                mIsNightTheme);

        PrintableThreads.addCloseTask(() -> {
            if (mPrintable != null) {
                mPrintable.onClose();
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
        PrintableThreads.logThread(runnable);
    }

    public void shutdownThreads() {
        PrintableThreads.shutdownThreads();
    }

    public synchronized void setPrintable(Printable printable) {
        if (printable == null) {
            throw new IllegalArgumentException("Printable cannot be null.");
        }

        if (mPrintable != null && mPrintable != printable) {
            mPrintable.onClose();
        }
        mPrintable = printable;
    }

    public synchronized void setPrintable(PrintableType printableType) {
        Printable printable = PrintableFactory.setPrintable(
                printableType,
                mPrintable,
                mIsNightTheme);

        if (mPrintable != null && mPrintable != printable) {
            mPrintable.onClose();
        }
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
}
