package com.company.consolecolors.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static final Object LOCK = new Object();
    private static AppExecutors sInstance;
    private final Executor logExecutor;

    private AppExecutors(Executor logExecutor) {
        this.logExecutor = logExecutor;
    }

    public static AppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new AppExecutors(Executors.newSingleThreadExecutor());
                }
            }
        }

        return sInstance;
    }

    public Executor logThread() {
        return logExecutor;
    }
}
