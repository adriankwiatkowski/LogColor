package com.example.logcolor.colorbuilder.text;

import java.util.Objects;

public class Text {

    private String text;
    private TextAttribute textAttribute;

    public Text(String text) {
        this.text = text;
    }

    public Text(String text, TextAttribute textAttribute) {
        this.text = text;
        this.textAttribute = textAttribute;
    }

    public String getMessage() {
        return text;
    }

    public TextAttribute getTextAttribute() {
        return textAttribute;
    }

    @Override
    public String toString() {
        return "Text{" + "text='" + text + '\'' + ", textAttribute=" + textAttribute + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Text text1 = (Text) o;
        return Objects.equals(text, text1.text) &&
               Objects.equals(textAttribute, text1.textAttribute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, textAttribute);
    }
}