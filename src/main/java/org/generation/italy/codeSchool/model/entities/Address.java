package org.generation.italy.codeSchool.model.entities;

public class    Address {
    private long id;
    private String street;
    private int houseNumber;
    private String city;
    private String country;

    public Address(long id, String street, int houseNumber, String city, String country) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.country = country;
    }
}
