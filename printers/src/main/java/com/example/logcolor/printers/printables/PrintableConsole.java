package com.example.logcolor.printers.printables;

import com.example.logcolor.colorbuilder.interfaces.ColorBuilder;
import com.example.logcolor.printers.interfaces.Printable;

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
        // Not implemented.
    }

    @Override
    public void setNightTheme() {
        // Not implemented.
    }

    @Override
    public void onClose() {
        // Not implemented.
    }
}
