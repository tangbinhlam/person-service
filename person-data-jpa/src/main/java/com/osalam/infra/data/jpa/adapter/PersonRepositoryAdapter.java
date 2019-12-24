package com.osalam.infra.data.jpa.adapter;

import com.osalam.domain.person.Person;
import com.osalam.domain.person.PersonRepository;
import com.osalam.domain.vo.PNumber;
import com.osalam.infra.data.jpa.mapper.PersonEntityDomainMapper;
import com.osalam.infra.data.jpa.repository.PersonJpaRepository;

public class PersonRepositoryAdapter implements PersonRepository {
    private final PersonJpaRepository personRepository;
    private final PersonEntityDomainMapper personEntityDomainMapper;

    public PersonRepositoryAdapter(PersonJpaRepository personRepository, PersonEntityDomainMapper personEntityDomainMapper) {
        this.personRepository = personRepository;
        this.personEntityDomainMapper = personEntityDomainMapper;
    }


    @Override
    public Person putPerson(Person person) {
        return personEntityDomainMapper.mapToDomain(personRepository.save(personEntityDomainMapper.mapToEntity(person)));
    }

    @Override
    public Person fetchPersonById(PNumber number) {
        return personEntityDomainMapper.mapToDomain(personRepository.findByNumber(number.getValue()));
    }
}
