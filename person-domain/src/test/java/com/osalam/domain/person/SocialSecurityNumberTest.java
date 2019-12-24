package com.osalam.domain.person;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class SocialSecurityNumberTest {
    private static Stream<Long> constructor_should_throw_error_if_ssn_wrong_pattern() {
        return Stream.of(1L, 123456789L, 740323L);
    }

    @Test
    public void of_should_create_an_SocialSecurityNumber() {
        //Given
        long ssnValue = 740123456;
        // When
        SocialSecurityNumber ssn = SocialSecurityNumber.of(ssnValue);
        // Then
        assertThat(ssn).isNotNull();
        assertThat(ssn.value()).isEqualTo(740123456);
        assertThat(ssn.format()).isEqualTo("740-12-3456");
    }

    @ParameterizedTest
    @MethodSource
    void constructor_should_throw_error_if_ssn_wrong_pattern(long number) {
        // When
        Throwable thrown = catchThrowable(() -> SocialSecurityNumber.of(number));
        // Then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown.getMessage()).containsIgnoringCase(String.format("The number [%d] should begin by 740 and contains 9 digits (740 + 6 digits)", number));
    }
}
