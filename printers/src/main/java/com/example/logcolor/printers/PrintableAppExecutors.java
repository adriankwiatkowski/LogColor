package com.example.logcolor.printers;

import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class PrintableAppExecutors {

    private static final int TIMEOUT_TERMINATION_SECONDS = 60;

    private static final Object LOCK = new Object();

    private static final long TERMINATION_TIMEOUT = 4L;
    private static final TimeUnit TERMINATION_TIMEOUT_TIME_UNIT = TimeUnit.SECONDS;

    private static PrintableAppExecutors sInstance;

    private final ExecutorService mMainExecutor;

    private PrintableAppExecutors(ExecutorService mainExecutor) {
        this.mMainExecutor = mainExecutor;
    }

    static PrintableAppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    ThreadPoolExecutor threadPoolExecutor =
                            (ThreadPoolExecutor) Executors.newSingleThreadExecutor();
                    ExecutorService executorService = MoreExecutors.getExitingExecutorService(
                            threadPoolExecutor,
                            TERMINATION_TIMEOUT,
                            TERMINATION_TIMEOUT_TIME_UNIT);
                    sInstance = new PrintableAppExecutors(executorService);
                }
            }
        }

        return sInstance;
    }

    void execute(Runnable runnable) {
        mMainExecutor.execute(runnable);
    }

    void shutdownExecutors() {
        shutdownAndAwaitTermination(mMainExecutor);

        sInstance = null;
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
            e.printStackTrace();
        }
    }
}
