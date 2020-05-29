package com.example.printers.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PrinterAppExecutors {

    private static final Object LOCK = new Object();
    private static PrinterAppExecutors sInstance;
    private final Executor mLogExecutor;

    private PrinterAppExecutors(Executor logExecutor) {
        this.mLogExecutor = logExecutor;
    }

    public static PrinterAppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new PrinterAppExecutors(
                            Executors.newSingleThreadExecutor());
                }
            }
        }

        return sInstance;
    }

    public Executor logThread() {
        return mLogExecutor;
    }
}
