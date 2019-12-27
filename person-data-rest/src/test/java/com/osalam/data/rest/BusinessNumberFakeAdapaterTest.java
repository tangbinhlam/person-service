package com.osalam.data.rest;

import com.osalam.domain.share.AddressNumber;
import com.osalam.domain.vo.PNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class BusinessNumberFakeAdapaterTest {

    private BusinessNumberFakeAdapater adapater = new BusinessNumberFakeAdapater();
    private Random r = mock(Random.class);

    @BeforeEach
    void setUpMocks() {
        setField(adapater, "r", r);
    }

    @Test
    void fetchNextPNumber_should_create_new_PNumber() {
        // Given
        int highest = 99999999;
        int lowest = 10000000;
        int mockRandom = 22;
        given(r.nextInt(highest - lowest)).willReturn(mockRandom);
        // When
        PNumber pNumber = adapater.fetchNextPNumber();
        // Then
        assertThat(pNumber).isNotNull();
        assertThat(pNumber.getValue()).isEqualTo(mockRandom + lowest);
        verify(r).nextInt(highest - lowest);
    }

    @Test
    void fetchNextAddressNumber_should_create_new_AddressNumber() {
        // Given
        int highest = 999999999;
        int lowest = 100000000;
        int mockRandom = 88;
        given(r.nextInt(highest - lowest)).willReturn(mockRandom);
        // When
        AddressNumber addressNumber = adapater.fetchNextAddressNumber();
        // Then
        assertThat(addressNumber).isNotNull();
        assertThat(addressNumber.getValue()).isEqualTo(mockRandom + lowest);
        verify(r).nextInt(highest - lowest);
    }
}