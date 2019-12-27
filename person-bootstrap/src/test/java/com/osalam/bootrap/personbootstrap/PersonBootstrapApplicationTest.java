package com.osalam.bootrap.personbootstrap;

import com.osalam.domain.person.PersonApplicationService;
import com.osalam.domain.person.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PersonBootstrapApplicationTest {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonApplicationService personApplicationService;

    @Test
    void context_should_load_correctly() {
        assertThat(personRepository).isNotNull();
        assertThat(personApplicationService).isNotNull();
    }
}