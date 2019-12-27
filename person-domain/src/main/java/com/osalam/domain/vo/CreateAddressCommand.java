package com.osalam.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
class CreateAddressCommand {
    String address;
    boolean main;

    public static CreateAddressCommand of(String address) {
        return new CreateAddressCommand(address, false);
    }

}
