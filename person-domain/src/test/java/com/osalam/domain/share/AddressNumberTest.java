package com.osalam.domain.share;

import com.osalam.domain.vo.BusinessNumber;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AddressNumberTest {
    @Test
    void of_should_create_an_AddressNumber() {
        //Given
        String addressNumber = "A123456789";
        //When
        AddressNumber createdAddressNumber = AddressNumber.of(addressNumber);
        //Then
        assertThat(createdAddressNumber).isNotNull();
        assertThat(createdAddressNumber.format()).isEqualTo(addressNumber);
        assertThat(createdAddressNumber.format()).matches("A[0-9]{9}");
        assertThat(createdAddressNumber.getValue()).isEqualTo(123456789);
    }

    @Test
    void of_should_create_an_AddressNumber_with_long() {
        //Given
        long addressNumber = 123456789;
        //When
        AddressNumber createdAddressNumber = AddressNumber.of(addressNumber);
        //Then
        assertThat(createdAddressNumber).isNotNull();
        assertThat(createdAddressNumber.format()).isEqualTo("A123456789");
        assertThat(createdAddressNumber.format()).matches("A[0-9]{9}");
        assertThat(createdAddressNumber.getValue()).isEqualTo(123456789);
    }

    @Test
    void constructor_should_throw_error_if_AddressNumber_is_null() {
        // When
        Throwable thrown = catchThrowable(() -> AddressNumber.of(null));
        // Then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown.getMessage()).containsIgnoringCase("Address number should not be null");
    }

    @ParameterizedTest
    @ValueSource(strings = {"P1234678", "123456789", "A12q456789", "", "  "})
    void constructor_should_throw_error_if_AddressNumber_is_incorrect_format(String input) {
        // When
        Throwable thrown = catchThrowable(() -> AddressNumber.of(input));
        // Then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown.getMessage()).containsIgnoringCase("Address number should follow the pattern A[0-9]{9}");
    }

    //we will validate if our Product class had its methods equals() and hashCode()overridden correctly.
    @Test
    void equalsContract() {
        EqualsVerifier.forClass(BusinessNumber.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }
}
