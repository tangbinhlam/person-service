package com.osalam.rest.mapper;


import com.osalam.domain.person.PersonalAddress;
import com.osalam.domain.share.AddressNumber;
import com.osalam.rest.dto.PersonalAddressDTO;

public class PersonalAddressDtoMapper {
    public PersonalAddress mapToDomain(PersonalAddressDTO dto) {

        return PersonalAddress.of(AddressNumber.of(dto.getId()), dto.getAddress());
    }
}
