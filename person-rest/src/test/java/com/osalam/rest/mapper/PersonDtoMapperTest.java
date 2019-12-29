package com.osalam.rest.mapper;

import com.osalam.domain.person.Person;
import com.osalam.domain.person.PersonalAddress;
import com.osalam.domain.person.SocialSecurityNumber;
import com.osalam.domain.share.AddressNumber;
import com.osalam.domain.vo.PNumber;
import com.osalam.rest.dto.PersonDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

class PersonDtoMapperTest {
    private final PersonDtoMapper dtoMapper = new PersonDtoMapper();

    @Test
    void mapToDto_should_map_to_PersonDTO() {
        //Given
        String firstName = "first Name";
        String lastName = "last Name";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        SocialSecurityNumber ssn = SocialSecurityNumber.of(740123456);
        AddressNumber addressNumber = AddressNumber.of(7568463725L);
        PersonalAddress mainAddress = PersonalAddress.of(addressNumber, "Hello");
        Person person = Person.of(firstName, lastName, birthDate, mainAddress).withId(number).withSocialSecurityNumber(ssn).build();
        // When
        PersonDTO result = dtoMapper.mapToDto(person);
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getLastName()).isEqualTo(person.getLastName().getLine());
        assertThat(result.getFirstName()).isEqualTo(person.getFirstName().getLine());
        assertThat(result.getBirthDate()).isEqualTo(person.getBirthDate());
        assertThat(result.getId()).isEqualTo(person.getId().format());
        assertThat(result.getMainAddress().getAddress()).isEqualTo(mainAddress.getAddress());
        assertThat(result.getMainAddress().getId()).isEqualTo(mainAddress.getId().format());
    }

}