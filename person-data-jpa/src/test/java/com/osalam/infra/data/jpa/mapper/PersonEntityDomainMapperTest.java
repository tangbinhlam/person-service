package com.osalam.infra.data.jpa.mapper;

import com.osalam.domain.address.Address;
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
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PersonEntityDomainMapperTest {
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
        //for address
        PersonalAddress secondPersonalAddress = mock(PersonalAddress.class);
        person.addSecondaryAddresses(secondPersonalAddress);
        PersonalAddressEntity returnedSecondPersonalAddressEntity = mock(PersonalAddressEntity.class);
        given(addressMapper.mapToEntity(address)).willReturn(returnedPersonalAddressEntity);
        given(addressMapper.mapToEntity(secondPersonalAddress)).willReturn(returnedSecondPersonalAddressEntity);
        //When
        PersonEntity entity = mapper.mapToEntity(person);
        //Then
        checkPersonEntityValues(entity);
        verify(addressMapper).mapToEntity(address);
        verify(addressMapper).mapToEntity(secondPersonalAddress);
        assertThat(entity.getPersonAddresses()).isNotEmpty()
                .extracting("address")
                .contains(returnedPersonalAddressEntity, returnedSecondPersonalAddressEntity);
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
        PersonAddressesEntity mainAddress = new PersonAddressesEntity();
        PersonAddressesEntity secondAddress = new PersonAddressesEntity();
        PersonalAddressEntity mainPersonalAddressEntity = mock(PersonalAddressEntity.class);
        PersonalAddressEntity secondPersonalAddressEntity = mock(PersonalAddressEntity.class);
        mainAddress.setPerson(entity);
        mainAddress.setAddress(mainPersonalAddressEntity);
        mainAddress.setMain(true);
        secondAddress.setAddress(secondPersonalAddressEntity);
        secondAddress.setMain(false);
        entity.getPersonAddresses().add(mainAddress);
        entity.getPersonAddresses().add(secondAddress);
        PersonalAddress mappedMainAddress = mock(PersonalAddress.class);
        PersonalAddress mappedSecondAddress = mock(PersonalAddress.class);
        given(addressMapper.mapToDomain(mainPersonalAddressEntity)).willReturn(mappedMainAddress);
        given(addressMapper.mapToDomain(secondPersonalAddressEntity)).willReturn(mappedSecondAddress);
        // When
        Person domain = mapper.mapToDomain(entity);
        // Then
        checkPersonValues(domain);
        verify(addressMapper).mapToDomain(mainPersonalAddressEntity);
        assertThat(domain.getMainAddress()).isNotNull().isEqualTo(mappedMainAddress);
        assertThat(domain.getSecondaryAddresses().get(0)).isEqualTo(mappedSecondAddress);
    }

    @Test
    void mapToDomain_should_thown_RuntimeException_when_mainAddress_is_null(){
        // Given
        PersonEntity entity = createPersonEntity();
        // When
        Throwable thrown = catchThrowable(() -> mapper.mapToDomain(entity));
        // Then
        assertThat(thrown).isInstanceOf(RuntimeException.class);
        assertThat(thrown.getMessage()).containsIgnoringCase("doesn't have any main address.");
    }
}