package com.osalam.infra.data.jpa;

import com.osalam.domain.person.PersonRepository;
import com.osalam.infra.data.jpa.adapter.PersonRepositoryAdapter;
import com.osalam.infra.data.jpa.entity.PersonEntity;
import com.osalam.infra.data.jpa.mapper.AddressEntityDomainMapper;
import com.osalam.infra.data.jpa.mapper.PersonEntityDomainMapper;
import com.osalam.infra.data.jpa.repository.AddressJpaRepository;
import com.osalam.infra.data.jpa.repository.PersonJpaRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = PersonJpaRepository.class)
@EntityScan(basePackageClasses = PersonEntity.class)
public class PersonDataJpaConfiguration {

    @Bean
    public PersonRepository readStoreAdapter(PersonJpaRepository personJpaRepository, AddressJpaRepository addressJpaRepository) {
        return new PersonRepositoryAdapter(personJpaRepository, new PersonEntityDomainMapper(new AddressEntityDomainMapper(addressJpaRepository)));
    }
}
