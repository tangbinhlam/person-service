package com.osalam.domain.person;

import com.osalam.domain.vo.PNumber;
import io.vavr.control.Option;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
public class Person {
    private final PNumber id;
    private final SocialSecurityNumber socialSecurityNumber;
    private PostalLine firstName;
    private PostalLine lastName;
    private final LocalDate birthDate;
    private PersonalAddress mainAddress;
    private List<PersonalAddress> secondaryAddresses = new ArrayList<>();

    private Person(@NonNull PNumber id,
                  String firstName,
                  String lastName,
                  LocalDate birthDate,
                  SocialSecurityNumber socialSecurityNumber,
                  @NonNull PersonalAddress mainAddress) {
        this.id = id;
        this.firstName = PostalLine.of(
                Option.of(firstName)
                        .filter(value -> !StringUtils.isBlank(value))
                        .getOrElseThrow(() -> new IllegalArgumentException("The first name should not be empty or null !")));
        ;
        this.lastName = PostalLine.of(
                Option.of(lastName)
                .filter(value -> !StringUtils.isBlank(value))
                        .getOrElseThrow(() -> new IllegalArgumentException("The last name should not be empty or null !")));
        this.birthDate = Option.of(birthDate)
                .filter(date -> LocalDate.now().plus(1, ChronoUnit.DAYS).isAfter(date)) // we accept today as a valid date
                .getOrElseThrow(() -> new IllegalArgumentException("The birth date can not be null nor in the future"));
        this.mainAddress = mainAddress;
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public static WithIdBuilder of(String firstName, String lastName, LocalDate birthDate, PersonalAddress mainPersonalAddress) {
        return new Builder(firstName, lastName, birthDate, mainPersonalAddress);
    }

    Person changeName(String firstName) {
        this.firstName = PostalLine.of(firstName);
        return this;
    }

    public Person addSecondaryAddresses(PersonalAddress... personalAddress) {
        secondaryAddresses.addAll(Arrays.asList(personalAddress));
        return this;
    }

    public List<PersonalAddress> getSecondaryAddresses() {
        return Collections.unmodifiableList(secondaryAddresses);
    }

    public Person relocate(PersonalAddress newPersonalAddress) {
        mainAddress = newPersonalAddress;
        return this;
    }

    public interface BaseBuilder {

        BaseBuilder withSocialSecurityNumber(SocialSecurityNumber number);

        Person build();
    }

    public interface WithIdBuilder {
        BaseBuilder withId(PNumber id);
    }

    public static class Builder implements BaseBuilder, WithIdBuilder {
        private final String fistName;
        private final String lastName;
        private final LocalDate birthDate;
        private final PersonalAddress mainPersonalAddress;
        private PNumber id;
        private SocialSecurityNumber ssn;

        Builder(String fistName, String lastName, LocalDate birthDate, PersonalAddress mainPersonalAddress) {
            this.fistName = fistName;
            this.lastName = lastName;
            this.birthDate = birthDate;
            this.mainPersonalAddress = mainPersonalAddress;
        }

        public BaseBuilder withId(PNumber id) {
            this.id = id;
            return this;
        }

        @Override
        public BaseBuilder withSocialSecurityNumber(SocialSecurityNumber number) {
            this.ssn = number;
            return this;
        }

        public Person build() {
            return new Person(id, fistName, lastName, birthDate, ssn, mainPersonalAddress);
        }

    }
}
