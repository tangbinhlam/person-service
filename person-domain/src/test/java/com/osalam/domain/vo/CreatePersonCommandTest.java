package com.osalam.domain.vo;

import com.osalam.domain.person.PersonalAddress;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CreatePersonCommandTest {
    private PersonalAddress mainAddress = mock(PersonalAddress.class);

    @Test
    void of_only_mandatory_should_set_the_value() {
        //Given
        String firstName = "first name";
        String lastName = "last name";
        LocalDate birthDate = LocalDate.now();
        //When
        CreatePersonCommand createdCommand = CreatePersonCommand.of(firstName, lastName, birthDate, mainAddress);
        //Then
        assertThat(createdCommand).isNotNull();
        assertThat(createdCommand.getFirstName()).isEqualTo(firstName);
        assertThat(createdCommand.getLastName()).isEqualTo(lastName);
        assertThat(createdCommand.getBirthDate()).isEqualTo(birthDate);
        assertThat(createdCommand.getMainAddress()).isSameAs(mainAddress);
    }

    @Test
    void of_should_set_the_value() {
        //Given
        String firstName = "first name";
        String lastName = "last name";
        LocalDate birthDate = LocalDate.now();
        long number = 750123456;
        //When
        CreatePersonCommand createdCommand = CreatePersonCommand.of(firstName, lastName, birthDate, number, mainAddress);
        //Then
        assertThat(createdCommand).isNotNull();
        assertThat(createdCommand.getFirstName()).isEqualTo(firstName);
        assertThat(createdCommand.getLastName()).isEqualTo(lastName);
        assertThat(createdCommand.getBirthDate()).isEqualTo(birthDate);
        assertThat(createdCommand.getSocialSecurityNumber()).isEqualTo(number);
        assertThat(createdCommand.getMainAddress()).isSameAs(mainAddress);
    }
}