package com.osalam.infra.data.jpa.repository;

import com.osalam.infra.data.jpa.entity.PersonalAddressEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AddressJpaRepository extends CrudRepository<PersonalAddressEntity, Long> {

    Optional<PersonalAddressEntity> findByNumber(long number);
}
