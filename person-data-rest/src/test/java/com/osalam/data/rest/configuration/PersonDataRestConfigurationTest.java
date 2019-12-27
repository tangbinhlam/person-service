package com.osalam.data.rest.configuration;

import com.osalam.domain.number.BusinessNumberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import({
        PersonDataRestConfiguration.class
})
class PersonDataRestConfigurationTest {

    @Autowired
    BusinessNumberRepository businessNumberRepository;

    @Test
    void context_should_load() {
        assertThat(businessNumberRepository).isNotNull();
    }
}