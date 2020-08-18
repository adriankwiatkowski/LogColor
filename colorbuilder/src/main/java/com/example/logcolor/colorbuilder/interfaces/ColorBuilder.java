package com.example.logcolor.colorbuilder.interfaces;

import com.example.logcolor.colorbuilder.converters.TextConverter;
import com.example.logcolor.colorbuilder.text.Text;
import com.example.logcolor.colorbuilder.text.TextAttribute;

import java.util.List;

public interface ColorBuilder {

    void append(String s);

    void append(String text, TextAttribute textAttribute);

    void flush();

    List<Text> getTextList();

    default String convertText(TextConverter textConverter) {
        return textConverter.convertText(getTextList());
    }

    default void append(int i) {
        append(String.valueOf(i));
    }

    default void append(char c) {
        append(String.valueOf(c));
    }

    default void appendNewLine() {
        append("\n");
    }
}