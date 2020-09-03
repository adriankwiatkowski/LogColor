package com.adriankwiatkowski.logcolor.utils.printables;

import com.adriankwiatkowski.logcolor.colorbuilder.converters.HtmlTextConverter;

public class LeetHtmlTextConverter extends HtmlTextConverter {

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
