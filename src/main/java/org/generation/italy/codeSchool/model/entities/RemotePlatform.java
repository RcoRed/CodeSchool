package org.generation.italy.codeSchool.model.entities;

import java.time.LocalDate;

public class RemotePlatform {
    private long id;
    private String name;
    private double annualCost;
    private LocalDate adoptionDate;

    public RemotePlatform(){}

    public RemotePlatform(long id, String name, double annualCost, LocalDate adoptionDate) {
        this.id = id;
        this.name = name;
        this.annualCost = annualCost;
        this.adoptionDate = adoptionDate;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAnnualCost() {
        return annualCost;
    }

    public LocalDate getAdoptionDate() {
        return adoptionDate;
    }
}
