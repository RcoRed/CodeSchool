package org.generation.italy.codeSchool.model.entities;

import jakarta.persistence.*;

@Entity
@Table (name = "skill")
public class Skill {
    @Id
    @GeneratedValue(generator = "skill_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "skill_generator", sequenceName = "skill_sequence", allocationSize = 1)
    @Column(name = "id_skill")
    private long id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn (name = "id_category")
    private Category category;

    public Skill() {

    }

    public Skill(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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
