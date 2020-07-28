package com.example.logcolor;

import com.example.logcolor.printers.PrintableManager;
import com.example.logcolor.utils.AppExecutors;
import com.example.logcolor.utils.WindowLookUtils;

public class Main {

    public static void main(String[] args) {
        new Main().start();
    }

    private void start() {

        AppExecutors.getInstance().mainThread().execute(() -> {
            WindowLookUtils.setLookAndFeel();

            PrintableManager.getInstance().shutdownThreads();
            AppExecutors.getInstance().shutdownExecutors();
        });
    }
}