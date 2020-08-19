package com.example.logcolor.utils;

import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.*;

public class AppExecutors {

    private static final int TIMEOUT_TERMINATION_SECONDS = 5;

    private static final Object LOCK = new Object();

    private static final long TERMINATION_TIMEOUT = 4L;
    private static final TimeUnit TERMINATION_TIMEOUT_TIME_UNIT = TimeUnit.SECONDS;

    private static AppExecutors sInstance;

    private final ExecutorService mMainExecutor;

    private AppExecutors(ExecutorService mainExecutor) {
        this.mMainExecutor = mainExecutor;
    }

    public static AppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    ThreadPoolExecutor mainThreadPoolExecutor =
                            (ThreadPoolExecutor) Executors.newSingleThreadExecutor();
                    ExecutorService mainExecutorService = MoreExecutors.getExitingExecutorService(
                            mainThreadPoolExecutor,
                            TERMINATION_TIMEOUT,
                            TERMINATION_TIMEOUT_TIME_UNIT);
                    sInstance = new AppExecutors(mainExecutorService);
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
