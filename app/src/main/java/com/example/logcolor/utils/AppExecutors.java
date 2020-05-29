package com.example.logcolor.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static final Object LOCK = new Object();
    private static AppExecutors sInstance;
    private final Executor mMainThread;
    private final Executor mLogExecutor;

    private AppExecutors(Executor mainThread, Executor logExecutor) {
        this.mMainThread = mainThread;
        this.mLogExecutor = logExecutor;
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

    public Executor mainThread() {
        return mMainThread;
    }

    public Executor logThread() {
        return mLogExecutor;
    }

    public void shutdownMainThread() {
//        ((ExecutorService) mMainThread).shutdownNow();
        shutdownExecutors();
    }

    public void shutdownExecutors() {
        ((ExecutorService) mMainThread).shutdown();
        ((ExecutorService) mLogExecutor).shutdown();
    }

    public void shutdownNowExecutors() {
        ((ExecutorService) mMainThread).shutdownNow();
        ((ExecutorService) mLogExecutor).shutdownNow();
    }

    public boolean isMainThreadShutdown() {
        return ((ExecutorService) mMainThread).isShutdown();
    }

    public boolean isLogExecutorShutdown() {
        return ((ExecutorService) mLogExecutor).isShutdown();
    }
}
