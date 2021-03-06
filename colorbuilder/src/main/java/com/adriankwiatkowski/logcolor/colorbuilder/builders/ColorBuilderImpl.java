package com.adriankwiatkowski.logcolor.colorbuilder.builders;

import com.adriankwiatkowski.logcolor.colorbuilder.text.Text;
import com.adriankwiatkowski.logcolor.colorbuilder.text.TextAttribute;

import java.util.ArrayList;
import java.util.List;

public class ColorBuilderImpl extends AbstractColorBuilder {

    private List<Text> mTextList = new ArrayList<>();

    public ColorBuilderImpl(Builder builder) {
        super(builder);
    }

    @Override
    public void flush() {
        mTextList.clear();
    }

    @Override
    public void append(String s) {
        mTextList.add(new Text(s));
    }

    @Override
    public void append(String text, TextAttribute textAttribute) {
        mTextList.add(new Text(text, textAttribute));
    }

    @Override
    public List<Text> getTextList() {
        return mTextList;
    }

    public static class Builder extends AbstractColorBuilder.Builder {

        public Builder() {
        }

        @Override
        public ColorBuilderImpl build() {
            return new ColorBuilderImpl(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
