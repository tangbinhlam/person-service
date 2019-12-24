package com.osalam.infra.data.jpa.repository;

import com.osalam.infra.data.jpa.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;

public interface PersonJpaRepository extends CrudRepository<PersonEntity, Long> {
    PersonEntity findByNumber(long pNumber);
}
