package com.example.logcolor.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static final Object LOCK = new Object();
    private static AppExecutors sInstance;
    private final Executor mMainExecutor;
    private final Executor mIOExecutor;
    private final Executor mNetworkExecutor;

    private AppExecutors(Executor mainExecutor,
                         Executor ioExecutor,
                         Executor networkExecutor) {
        this.mMainExecutor = mainExecutor;
        this.mIOExecutor = ioExecutor;
        this.mNetworkExecutor = networkExecutor;
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
        return mMainExecutor;
    }

    public Executor diskThread() {
        return mIOExecutor;
    }

    public Executor networkThread() {
        return mNetworkExecutor;
    }

    public void shutdownExecutors() {
        ((ExecutorService) mMainExecutor).shutdown();
    }
}
