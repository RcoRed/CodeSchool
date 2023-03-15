package org.generation.italy.codeSchool.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(generator = "address_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "address_generator", sequenceName = "address_sequence", allocationSize = 1)
    @Column(name = "id_address")
    private long id;
    private String street;

    @Column(name = "house_number")
    private int houseNumber;
    private String city;
    private String country;

    public Address(){}

    public Address(long id, String street, int houseNumber, String city, String country) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.country = country;
    }
}
