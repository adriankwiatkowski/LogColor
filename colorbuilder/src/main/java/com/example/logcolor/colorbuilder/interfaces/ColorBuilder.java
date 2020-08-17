package com.example.logcolor.colorbuilder.interfaces;

import com.example.logcolor.color.models.TextAttribute;
import com.example.logcolor.colorbuilder.TextConverter;
import com.example.logcolor.colorbuilder.builders.Text;

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