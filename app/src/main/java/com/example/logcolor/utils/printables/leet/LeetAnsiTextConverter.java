package com.example.logcolor.utils.printables.leet;

import com.example.logcolor.colorbuilder.converters.AnsiTextConverter;

public class LeetAnsiTextConverter extends AnsiTextConverter {

    @Override
    protected String convertMessage(String message) {
        return convertToLeet(message);
    }

    private String convertToLeet(String s) {
        return s.replace('o', '0')
                .replace('l', '1')
                .replace('e', '3')
                .replace('a', '4')
                .replace('t', '7')
                .replace('O', '0')
                .replace('L', '1')
                .replace('E', '3')
                .replace('A', '4')
                .replace('T', '7')
                .replaceAll("s(?=\\s|$)", "Z");
    }
}
