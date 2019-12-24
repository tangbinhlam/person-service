package com.osalam.domain.address;

import com.osalam.domain.person.PersonalAddress;
import com.osalam.domain.share.AddressNumber;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;

public class AddressTest {

    @Test
    public void of_should_create_an_address() {
        //Given
        AddressNumber id = mock(AddressNumber.class);
        String address = "My Address";
        //When
        Address createdAddress = Address.of(id, address);
        //Then
        assertThat(createdAddress).isNotNull();
        assertThat(createdAddress.getAddress()).isSameAs(address);
        assertThat(createdAddress.getId()).isSameAs(id);
    }

    @ParameterizedTest
    @MethodSource("stringProvider_null_or_blank")
    void constructor_should_throw_error_if_address_is_null_or_blank(String input) {
        //Given
        AddressNumber id = mock(AddressNumber.class);
        // When
        Throwable thrown = catchThrowable(() -> Address.of(id, input));
        // Then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown.getMessage()).containsIgnoringCase("Address should not be empty nor null !");
    }

    private static Stream<String> stringProvider_null_or_blank() {
        return Stream.of(null, "", "  ");
    }

    @Test
    public void constructor_should_throw_error_if_id_null() {
        // Given
        String address = "My Address";
        // When
        Throwable thrown = catchThrowable(() -> Address.of(null, address));
        // Then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown.getMessage()).containsIgnoringCase("id should not be null");
    }

}
