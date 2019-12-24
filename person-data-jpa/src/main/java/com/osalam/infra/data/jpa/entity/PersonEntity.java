package com.osalam.infra.data.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "T_PERSON")
public class PersonEntity {
    @Id
    @GeneratedValue
    @Column(name = "PERSON_PK", nullable = false, unique = true)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    private long number;

    @Column(name = "SOCIAL_SECURITY_NUMBER")
    private Long socialSecurityNumber;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<PersonAddressesEntity> personAddresses = new ArrayList<>();

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;
}
