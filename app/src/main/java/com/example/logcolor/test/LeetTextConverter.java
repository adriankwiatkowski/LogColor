package com.example.logcolor.test;

import com.example.logcolor.colorbuilder.converters.AnsiTextConverter;
import com.example.logcolor.colorbuilder.text.Text;

import java.util.List;

public class LeetTextConverter extends AnsiTextConverter {

    @Override
    public String convertText(List<Text> textList) {
        String s = super.convertText(textList);

        s = convertToLeet(s);

        return s;
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
                .replaceAll("s(?=\\s|$)", "Z")
                .replaceAll("\\S+", "($0)");
    }
}
