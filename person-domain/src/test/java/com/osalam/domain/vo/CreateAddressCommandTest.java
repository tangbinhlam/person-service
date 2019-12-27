package com.osalam.domain.vo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateAddressCommandTest {
    @Test
    public void of_should_initialize_correctly() {
        //Given
        String address = "my AddressNumber";
        //When
        CreateAddressCommand command = CreateAddressCommand.of(address);
        //Then
        assertThat(command.getAddress()).isSameAs(address);
        assertThat(command.isMain()).isFalse();
    }
}