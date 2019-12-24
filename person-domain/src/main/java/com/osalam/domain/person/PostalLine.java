package com.osalam.domain.person;

import lombok.Getter;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Getter
public class PostalLine {

    private static final int MAX_LENGTH = 30;

    private final String line;

    private PostalLine(String value) {
        this.line = value;
    }

    public static PostalLine of(String value) {
        if (value == null || isEmpty(value.trim()) || value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("A PostalLine cannot be empty or longer than 30 characters !");
        }
        return new PostalLine(value);
    }
}
