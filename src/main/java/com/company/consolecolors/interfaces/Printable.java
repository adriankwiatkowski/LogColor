package com.company.consolecolors.interfaces;

public interface Printable {

    void print(String string);

    void print(ColorBuilder colorBuilder);

    void print_flush(ColorBuilder colorBuilder);

    void setDayTheme();

    void setNightTheme();

    void onClose();
}
