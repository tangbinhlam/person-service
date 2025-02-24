package com.osalam.domain.address;

import com.osalam.domain.share.AddressNumber;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class Address {
    private final AddressNumber id;
    private final String address;

    private Address(AddressNumber id, String address) {
        if (id == null) {
            throw new IllegalArgumentException("Id should not be null !");
        }
        if (StringUtils.isBlank(address)) {
            throw new IllegalArgumentException("Address should not be empty nor null !");
        }
        this.id = id;
        this.address = address;
    }

    public static Address of(AddressNumber id, String address) {
        return new Address(id, address);
    }
}
