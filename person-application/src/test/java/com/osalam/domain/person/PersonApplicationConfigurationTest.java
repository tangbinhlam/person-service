package com.osalam.domain.person;

import com.osalam.domain.number.BusinessNumberRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.test.util.ReflectionTestUtils.getField;

class PersonApplicationConfigurationTest {

    @Test
    void createPersonApplicationService_should_instantiate_domain_service() {
        // Given
        PersonApplicationConfiguration configuration = new PersonApplicationConfiguration();
        PersonRepository personRepository = mock(PersonRepository.class);
        BusinessNumberRepository businessNumberRepository = mock(BusinessNumberRepository.class);
        // When
        PersonApplicationService service = configuration.createPersonApplicationService(personRepository, businessNumberRepository);
        // Then
        assertThat(service).isInstanceOf(PersonApplicationService.class);
        assertThat(getField(service, "personRepository")).isSameAs(personRepository);
        PersonFactory factory = (PersonFactory) getField(service, "personFactory");
        assertThat(factory).isNotNull().isInstanceOf(PersonFactory.class);
        assertThat(getField(factory, "businessNumberRepository")).isSameAs(businessNumberRepository);
    }
}