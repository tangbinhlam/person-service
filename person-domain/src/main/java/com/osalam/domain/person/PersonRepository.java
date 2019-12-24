package com.osalam.domain.person;

import com.osalam.domain.vo.PNumber;

public interface PersonRepository {

    Person putPerson(Person person);

    Person fetchPersonById(PNumber id);
}
