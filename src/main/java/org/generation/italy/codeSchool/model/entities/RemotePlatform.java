package org.generation.italy.codeSchool.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table (name = "remote_platform")
public class RemotePlatform {
    @Id
    @GeneratedValue(generator = "remote_platform_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "remote_platform_generator", sequenceName = "remote_platform_sequence", allocationSize = 1)
    @Column(name = "id_remote_platform")
    private long id;
    private String name;
    @Column (name = "annual_cost")
    private double annualCost;
    @Column (name = "adoption_date")
    private LocalDate adoptionDate;
    public RemotePlatform(){}

    public RemotePlatform(long id, String name, double annualCost, LocalDate adoptionDate){
        this.id=id;
        this.name=name;
        this.annualCost = annualCost;
        this.adoptionDate=adoptionDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public double getAnnualCost(){
        return annualCost;
    }
    public LocalDate getAdoptionDate() {
        return adoptionDate;
    }

}