package com.osalam.bootrap.personbootstrap;

import com.osalam.infra.data.jpa.PersonDataJpaConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import( {
        PersonDataJpaConfiguration.class
})
public class PersonBootstrapApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonBootstrapApplication.class, args);
    }

}
