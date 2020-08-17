package com.example.logcolor.colorbuilder.builders;

import com.example.logcolor.colorbuilder.interfaces.ColorBuilder;

public abstract class AbstractColorBuilder implements ColorBuilder {

    public AbstractColorBuilder(Builder<?> builder) {
    }

    public abstract static class Builder<T extends Builder<T>> {

        public abstract AbstractColorBuilder build();

        protected abstract T self();
    }
}
