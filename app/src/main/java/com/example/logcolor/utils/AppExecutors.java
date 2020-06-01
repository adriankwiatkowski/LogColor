package com.example.logcolor.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AppExecutors {

    private static final int TIMEOUT_TERMINATION_SECONDS = 5;

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
        shutdownAndAwaitTermination((ExecutorService) mMainExecutor);
    }

    private void shutdownAndAwaitTermination(ExecutorService executorService) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(
                    TIMEOUT_TERMINATION_SECONDS, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(
                        TIMEOUT_TERMINATION_SECONDS, TimeUnit.SECONDS)) {
                    // Error terminating thread.
                    System.err.println("ExecutorService did not terminate.");
                }
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
