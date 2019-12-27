package com.osalam.domain.person;

import com.osalam.domain.vo.CreatePersonCommand;
import com.osalam.domain.vo.PNumber;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PersonApplicationServiceTest {
    private final Person mockedPerson = mock(Person.class);
    private final Person mockedReturnedPerson = mock(Person.class);
    private PersonRepository personRepository = mock(PersonRepository.class);
    private PersonFactory personFactory = mock(PersonFactory.class);
    private PersonApplicationService personApplicationService = new PersonApplicationService(personRepository, personFactory);

    @Test
    void createPerson_should_call_domain_service() {
        // Given
        CreatePersonCommand input = mock(CreatePersonCommand.class);
        given(personFactory.createPerson(input)).willReturn(mockedPerson);
        given(personRepository.putPerson(mockedPerson)).willReturn(mockedReturnedPerson);
        // When
        Person result = personApplicationService.createPerson(input);
        // Then
        assertThat(result).isNotNull();
        assertThat(result).isSameAs(mockedReturnedPerson);
        verify(personFactory).createPerson(input);
        verify(personRepository).putPerson(mockedPerson);
    }

    @Test
    void readPerson_should_call_the_repository() {
        // Given
        PNumber pNumber = mock(PNumber.class);
        given(personRepository.fetchPersonById(pNumber)).willReturn(mockedReturnedPerson);
        // When
        Person result = personApplicationService.readPerson(pNumber);
        // Then
        assertThat(result).isNotNull();
        assertThat(result).isSameAs(mockedReturnedPerson);
        verify(personRepository).fetchPersonById(pNumber);
    }
}