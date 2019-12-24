package com.osalam.domain.vo;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class PNumberTest {
    @Test
    void of_should_create_an_pNumber() {
        //Given
        String pNumber = "P12345678";
        //When
        PNumber createdPNumber = PNumber.of(pNumber);
        //Then
        assertThat(createdPNumber).isNotNull();
        assertThat(createdPNumber.format()).isEqualTo(pNumber);
        assertThat(createdPNumber.format()).matches("P[0-9]{8}");
        assertThat(createdPNumber.getValue()).isEqualTo(12345678);
    }

    @Test
    void of_should_create_an_pNumber_with_number() {
        //Given
        long pNumber = 12345678;
        //When
        PNumber createdPNumber = PNumber.of(pNumber);
        //The
        assertThat(createdPNumber).isNotNull();
        assertThat(createdPNumber.format()).isEqualTo("P12345678");
        assertThat(createdPNumber.format()).matches("P[0-9]{8}");
        assertThat(createdPNumber.getValue()).isEqualTo(12345678);
    }

    @Test
    void constructor_should_throw_error_if_pNumber_is_null() {
        // When
        Throwable thrown = catchThrowable(() -> PNumber.of(null));
        // Then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown.getMessage()).containsIgnoringCase("Number should not be null");
    }

    @ParameterizedTest
    @ValueSource(strings = {"P123467", "12345678", "P12q45678", "", "  "})
    void constructor_should_throw_error_if_pNumber_is_incorrect_format(String input) {
        // When
        Throwable thrown = catchThrowable(() -> PNumber.of(input));
        // Then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown.getMessage()).containsIgnoringCase("PNumber should follow the pattern P[0-9]{8}");
    }
}
