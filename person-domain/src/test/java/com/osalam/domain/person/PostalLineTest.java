package com.osalam.domain.person;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.internal.Strings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;

public class PostalLineTest {
    @Test
    public void of_should_create_an_PostalLine() {
        //Given
        PostalLine id = mock(PostalLine.class);
        String line = "My Address";
        //When
        PostalLine createdAddress = PostalLine.of(line);
        //Then
        assertThat(createdAddress).isNotNull();
        assertThat(createdAddress.getLine()).isSameAs(line);
    }

    @ParameterizedTest
    @MethodSource
    void constructor_should_throw_error_if_address_is_null_or_blank_length_greater_30(String input) {
        // When
        Throwable thrown = catchThrowable(() -> PostalLine.of(input));
        // Then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown.getMessage()).containsIgnoringCase("A PostalLine cannot be empty ");
    }

    private static Stream<String> constructor_should_throw_error_if_address_is_null_or_blank_length_greater_30() {
        return Stream.of(null, "", "  ", StringUtils.repeat("a", 31));
    }
}