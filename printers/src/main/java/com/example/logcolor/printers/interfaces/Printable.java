package com.example.logcolor.printers.interfaces;

import com.example.logcolor.colorbuilder.interfaces.ColorBuilder;

public interface Printable {

    void print(String string);

    void print(ColorBuilder colorBuilder);

    void print_flush(ColorBuilder colorBuilder);

    void setDayTheme();

    void setNightTheme();

    void onClose();
}
