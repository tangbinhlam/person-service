package com.osalam.rest;

import com.osalam.domain.number.BusinessNumberRepository;
import com.osalam.domain.person.PersonApplicationService;
import com.osalam.rest.controller.PersonRestController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(PersonRestConfiguration.class)
class PersonRestConfigurationIT {
    @Autowired
    private PersonRestController controller;

    @MockBean
    private BusinessNumberRepository businessNumberRepository;

    @MockBean
    private PersonApplicationService service;

    @Test
    void context_should_be_complete() {
        assertThat(controller).isNotNull();
        assertThat(service).isNotNull();
        assertThat(businessNumberRepository).isNotNull();
    }
}