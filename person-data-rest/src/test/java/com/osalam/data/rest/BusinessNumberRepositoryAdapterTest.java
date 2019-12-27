package com.osalam.data.rest;

import com.osalam.data.rest.configuration.BusinessNumberServiceProperties;
import com.osalam.domain.share.AddressNumber;
import com.osalam.domain.vo.PNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class BusinessNumberRepositoryAdapterTest {
    private final String baseUrl = "Http://TestUrl.net";
    private final String completeUrlPattern = "%s/rest/api/v1/business-number/%s";
    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final BusinessNumberServiceProperties properties = mock(BusinessNumberServiceProperties.class);
    private final BusinessNumberRepositoryAdapter client = new BusinessNumberRepositoryAdapter(properties, restTemplate);

    @BeforeEach
    void setUpMocks() {
        given(properties.getUrl()).willReturn(baseUrl);
    }

    @Test
    void fetchNextPNumber_should_call_rest_endpoint() {
        // Given
        long result = 98765432;
        String completeUrl = String.format(completeUrlPattern, baseUrl, "person-id");
        given(restTemplate.getForObject(completeUrl, Long.class)).willReturn(result);
        // When
        PNumber number = client.fetchNextPNumber();
        // Then
        assertThat(number.getValue()).isEqualTo(98765432);
        verify(restTemplate).getForObject(completeUrl, Long.class);
        verify(properties).getUrl();
    }

    @Test
    void fetchNextAddressNumber_should_call_rest_endpoint() {
        // Given
        long result = 98765432;
        String completeUrl = String.format(completeUrlPattern, baseUrl, "address-id");
        given(restTemplate.getForObject(completeUrl, Long.class)).willReturn(result);
        // When
        AddressNumber number = client.fetchNextAddressNumber();
        // Then
        assertThat(number.getValue()).isEqualTo(98765432);
        verify(restTemplate).getForObject(completeUrl, Long.class);
        verify(properties).getUrl();
    }
}