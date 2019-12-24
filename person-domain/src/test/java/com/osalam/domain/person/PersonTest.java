package com.osalam.domain.person;

import com.osalam.domain.vo.PNumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class PersonTest {
    private PersonalAddress personalAddress = mock(PersonalAddress.class);

    private static Stream<Arguments> provideArgumentForMandatoryCheck() {
        return Stream.of(
                // for fist name
                Arguments.of("", "lastName", LocalDate.now(), mock(PersonalAddress.class), mock(PNumber.class), mock(SocialSecurityNumber.class)),
                Arguments.of("  ", "lastName", LocalDate.now(), mock(PersonalAddress.class), mock(PNumber.class), mock(SocialSecurityNumber.class)),
                Arguments.of(null, "lastName", LocalDate.now(), mock(PersonalAddress.class), mock(PNumber.class), mock(SocialSecurityNumber.class)),
                // for last name
                Arguments.of("firstName", "", LocalDate.now(), mock(PersonalAddress.class), mock(PNumber.class), mock(SocialSecurityNumber.class)),
                Arguments.of("firstName", "   ", LocalDate.now(), mock(PersonalAddress.class), mock(PNumber.class), mock(SocialSecurityNumber.class)),
                Arguments.of("firstName", null, LocalDate.now(), mock(PersonalAddress.class), mock(PNumber.class), mock(SocialSecurityNumber.class)),
                // for birth Date
                Arguments.of("firstName", "lastName", LocalDate.now().plus(3, ChronoUnit.DAYS), mock(PersonalAddress.class), mock(PNumber.class), mock(SocialSecurityNumber.class)),
                Arguments.of("firstName", "lastName", null, mock(PersonalAddress.class), mock(PNumber.class), mock(SocialSecurityNumber.class))
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentForMandatoryCheck")
    void mandatory_fields_should_throw_exception_when_not_correct(String name, String forename, LocalDate birthDate, PersonalAddress mainAddress, PNumber id, SocialSecurityNumber ssn) {
        // Given
        // When
        Assertions.assertThrows(IllegalArgumentException.class, () -> Person.of(name, forename, birthDate, mainAddress).withId(id).withSocialSecurityNumber(ssn).build());
        // Then
    }

    @Test
    void builder_should_build_person_without_socialNumber() {
        //Given
        String firstName = "first Name";
        String lastName = "last Name";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        //When
        Person createdPerson = Person.of(firstName, lastName, birthDate, personalAddress).withId(number).build();
        //Then
        assertThat(createdPerson).isNotNull();
        assertThat(createdPerson.getFirstName().getLine()).isEqualTo(firstName);
        assertThat(createdPerson.getLastName().getLine()).isEqualTo(lastName);
        assertThat(createdPerson.getBirthDate()).isEqualTo(birthDate);
        assertThat(createdPerson.getId()).isEqualTo(number);
        assertThat(createdPerson.getSocialSecurityNumber()).isNull();
        assertThat(createdPerson.getMainAddress()).isSameAs(personalAddress);
        assertThat(createdPerson.getSecondaryAddresses()).isEmpty();
    }

    @Test
    void builder_should_build_person_wit_socialNumber() {
        //Given
        String firstName = "first Name";
        String lastName = "last Name";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        SocialSecurityNumber ssn = SocialSecurityNumber.of(740123456);
        //When
        Person createdPerson = Person.of(firstName, lastName, birthDate, personalAddress).withId(number).withSocialSecurityNumber(ssn).build();
        //Then
        assertThat(createdPerson).isNotNull();
        assertThat(createdPerson.getFirstName().getLine()).isEqualTo(firstName);
        assertThat(createdPerson.getLastName().getLine()).isEqualTo(lastName);
        assertThat(createdPerson.getBirthDate()).isEqualTo(birthDate);
        assertThat(createdPerson.getId()).isEqualTo(number);
        assertThat(createdPerson.getSocialSecurityNumber()).isSameAs(ssn);
        assertThat(createdPerson.getMainAddress()).isSameAs(personalAddress);
        assertThat(createdPerson.getSecondaryAddresses()).isEmpty();
    }

    @Test
    void changeName_should_update_name() {
        //Given
        String firstName = "first Name";
        String lastName = "last Name";
        String newName = "new Name";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        Person toModify = Person.of(firstName, lastName, birthDate, personalAddress)
                .withId(number)
                .build();
        assertThat(toModify.getFirstName().getLine()).isEqualTo(firstName);
        //When
        assertThat(toModify.changeName(newName)).isNotNull();
        //Then
        assertThat(toModify).isNotNull();
        assertThat(toModify.getLastName().getLine()).isEqualTo(lastName);
        assertThat(toModify.getFirstName().getLine()).isEqualTo(newName);
        assertThat(toModify.getBirthDate()).isEqualTo(birthDate);
        assertThat(toModify.getId()).isEqualTo(number);
        assertThat(toModify.getMainAddress()).isSameAs(personalAddress);
        assertThat(toModify.getSecondaryAddresses()).isEmpty();
    }

    @Test
    void addSecondaryAddress_should_add_an_address_to_the_list() {
        //Given
        String firstName = "first Name";
        String lastName = "last Name";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        Person person = Person.of(firstName, lastName, birthDate, personalAddress)
                .withId(number)
                .build();
        PersonalAddress secondaryPersonalAddress = mock(PersonalAddress.class);
        assertThat(person.getSecondaryAddresses()).isEmpty();
        //When
        Person modifiedPerson = person.addSecondaryAddresses(secondaryPersonalAddress);
        //Then
        assertThat(modifiedPerson).isNotNull();
        assertThat(modifiedPerson.getSecondaryAddresses()).containsOnly(secondaryPersonalAddress);
        assertThat(modifiedPerson.getMainAddress()).isSameAs(personalAddress);
    }

    @Test
    void relocate_should_change_main_address() {
        // Given
        String firstName = "first Name";
        String lastName = "last Name";
        LocalDate birthDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        PNumber number = PNumber.of(12345678);
        Person person = Person.of(firstName, lastName, birthDate, personalAddress)
                .withId(number)
                .build();
        PersonalAddress newPersonalAddress = mock(PersonalAddress.class);
        assertThat(person.getSecondaryAddresses()).isEmpty();
        assertThat(person.getMainAddress()).isSameAs(personalAddress);
        // When
        person.relocate(newPersonalAddress);
        // Then
        assertThat(person.getMainAddress()).isSameAs(newPersonalAddress);
    }
}
