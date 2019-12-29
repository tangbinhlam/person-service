package com.osalam.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreatePersonCommandDTO {
    private String firstName;
    private String lastName;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthDate;
    private PersonalAddressDTO mainAddress;
}
