package com.osalam.infra.data.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "T_PERSON_ADDRESS")
public class PersonAddressesEntity {

    @Id
    @GeneratedValue
    @Column(name = "PERSON_ADDRESS_PK")
    private long id;

    @ManyToOne
    @JoinColumn(name = "PERSON_FK", referencedColumnName = "PERSON_PK")
    private PersonEntity person;


    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "ADDRESS_FK", referencedColumnName = "ADDRESS_PK")
    private PersonalAddressEntity address;

    private boolean main;
}
