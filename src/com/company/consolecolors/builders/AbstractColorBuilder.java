package com.company.consolecolors.builders;

import com.company.consolecolors.interfaces.Printable;
import com.company.consolecolors.models.AnsiColor;

import java.util.Objects;

public abstract class AbstractColorBuilder implements Printable {

    // String builder used throughout class.
    private StringBuilder mStringBuilder;

    public AbstractColorBuilder(Builder<?> builder) {
        mStringBuilder = Objects.requireNonNullElseGet(builder.stringBuilder, StringBuilder::new);
    }

    public abstract static class Builder<T extends Builder<T>> {

        StringBuilder stringBuilder;

        public T addStringBuilder(StringBuilder stringBuilder) {
            this.stringBuilder = stringBuilder;
            return self();
        }

        public abstract AbstractColorBuilder build();

        protected abstract T self();
    }

    /**
     * Appends encoded ansi reset on <c>StringBuilder</c>.
     * Use it only after encoding color and only if you are done using this color.
     */
    @Override
    public void appendColorReset() {
        mStringBuilder.append(AnsiColor.ANSI_RESET.getValue());
    }

    /**
     * Appends new line on <c>StringBuilder</c>
     */
    @Override
    public void appendNewLine() {
        mStringBuilder.append('\n');
    }

    /**
     * Builds String from <c>StringBuilder</c>.
     *
     * @return String from <c>StringBuilder</c>.
     */
    @Override
    public String getText() {
        return mStringBuilder.toString();
    }

    /**
     * Creates new <c>StringBuilder</c>.
     */
    @Override
    public void flush() {
        mStringBuilder = new StringBuilder();
    }

    @Override
    public void append(String str) {
        mStringBuilder.append(str);
    }

    @Override
    public void append(int i) {
        mStringBuilder.append(i);
    }

    @Override
    public void append(char c) {
        mStringBuilder.append(c);
    }

    protected StringBuilder getStringBuilder() {
        return mStringBuilder;
    }
}
