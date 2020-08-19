package com.example.logcolor.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AppExecutors {

    private static final int TIMEOUT_TERMINATION_SECONDS = 5;
    private static final int N_THREADS = 1;

    private static final Object LOCK = new Object();

    private static AppExecutors sInstance;

    private final ExecutorService mMainExecutor;

    private AppExecutors(ExecutorService mainExecutor) {
        this.mMainExecutor = mainExecutor;
    }

    public static AppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS,
                                                                                   new ThreadFactoryBuilder()
                                                                                           .setDaemon(
                                                                                                   true)
                                                                                           .build());
                    sInstance = new AppExecutors(executorService);
                }
            }
        }

        return sInstance;
    }

    public Executor mainThread() {
        return mMainExecutor;
    }

    public void shutdownExecutors() {
        shutdownAndAwaitTermination(mMainExecutor);
    }

    private void shutdownAndAwaitTermination(ExecutorService executorService) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(TIMEOUT_TERMINATION_SECONDS, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(TIMEOUT_TERMINATION_SECONDS,
                                                      TimeUnit.SECONDS)) {
                    // Error terminating thread.
                    System.err.println("ExecutorService did not terminate.");
                }
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
            //e.printStackTrace();
        }
    }
}
