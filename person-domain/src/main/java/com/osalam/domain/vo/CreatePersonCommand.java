package com.osalam.domain.vo;

import com.osalam.domain.person.PersonalAddress;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreatePersonCommand {

    private final String firstName;
    private final String lastName;
    private final LocalDate birthDate;
    private final Long socialSecurityNumber;
    private final PersonalAddress mainAddress;

    private CreatePersonCommand(String firstName,
                                String lastName,
                                LocalDate birthDate,
                                Long socialSecurityNumber,
                                PersonalAddress mainAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.socialSecurityNumber = socialSecurityNumber;
        this.mainAddress = mainAddress;
    }

    public static CreatePersonCommand of(String firstName,
                                         String lastName,
                                         LocalDate birthDate,
                                         PersonalAddress mainAddress) {
        return of(firstName, lastName, birthDate, null, mainAddress);
    }

    public static CreatePersonCommand of(String firstName,
                                         String lastName,
                                         LocalDate birthDate,
                                         Long socialSecurityNumber,
                                         PersonalAddress mainAddress) {
        return new CreatePersonCommand(firstName, lastName, birthDate, socialSecurityNumber, mainAddress);
    }
}
