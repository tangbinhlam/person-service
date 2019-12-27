package com.osalam.data.rest.configuration;

import com.osalam.data.rest.BusinessNumberFakeAdapater;
import com.osalam.domain.number.BusinessNumberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableConfigurationProperties(BusinessNumberServiceProperties.class)
public class PersonDataRestConfiguration {
    @Bean
    public BusinessNumberRepository fakeBusinessNumberService() {
        return new BusinessNumberFakeAdapater();
    }
}
