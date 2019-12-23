package com.osalam.domain.address;

import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Setter
public class Address {
    private final int id;
    private final String address;

    private Address(int id, String address) {
        if (id == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Id should not be null !");
        }
        if (StringUtils.isEmpty(address)) {
            throw new IllegalArgumentException("Address should not be empty nor null !");
        }
        this.id = id;
        this.address = address;
    }

    public static Address of(int id, String address) {
        return new Address(id, address);
    }
}
