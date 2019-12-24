package com.osalam.infra.data.jpa.mapper;


import com.osalam.domain.person.PersonalAddress;
import com.osalam.domain.share.AddressNumber;
import com.osalam.infra.data.jpa.entity.PersonalAddressEntity;
import com.osalam.infra.data.jpa.repository.AddressJpaRepository;

public class AddressEntityDomainMapper {
    private final AddressJpaRepository repository;

    public AddressEntityDomainMapper(AddressJpaRepository repository) {
        this.repository = repository;
    }

    public PersonalAddressEntity mapToEntity(PersonalAddress address) {
        return repository.findByNumber(address.getId().getValue())
                .orElse(createNewEntity(address));
    }

    private PersonalAddressEntity createNewEntity(PersonalAddress address) {
        PersonalAddressEntity entity = new PersonalAddressEntity();
        entity.setAddressLine(address.getAddress());
        entity.setNumber(address.getId().getValue());
        return entity;
    }

    public PersonalAddress mapToDomain(PersonalAddressEntity entity) {
        return PersonalAddress.of(AddressNumber.of(entity.getNumber()), entity.getAddressLine());
    }
}
