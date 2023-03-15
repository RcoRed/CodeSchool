package org.generation.italy.codeSchool.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(generator = "course_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "course_generator", sequenceName = "course_sequence", allocationSize = 1)
    @Column(name = "id_category")
    private long id;
    private String name;
    private String description;

    public Category(){}

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
