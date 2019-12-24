package com.osalam.domain.vo;


import java.util.regex.Pattern;

public class PNumber extends BusinessNumber {

    private PNumber(long value) {
        super(value);
    }

    public static PNumber of(String pNumber) {
        if (pNumber == null) {
            throw new IllegalArgumentException("PNumber should not be null");
        }
        Pattern pattern = Pattern.compile("P[0-9]{8}");
        if (!pattern.matcher(pNumber).matches()) {
            throw new IllegalArgumentException("PNumber should follow the pattern P[0-9]{8}");
        }
        return new PNumber(Integer.parseInt(pNumber.substring(1)));
    }

    public static PNumber of(long id) {
        return new PNumber(id);
    }

    @Override
    public String format() {
        return "P" + getValue();
    }

}
