package com.osalam.domain.person;

import java.util.regex.Pattern;

public class SocialSecurityNumber {
    private final long number;

    private SocialSecurityNumber(long number) {
        Pattern pattern = Pattern.compile("740[0-9]{6}");
        if (!pattern.matcher(number + "").matches()) {
            throw new IllegalArgumentException(
                    String.format("The number [%d] should begin by 740 and contains 9 digits (740 + 6 digits)", number));
        }
        this.number = number;
    }

    public static SocialSecurityNumber of(long number) {
        return new SocialSecurityNumber(number);
    }

    public Long value() {
        return number;
    }

    public String format() {
        return new StringBuffer(number + "")
                .insert(3, '-')
                .insert(6,'-')
                .toString();
    }
}
