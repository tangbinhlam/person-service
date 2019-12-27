package com.osalam.domain.person;

import com.osalam.domain.number.BusinessNumberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonApplicationConfiguration {

    @Bean
    public PersonApplicationService createPersonApplicationService(PersonRepository personRepository,
                                                                   BusinessNumberRepository businessNumberRepository) {
        return new PersonApplicationService(personRepository, new PersonFactory(businessNumberRepository));
    }
}
