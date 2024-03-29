package org.generation.italy.codeSchool.model.entities;

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "competence")
public class Competence {
    @Id
    @GeneratedValue(generator = "competence_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "competence_generator", sequenceName = "competence_sequence", allocationSize = 1)
    @Column(name= "id_competence")
    private long id;
    @ManyToOne
    @JoinColumn(name = "id_skill")
    private Skill skill;
    @ManyToOne
    @JoinColumn(name = "id_person")
    private Person person;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "level")
    @Type(PostgreSQLEnumType.class)
    private Level level;

    public Competence(){}
    public Competence(long id, Skill skill, Person person, Level level) {
        this.id = id;
        this.skill = skill;
        this.person = person;
        this.level = level;
    }

    public long getId() {
        return id;
    }

    public Skill getSkill() {
        return skill;
    }

    public Person getPerson() {
        return person;
    }

    public Level getLevel() {
        return level;
    }

    public void setId(long id) {
        this.id = id;
    }
}
