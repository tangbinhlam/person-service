package com.osalam.rest.controller;

import com.osalam.domain.person.Person;
import com.osalam.domain.person.PersonApplicationService;
import com.osalam.domain.person.PersonalAddress;
import com.osalam.domain.vo.CreatePersonCommand;
import com.osalam.domain.vo.PNumber;
import com.osalam.rest.dto.CreatePersonCommandDTO;
import com.osalam.rest.dto.PersonDTO;
import com.osalam.rest.dto.PersonalAddressDTO;
import com.osalam.rest.mapper.PersonDtoMapper;
import com.osalam.rest.mapper.PersonalAddressDtoMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PersonRestControllerTest {
    private final PersonApplicationService service = mock(PersonApplicationService.class);
    private final PersonDtoMapper personDtoMapper = mock(PersonDtoMapper.class);
    private final PersonalAddressDtoMapper personalAddressDtoMapper = mock(PersonalAddressDtoMapper.class);
    private final PersonRestController controller = new PersonRestController(service, personDtoMapper, personalAddressDtoMapper);
    private final Person returnedPerson = mock(Person.class);
    private final PersonDTO expectedDto = mock(PersonDTO.class);
    private final PersonalAddress mainAddress = mock(PersonalAddress.class);

    @Test
    void create_should_call_service() {
        // Given
        given(service.createPerson(any(CreatePersonCommand.class))).willReturn(returnedPerson);
        given(personDtoMapper.mapToDto(returnedPerson)).willReturn(expectedDto);
        CreatePersonCommandDTO input = createPersonCommandDTO();
        given(personalAddressDtoMapper.mapToDomain(input.getMainAddress())).willReturn(mainAddress);
        // When
        ResponseEntity<PersonDTO> response = controller.create(input);
        // Then
        ArgumentCaptor<CreatePersonCommand> commandCaptor = ArgumentCaptor.forClass(CreatePersonCommand.class);
        verify(service).createPerson(commandCaptor.capture());
        verify(personDtoMapper).mapToDto(returnedPerson);
        verify(personalAddressDtoMapper).mapToDomain(input.getMainAddress());
        CreatePersonCommand capturedCommand = commandCaptor.getValue();
        assertThat(capturedCommand).isNotNull();
        assertThat(capturedCommand.getFirstName()).isEqualTo(input.getFirstName());
        assertThat(capturedCommand.getLastName()).isEqualTo(input.getLastName());
        assertThat(capturedCommand.getMainAddress()).isEqualTo(mainAddress);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isSameAs(expectedDto);
    }

    @Test
    void readPerson_should_call_service() {
        // Given
        given(service.readPerson(any(PNumber.class))).willReturn(returnedPerson);
        given(personDtoMapper.mapToDto(returnedPerson)).willReturn(expectedDto);
        String pNumber = "P12345678";
        PNumber pN = PNumber.of(pNumber);
        // When
        PersonDTO response = controller.readPerson(pNumber);
        // Then
        ArgumentCaptor<PNumber> commandCaptor = ArgumentCaptor.forClass(PNumber.class);
        verify(service).readPerson(commandCaptor.capture());
        verify(personDtoMapper).mapToDto(returnedPerson);
        assertThat(response).isEqualTo(expectedDto);
        assertThat(commandCaptor.getValue()).isEqualTo(pN);
    }

    private CreatePersonCommandDTO createPersonCommandDTO() {
        CreatePersonCommandDTO input = new CreatePersonCommandDTO();
        String firstName = "first name";
        String lastName = "last name";
        LocalDate birthDate = LocalDate.now();
        input.setFirstName(firstName);
        input.setLastName(lastName);
        input.setBirthDate(birthDate);
        PersonalAddressDTO mainAddressDto = mock(PersonalAddressDTO.class);
        input.setMainAddress(mainAddressDto);
        return input;
    }
}