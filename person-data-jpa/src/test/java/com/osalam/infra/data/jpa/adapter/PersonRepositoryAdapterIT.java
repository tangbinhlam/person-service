package com.osalam.infra.data.jpa.adapter;

import com.osalam.domain.person.Person;
import com.osalam.domain.person.PersonRepository;
import com.osalam.domain.person.PersonalAddress;
import com.osalam.domain.person.SocialSecurityNumber;
import com.osalam.domain.share.AddressNumber;
import com.osalam.domain.vo.PNumber;
import com.osalam.infra.data.jpa.PersonDataJpaConfiguration;
import com.osalam.infra.data.jpa.entity.PersonAddressesEntity;
import com.osalam.infra.data.jpa.entity.PersonEntity;
import com.osalam.infra.data.jpa.entity.PersonalAddressEntity;
import com.osalam.infra.data.jpa.repository.AddressJpaRepository;
import com.osalam.infra.data.jpa.repository.PersonJpaRepository;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@Testcontainers
@DataJpaTest
@ContextConfiguration(classes = PersonDataJpaConfiguration.class, initializers = {PersonRepositoryAdapterIT.Initializer.class})
@AutoConfigureTestDatabase(replace = NONE) // Don't take H2, wait for in class configuration
@Transactional // Allow you handily manage transactions
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD) //reset DB after each test
public class PersonRepositoryAdapterIT {

    @Container
    private static MySQLContainer mySQLContainer = new MySQLContainer("mysql:5.7")
            .withDatabaseName("it-test-db")
            .withUsername("sa")
            .withPassword("sa");

    private final String firstName = "first name";
    private final String lastName = "last name";
    private final LocalDate birthDate = LocalDate.parse("2019-08-16");
    private final PNumber id = PNumber.of(12345678L);
    private final long socialSecurityNumber = 740123456;
    private final AddressNumber addressNumber = AddressNumber.of(123456789);
    private final String addressLine = "My Address";
    private final PersonalAddress address = PersonalAddress.of(addressNumber, addressLine);

    private final Person toSave = Person.of(firstName, lastName, birthDate, address)
            .withId(id)
            .withSocialSecurityNumber(SocialSecurityNumber.of(socialSecurityNumber))
            .build();
    @Autowired
    private PersonRepository adapter;
    @Autowired
    private AddressJpaRepository addressJpaRepository;
    @Autowired
    private PersonJpaRepository personJpaRepository;

    @Test
    public void putPerson_should_store_a_person_in_the_db() {
        //Given
        //When
        Person saved = adapter.putPerson(toSave);
        //Then
        checkPersonValues(saved);
    }

    private void checkPersonValues(Person saved) {
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isEqualTo(id);
        assertThat(saved.getBirthDate()).isEqualTo(birthDate);
        assertThat(saved.getFirstName().getLine()).isEqualTo(firstName);
        assertThat(saved.getLastName().getLine()).isEqualTo(lastName);
        assertThat(saved.getSocialSecurityNumber().value()).isEqualTo(socialSecurityNumber);
    }

    @Test
    public void putPerson_should_store_the_address_with_the_person() {
        // Given
        // When
        Person saved = adapter.putPerson(toSave);
        // Then
        checkPersonValues(saved);
        assertThat(saved.getMainAddress()).isNotNull()
                .extracting("id", "address")
                .contains(addressNumber, addressLine);
    }

    @Test
    public void putPerson_should_use_existing_address() {
        // Given
        TestTransaction.flagForCommit();
        PersonalAddressEntity returnedEntity = createAddressEntity();
        TestTransaction.end();
        // When
        TestTransaction.start();
        Person saved = adapter.putPerson(toSave);
        TestTransaction.end();
        // Then
        checkPersonValues(saved);
        assertThat(saved.getMainAddress()).isNotNull()
                .extracting("id", "address")
                .contains(addressNumber, addressLine);
        TestTransaction.start();
        Iterable<PersonalAddressEntity> addresses = addressJpaRepository.findAll();
        assertThat(addresses).isNotEmpty()
                .hasSize(1)
                .extracting("id", "number", "addressLine")
                .contains(new Tuple(returnedEntity.getId(), addressNumber.getValue(), addressLine));
    }

    private PersonalAddressEntity createAddressEntity() {
        PersonalAddressEntity personalAddressEntity = new PersonalAddressEntity();
        personalAddressEntity.setNumber(addressNumber.getValue());
        personalAddressEntity.setAddressLine(addressLine);
        return addressJpaRepository.save(personalAddressEntity);
    }

    @Test
    public void fetchPersonById_should_read_data_from_db() {
        //Given
        TestTransaction.flagForCommit();
        PersonEntity personEntity = new PersonEntity();
        personEntity.setBirthDate(birthDate);
        personEntity.setLastName(lastName);
        personEntity.setFirstName(firstName);
        personEntity.setNumber(id.getValue());
        personEntity.setSocialSecurityNumber(socialSecurityNumber);
        PersonalAddressEntity personalAddressEntity = createAddressEntity();
        PersonAddressesEntity link = new PersonAddressesEntity();
        link.setAddress(personalAddressEntity);
        link.setPerson(personEntity);
        link.setMain(true);
        personEntity.getPersonAddresses().add(link);
        personalAddressEntity.getPersonAddresses().add(link);
        personJpaRepository.save(personEntity);
        TestTransaction.end();
        //When
        TestTransaction.start();
        Person returnedPerson = adapter.fetchPersonById(id);
        //Then
        assertThat(returnedPerson).isNotNull();
        assertThat(returnedPerson.getId()).isEqualTo(id);
        assertThat(returnedPerson.getFirstName().getLine()).isEqualTo(firstName);
        assertThat(returnedPerson.getLastName().getLine()).isEqualTo(lastName);
        assertThat(returnedPerson.getSocialSecurityNumber().value()).isEqualTo(socialSecurityNumber);
        assertThat(returnedPerson.getMainAddress()).isNotNull()
                .extracting("address")
                .isEqualTo(addressLine);
    }

    public static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + mySQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + mySQLContainer.getUsername(),
                    "spring.datasource.password=" + mySQLContainer.getPassword(),
                    "spring.jpa.hibernate.ddl-auto=create",
                    "spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
