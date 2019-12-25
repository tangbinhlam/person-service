package com.osalam.infra.data.jpa.mapper;

import com.osalam.domain.person.Person;
import com.osalam.domain.person.PersonalAddress;
import com.osalam.domain.person.SocialSecurityNumber;
import com.osalam.domain.vo.PNumber;
import com.osalam.infra.data.jpa.entity.PersonAddressesEntity;
import com.osalam.infra.data.jpa.entity.PersonEntity;
import com.osalam.infra.data.jpa.entity.PersonalAddressEntity;
import com.osalam.infra.data.jpa.repository.AddressJpaRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AddressEntityDomainMapperTest {
    private final long id = 12345678;
    private final PNumber pNumber = PNumber.of(id);
    private final String firstName = "first name";
    private final String lastName = "last name";
    private final LocalDate birthDate = LocalDate.now();
    private final AddressEntityDomainMapper addressMapper = mock(AddressEntityDomainMapper.class);
    private final long number = 740123456;
    private final PersonalAddress address = mock(PersonalAddress.class);
    private PersonEntityDomainMapper mapper = new PersonEntityDomainMapper(addressMapper);

    @Test
    void mapToEntity_should_map_correctly() {
        //Given
        Person person = Person.of(firstName, lastName, birthDate, address)
                .withId(pNumber)
                .withSocialSecurityNumber(SocialSecurityNumber.of(number))
                .build();
        PersonalAddressEntity returnedPersonalAddressEntity = mock(PersonalAddressEntity.class);
        given(addressMapper.mapToEntity(address)).willReturn(returnedPersonalAddressEntity);
        //When
        PersonEntity entity = mapper.mapToEntity(person);
        //Then
        checkPersonEntityValues(entity);
        verify(addressMapper).mapToEntity(address);
        assertThat(entity.getPersonAddresses()).isNotEmpty()
                .extracting("address")
                .containsOnly(returnedPersonalAddressEntity);
        assertThat(entity.getSocialSecurityNumber()).isEqualTo(number);
    }

    private void checkPersonEntityValues(PersonEntity entity) {
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isNull();
        assertThat(entity.getFirstName()).isEqualTo(firstName);
        assertThat(entity.getLastName()).isEqualTo(lastName);
        assertThat(entity.getNumber()).isEqualTo(id);
    }

    @Test
    void mapToEntity_should_map_correctly_without_sn() {
        // Given
        Person person = Person.of(firstName, lastName, birthDate, address)
                .withId(pNumber)
                .build();
        // When
        PersonEntity entity = mapper.mapToEntity(person);
        // Then
        checkPersonEntityValues(entity);
        assertThat(entity.getSocialSecurityNumber()).isNull();
    }

    private void checkPersonValues(Person domain) {
        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(pNumber);
        assertThat(domain.getFirstName().getLine()).isEqualTo(firstName);
        assertThat(domain.getLastName().getLine()).isEqualTo(lastName);
        assertThat(domain.getBirthDate()).isSameAs(birthDate);
        assertThat(domain.getSocialSecurityNumber().value()).isEqualTo(number);
    }

    @NotNull
    private PersonEntity createPersonEntity() {
        PersonEntity entity = new PersonEntity();
        entity.setNumber(id);
        entity.setFirstName(firstName);
        entity.setLastName(lastName);
        entity.setBirthDate(birthDate);
        entity.setId(98765432L);
        entity.setSocialSecurityNumber(number);
        return entity;
    }

    @Test
    void mapToDomain_should_map_correctly() {
        // Given
        PersonEntity entity = createPersonEntity();
        PersonAddressesEntity link = new PersonAddressesEntity();
        PersonalAddressEntity personalAddressEntity = mock(PersonalAddressEntity.class);
        link.setPerson(entity);
        link.setAddress(personalAddressEntity);
        link.setMain(true);
        entity.getPersonAddresses().add(link);
        PersonalAddress mappedAddress = mock(PersonalAddress.class);
        given(addressMapper.mapToDomain(personalAddressEntity)).willReturn(mappedAddress);
        // When
        Person domain = mapper.mapToDomain(entity);
        // Then
        checkPersonValues(domain);
        verify(addressMapper).mapToDomain(personalAddressEntity);
        assertThat(domain.getMainAddress()).isNotNull().isEqualTo(mappedAddress);
    }
}