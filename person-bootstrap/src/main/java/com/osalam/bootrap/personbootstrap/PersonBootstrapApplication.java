package com.osalam.bootrap.personbootstrap;

import com.osalam.data.rest.configuration.PersonDataRestConfiguration;
import com.osalam.domain.person.PersonApplicationConfiguration;
import com.osalam.infra.data.jpa.PersonDataJpaConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        PersonDataJpaConfiguration.class,
        PersonApplicationConfiguration.class,
        PersonDataRestConfiguration.class
})
public class PersonBootstrapApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonBootstrapApplication.class, args);
    }

}
