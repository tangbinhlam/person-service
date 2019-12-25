package com.osalam.infra.data.jpa;

import com.osalam.domain.person.PersonRepository;
import com.osalam.infra.data.jpa.repository.PersonJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = PersonDataJpaConfiguration.class)
class PersonDataJpaConfigurationIT {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonJpaRepository personJpaRepository;


    @Test
    void context_should_load() {
        assertThat(personRepository).isNotNull();
        assertThat(personJpaRepository).isNotNull();
    }
}