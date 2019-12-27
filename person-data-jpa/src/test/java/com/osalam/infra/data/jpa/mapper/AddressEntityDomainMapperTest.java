package com.osalam.infra.data.jpa.mapper;

import com.osalam.domain.person.PersonalAddress;
import com.osalam.domain.share.AddressNumber;
import com.osalam.infra.data.jpa.entity.PersonalAddressEntity;
import com.osalam.infra.data.jpa.repository.AddressJpaRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AddressEntityDomainMapperTest {
    private final String addressLine = "My Address";
    private final long addressNumber = 123456789L;
    private AddressJpaRepository addressJpaRepository = mock(AddressJpaRepository.class);
    private AddressEntityDomainMapper mapper = new AddressEntityDomainMapper(addressJpaRepository);

    @Test
    void mapToEntity_should_use_the_existing_address_if_number_is_set() {
        // Given
        PersonalAddressEntity existingAddress = mock(PersonalAddressEntity.class);
        given(addressJpaRepository.findByNumber(addressNumber)).willReturn(Optional.of(existingAddress));
        PersonalAddress addressInput = PersonalAddress.of(AddressNumber.of(addressNumber), addressLine);
        // When
        PersonalAddressEntity entity = mapper.mapToEntity(addressInput);
        // Then
        verify(addressJpaRepository).findByNumber(addressNumber);
        assertThat(entity).isSameAs(existingAddress);
    }

    @Test
    void mapToEntity_should_create_new_entity() {
        //Given
        PersonalAddress addressInput = PersonalAddress.of(AddressNumber.of(addressNumber), addressLine);
        //When
        PersonalAddressEntity entity = mapper.mapToEntity(addressInput);
        //Then
        assertThat(entity.getId()).isNull();
        assertThat(entity.getNumber()).isEqualTo(addressNumber);
        assertThat(entity.getAddressLine()).isEqualTo(addressLine);
    }

    @Test
    void mapToDomain_should_map_correctly() {
        // Given
        PersonalAddressEntity entity = new PersonalAddressEntity();
        entity.setAddressLine(addressLine);
        entity.setNumber(addressNumber);
        // When
        PersonalAddress domain = mapper.mapToDomain(entity);
        // Then
        assertThat(domain).isNotNull();
        assertThat(domain.getAddress()).isEqualTo(addressLine);
        assertThat(domain.getId().getValue()).isEqualTo(addressNumber);
    }
}