package com.osalam.domain.person;

import com.osalam.domain.number.BusinessNumberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(PersonApplicationConfiguration.class)
class PersonApplicationConfigurationIT {

    @MockBean
    PersonRepository personRepository;

    @MockBean
    BusinessNumberRepository businessNumberRepository;

    @Autowired
    PersonApplicationService service;

    @Test
    void context_should_be_complete() {
        assertThat(service).isNotNull()
                .isInstanceOf(PersonApplicationService.class);
    }
}