package com.osalam.domain.person;

import com.osalam.domain.vo.CreatePersonCommand;
import com.osalam.domain.vo.PNumber;
import org.springframework.transaction.annotation.Transactional;

public class PersonApplicationService {
    private final PersonRepository personRepository;
    private final PersonFactory personFactory;

    public PersonApplicationService(PersonRepository personRepository, PersonFactory personFactory) {
        this.personRepository = personRepository;
        this.personFactory = personFactory;
    }

    @Transactional
    public Person createPerson(CreatePersonCommand command) {
        return personRepository.putPerson(personFactory.createPerson(command));
    }

    @Transactional(readOnly = true)
    public Person readPerson(PNumber id) {
        return personRepository.fetchPersonById(id);
    }
}
