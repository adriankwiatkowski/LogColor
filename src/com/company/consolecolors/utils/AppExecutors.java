package com.company.consolecolors.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static final Object LOCK = new Object();
    private static AppExecutors sInstance;
    private final Executor mMainThread;
    private final Executor mLogExecutor;
    private final Executor mConsoleExecutor;

    private AppExecutors(Executor mainThread, Executor logExecutor, Executor consoleExecutor) {
        this.mMainThread = mainThread;
        this.mLogExecutor = logExecutor;
        this.mConsoleExecutor = consoleExecutor;
    }

    public static AppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new AppExecutors(
                            Executors.newSingleThreadExecutor(),
                            Executors.newSingleThreadExecutor(),
                            Executors.newSingleThreadExecutor());
                }
            }
        }

        return sInstance;
    }

    public Executor mainThread() {
        return mMainThread;
    }

    public Executor logThread() {
        return mLogExecutor;
    }

    public Executor consoleThread() {
        return mConsoleExecutor;
    }

    public void shutdownMainThread() {
//        ((ExecutorService) mMainThread).shutdownNow();
        shutdownExecutors();
    }

    public void shutdownExecutors() {
        ((ExecutorService) mMainThread).shutdown();
        ((ExecutorService) mLogExecutor).shutdown();
        ((ExecutorService) mConsoleExecutor).shutdown();
    }

    public void shutdownNowExecutors() {
        ((ExecutorService) mMainThread).shutdownNow();
        ((ExecutorService) mLogExecutor).shutdownNow();
        ((ExecutorService) mConsoleExecutor).shutdownNow();
    }

    public boolean isMainThreadShutdown() {
        return ((ExecutorService) mMainThread).isShutdown();
    }

    public boolean isLogExecutorShutdown() {
        return ((ExecutorService) mLogExecutor).isShutdown();
    }

    public boolean isConsoleExecutorShutdown() {
        return ((ExecutorService) mConsoleExecutor).isShutdown();
    }
}
