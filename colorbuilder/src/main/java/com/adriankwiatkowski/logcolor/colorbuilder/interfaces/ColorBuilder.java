package com.adriankwiatkowski.logcolor.colorbuilder.interfaces;

import com.adriankwiatkowski.logcolor.colorbuilder.converters.TextConverter;
import com.adriankwiatkowski.logcolor.colorbuilder.text.Text;
import com.adriankwiatkowski.logcolor.colorbuilder.text.TextAttribute;

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