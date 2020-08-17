package com.example.logcolor.colorbuilder.text;

import java.util.Objects;

public class Text {

    private String message;
    private TextAttribute textAttribute;

    public Text(String message) {
        this.message = message;
    }

    public Text(String message, TextAttribute textAttribute) {
        this.message = message;
        this.textAttribute = textAttribute;
    }

    public String getMessage() {
        return message;
    }

    public TextAttribute getTextAttribute() {
        return textAttribute;
    }

    @Override
    public String toString() {
        return "Text{" + "text='" + message + '\'' + ", textAttribute=" + textAttribute + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Text text1 = (Text) o;
        return Objects.equals(message, text1.message) &&
               Objects.equals(textAttribute, text1.textAttribute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, textAttribute);
    }
}