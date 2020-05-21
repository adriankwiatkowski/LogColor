package com.company.consolecolors.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static final Object LOCK = new Object();
    private static AppExecutors sInstance;
    private final Executor mLogExecutor;
    private final Executor mConsoleExecutor;

    private AppExecutors(Executor logExecutor, Executor consoleExecutor) {
        this.mLogExecutor = logExecutor;
        this.mConsoleExecutor = consoleExecutor;
    }

    public static AppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new AppExecutors(
                            Executors.newSingleThreadExecutor(),
                            Executors.newSingleThreadExecutor());
                }
            }
        }

        return sInstance;
    }

    public Executor logThread() {
        return mLogExecutor;
    }

    public Executor consoleThread() {
        return mConsoleExecutor;
    }

    public void shutdownExecutors() {
        ((ExecutorService) mLogExecutor).shutdown();
        ((ExecutorService) mConsoleExecutor).shutdown();
    }

    public void shutdownNowExecutors() {
        ((ExecutorService) mLogExecutor).shutdownNow();
        ((ExecutorService) mConsoleExecutor).shutdownNow();
    }
}
