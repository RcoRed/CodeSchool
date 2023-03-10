package org.generation.italy.codeSchool.model.entities;

public class Competence {
    private long id;
    private Skill skill;
    private Person person;
    private Level level;

    public Competence(long id, Skill skill, Person person, Level level) {
        this.id = id;
        this.skill = skill;
        this.person = person;
        this.level = level;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
