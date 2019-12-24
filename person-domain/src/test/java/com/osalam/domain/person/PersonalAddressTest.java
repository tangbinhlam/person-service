package com.osalam.domain.person;

import com.osalam.domain.share.AddressNumber;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;

class PersonalAddressTest {

    @Test
    void of_should_create_an_personal_address() {
        //Given
        AddressNumber id = mock(AddressNumber.class);
        String address = "My Address";
        //When
        PersonalAddress createdPersonalAddress = PersonalAddress.of(id, address);
        //Then
        assertThat(createdPersonalAddress).isNotNull();
        assertThat(createdPersonalAddress.getAddress()).isSameAs(address);
        assertThat(createdPersonalAddress.getId()).isSameAs(id);
    }

    @Test
    public void constructor_should_throw_error_if_id_null() {
        // Given
        String address = "My Address";
        // When
        Throwable thrown = catchThrowable(() -> PersonalAddress.of(null, address));
        // Then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown.getMessage()).containsIgnoringCase("id should not be null");
    }

    @ParameterizedTest
    @MethodSource("stringProvider_null_or_blank")
    void constructor_should_throw_error_if_address_is_null_or_blank(String input) {
        //Given
        AddressNumber id = mock(AddressNumber.class);
        // When
        Throwable thrown = catchThrowable(() -> PersonalAddress.of(id, input));
        // Then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown.getMessage()).containsIgnoringCase("The address should not be empty or null !");
    }

    private static Stream<String> stringProvider_null_or_blank() {
        return Stream.of(null, "", "  ");
    }
}
