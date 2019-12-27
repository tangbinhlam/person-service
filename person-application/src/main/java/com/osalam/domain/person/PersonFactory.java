package com.osalam.domain.person;

import com.osalam.domain.number.BusinessNumberRepository;
import com.osalam.domain.vo.CreatePersonCommand;
import com.osalam.domain.vo.PNumber;

class PersonFactory {
    private final BusinessNumberRepository businessNumberRepository;

    PersonFactory(BusinessNumberRepository businessNumberRepository) {
        this.businessNumberRepository = businessNumberRepository;
    }

    Person createPerson(CreatePersonCommand createPersonCommand) {
        PNumber pNumber = businessNumberRepository.fetchNextPNumber();
        return Person.of(createPersonCommand.getFirstName(),
                createPersonCommand.getLastName(),
                createPersonCommand.getBirthDate(),
                createPersonCommand.getMainAddress())
                .withId(pNumber)
                .build();
    }
}
