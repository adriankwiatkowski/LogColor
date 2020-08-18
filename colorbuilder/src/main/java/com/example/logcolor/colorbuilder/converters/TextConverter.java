package com.example.logcolor.colorbuilder.converters;

import com.example.logcolor.colorbuilder.text.Text;

import java.util.List;

public abstract class TextConverter {

    public String convertText(List<Text> textList) {
        for (Text text : textList) {
            text.setMessage(convertMessage(text.getMessage()));
        }

        return convertTextList(textList);
    }

    protected String convertMessage(String message) {
        return message;
    }

    protected abstract String convertTextList(List<Text> textList);

}
