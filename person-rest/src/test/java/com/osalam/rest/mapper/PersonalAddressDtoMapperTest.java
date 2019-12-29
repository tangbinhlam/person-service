package com.osalam.rest.mapper;

import com.osalam.domain.person.PersonalAddress;
import com.osalam.domain.share.AddressNumber;
import com.osalam.rest.dto.PersonalAddressDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PersonalAddressDtoMapperTest {
    @Test
    void mapToDomain_should_map_correctly() {
        // Given
        String addressLine = "My Address bli";
        AddressNumber addressId = AddressNumber.of(674397124L);
        PersonalAddressDTO dto = new PersonalAddressDTO();
        dto.setAddress(addressLine);
        dto.setId(addressId.format());
        // When
        PersonalAddressDtoMapper mapper = new PersonalAddressDtoMapper();
        PersonalAddress address = mapper.mapToDomain(dto);
        // Then
        assertThat(address).isNotNull();
        assertThat(address.getId()).isEqualTo(addressId);
        assertThat(address.getAddress()).isEqualTo(addressLine);
    }
}