package com.osalam.rest.mapper;

import com.osalam.domain.person.Person;
import com.osalam.domain.person.PersonalAddress;
import com.osalam.rest.dto.PersonDTO;
import com.osalam.rest.dto.PersonalAddressDTO;

public class PersonDtoMapper {
    public PersonDTO mapToDto(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setId(person.getId().format());
        dto.setFirstName(person.getFirstName().getLine());
        dto.setLastName(person.getLastName().getLine());
        dto.setBirthDate(person.getBirthDate());
        dto.setMainAddress(mapToAddressDto(person.getMainAddress()));
        return dto;
    }

    private PersonalAddressDTO mapToAddressDto(PersonalAddress address) {
        PersonalAddressDTO addressDto = new PersonalAddressDTO();
        addressDto.setId(address.getId().format());
        addressDto.setAddress(address.getAddress());
        return addressDto;
    }
}
