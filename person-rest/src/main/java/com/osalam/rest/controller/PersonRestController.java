package com.osalam.rest.controller;

import com.osalam.domain.person.PersonApplicationService;
import com.osalam.domain.vo.CreatePersonCommand;
import com.osalam.domain.vo.PNumber;
import com.osalam.rest.dto.CreatePersonCommandDTO;
import com.osalam.rest.dto.PersonDTO;
import com.osalam.rest.mapper.PersonDtoMapper;
import com.osalam.rest.mapper.PersonalAddressDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/rest/api/v1")
public class PersonRestController {
    private final PersonApplicationService service;
    private final PersonDtoMapper personDtoMapper;
    private final PersonalAddressDtoMapper addressDtoMapper;

    PersonRestController(PersonApplicationService service, PersonDtoMapper personDtoMapper, PersonalAddressDtoMapper addressDtoMapper) {
        this.service = service;
        this.personDtoMapper = personDtoMapper;
        this.addressDtoMapper = addressDtoMapper;
    }

    @GetMapping("/ping")
    public ResponseEntity ping() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/persons")
    public ResponseEntity<PersonDTO> create(@RequestBody CreatePersonCommandDTO dto) {
        CreatePersonCommand command = CreatePersonCommand.of(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getBirthDate(),
                addressDtoMapper.mapToDomain(dto.getMainAddress()));
        return ResponseEntity.status(HttpStatus.CREATED).body(personDtoMapper.mapToDto(service.createPerson(command)));
    }

    @GetMapping("/persons/{personId}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDTO readPerson(@PathVariable String personId) {
        return personDtoMapper.mapToDto(service.readPerson(PNumber.of(personId)));
    }

}
