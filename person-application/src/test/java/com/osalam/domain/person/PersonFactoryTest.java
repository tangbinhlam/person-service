package com.osalam.domain.person;

import com.osalam.domain.number.BusinessNumberRepository;
import com.osalam.domain.vo.CreatePersonCommand;
import com.osalam.domain.vo.PNumber;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PersonFactoryTest {
    private BusinessNumberRepository businessNumberRepository = mock(BusinessNumberRepository.class);
    private PersonFactory personFactory = new PersonFactory(businessNumberRepository);

    @Test
    void createPerson_should_call_BusinessNumberRepository_and_return_a_person_containing_command_field() {
        //Given
        PNumber pNumber = mock(PNumber.class);
        PersonalAddress personalAddress = mock(PersonalAddress.class);
        given(businessNumberRepository.fetchNextPNumber()).willReturn(pNumber);
        CreatePersonCommand createPersonCommand = CreatePersonCommand.of(
                "firtName",
                "lastName",
                LocalDate.now().minus(1, ChronoUnit.YEARS),
                personalAddress);
        // Then
        Person createdPerson = personFactory.createPerson(createPersonCommand);
        // Then
        assertThat(createdPerson).isNotNull();
        assertThat(createdPerson.getLastName().getLine()).isEqualTo(createPersonCommand.getLastName());
        assertThat(createdPerson.getFirstName().getLine()).isEqualTo(createPersonCommand.getFirstName());
        assertThat(createdPerson.getBirthDate()).isEqualTo(createPersonCommand.getBirthDate());
        assertThat(createdPerson.getId()).isSameAs(pNumber);
        assertThat(createdPerson.getMainAddress()).isSameAs(personalAddress);
    }

}