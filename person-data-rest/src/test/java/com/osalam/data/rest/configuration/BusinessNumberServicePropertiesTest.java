package com.osalam.data.rest.configuration;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

class BusinessNumberServicePropertiesTest {

    @Test
    void getUrl_should_get_url_correctly() {
        // Given
        BusinessNumberServiceProperties properties = new BusinessNumberServiceProperties();
        String urlResult = "Hello_URL";
        // When
        setField(properties, "url", urlResult);
        // Then
        assertThat(properties.getUrl()).isEqualTo(urlResult);
    }

    @Test
    void setUrl_should_set_url_correctly() {
        // Given
        BusinessNumberServiceProperties properties = new BusinessNumberServiceProperties();
        String urlResult = "Hello_URL";
        // When
        properties.setUrl(urlResult);
        // Then
        assertThat(getField(properties, "url")).isEqualTo(urlResult);
    }
}