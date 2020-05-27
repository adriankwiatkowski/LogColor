package com.company.consolecolors.utils.printers;

import com.company.consolecolors.interfaces.ColorBuilder;
import com.company.consolecolors.interfaces.Printable;

public class PrintableConsole implements Printable {

    @Override
    public void print(String string) {
        System.out.print(string);
    }

    @Override
    public void print(ColorBuilder colorBuilder) {
        System.out.print(colorBuilder.getText());
    }

    @Override
    public void print_flush(ColorBuilder colorBuilder) {
        print(colorBuilder);
        colorBuilder.flush();
    }

    @Override
    public void setDayTheme() {
    }

    @Override
    public void setNightTheme() {
    }

    @Override
    public void onClose() {
    }
}
