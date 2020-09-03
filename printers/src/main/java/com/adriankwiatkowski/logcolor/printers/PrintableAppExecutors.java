package com.adriankwiatkowski.logcolor.printers;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class PrintableAppExecutors {

    private static final int TIMEOUT_TERMINATION_SECONDS = 5;
    private static final int N_THREADS = 1;

    private static final Object LOCK = new Object();

    private static PrintableAppExecutors sInstance;

    private final ExecutorService mMainExecutor;

    private PrintableAppExecutors(ExecutorService mainExecutor) {
        this.mMainExecutor = mainExecutor;
    }

    static PrintableAppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS,
                                                                                   new ThreadFactoryBuilder()
                                                                                           .setDaemon(
                                                                                                   true)
                                                                                           .build());
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
