package com.example.logcolor.utils;

import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.*;

public class AppExecutors {

    private static final int TIMEOUT_TERMINATION_SECONDS = 5;

    private static final Object LOCK = new Object();

    private static final long ALIVE_TIMEOUT = 4L;
    private static final TimeUnit ALIVE_TIMEOUT_TIME_UNIT = TimeUnit.SECONDS;

    private static AppExecutors sInstance;

    private final ExecutorService mMainExecutor;

    private AppExecutors(ExecutorService mainExecutor) {
        this.mMainExecutor = mainExecutor;
    }

    public static AppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0,
                                                                                   2,
                                                                                   ALIVE_TIMEOUT,
                                                                                   ALIVE_TIMEOUT_TIME_UNIT,
                                                                                   new SynchronousQueue<>());
                    ExecutorService executorService = MoreExecutors.getExitingExecutorService(
                            threadPoolExecutor,
                            ALIVE_TIMEOUT,
                            ALIVE_TIMEOUT_TIME_UNIT);
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
