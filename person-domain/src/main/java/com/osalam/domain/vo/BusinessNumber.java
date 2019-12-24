package com.osalam.domain.vo;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class BusinessNumber {

    private final long value;

    protected BusinessNumber(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public abstract String format();
}
