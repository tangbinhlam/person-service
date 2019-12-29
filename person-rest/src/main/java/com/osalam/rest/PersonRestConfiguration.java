package com.osalam.rest;

import com.osalam.rest.controller.PersonRestController;
import com.osalam.rest.mapper.PersonDtoMapper;
import com.osalam.rest.mapper.PersonalAddressDtoMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = PersonRestController.class)
public class PersonRestConfiguration {
    @Bean
    public PersonDtoMapper personDomainDtoMapper() {
        return new PersonDtoMapper();
    }

    @Bean
    PersonalAddressDtoMapper personalAddressDtoMapper() {
        return new PersonalAddressDtoMapper();
    }
}
