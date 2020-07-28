package com.example.logcolor.printers;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class PrintableAppExecutors {

    private static final int TIMEOUT_TERMINATION_SECONDS = 60;

    private static final Object LOCK = new Object();

    private static PrintableAppExecutors sInstance;

    private final ExecutorService mLogExecutor;

    private PrintableAppExecutors(ExecutorService logExecutor) {
        this.mLogExecutor = logExecutor;
    }

    static PrintableAppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new PrintableAppExecutors(Executors.newSingleThreadExecutor());
                }
            }
        }

        return sInstance;
    }

    Executor logThread() {
        return mLogExecutor;
    }

    void shutdownExecutors() {
        shutdownAndAwaitTermination(mLogExecutor);

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
